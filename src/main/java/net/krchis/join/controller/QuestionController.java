package net.krchis.join.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.krchis.join.reposiory.Question;
import net.krchis.join.reposiory.QuestionRepository;
import net.krchis.join.reposiory.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);

		Question newQuestion = new Question(sessionedUser, title, contents);

		questionRepository.save(newQuestion);

		return "redirect:/";
	}
    
	//글보기
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model, HttpSession session) {
		
			
		model.addAttribute("question", questionRepository.findOne(id));
		return "/qna/show";
	}
	//글수정시 권한 및 에러체크
	
	public boolean hasPermission(HttpSession session,Question question) {
		//로그인여부체크
		if (!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이필요합니다.");
		}
		if(!question.isSameWriter(HttpSessionUtils.getUserFromSession(session))) {
			throw new IllegalStateException("자신이 쓴글만 수정 삭제가 가능합니다..");
		}
			
		return true;
		
	}
	//글수정폼
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model,HttpSession session) {
		
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			model.addAttribute("question",question);
			return "/qna/updateForm";
					
		}catch(IllegalStateException e){
			model.addAttribute("errorMessage",e.getMessage());
			return "/user/login";
		}
		
		
		
		
	}
	//글수정처리
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents,Model model,HttpSession session) {
		
		
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			question.update(title, contents);
			questionRepository.save(question);
			//System.out.println(question);
			return String.format("redirect:/questions/%d", id);
					
		}catch(IllegalStateException e){
			model.addAttribute("errorMessage",e.getMessage());
			return "/user/login";
		}
		
		
		

		

	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id,Model model,HttpSession session) {
		
		
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			questionRepository.delete(id);
			return "redirect:/";
					
		}catch(IllegalStateException e){
			model.addAttribute("errorMessage",e.getMessage());
			return "/user/login";
		}
		
		
		
	}

}
