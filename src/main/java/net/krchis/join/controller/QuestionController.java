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
	
	//글수정폼
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model,HttpSession session) {
		
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if(!question.isSameWriter(loginUser)) {
			return "redirect:/users/loginForm";
		}
		
		model.addAttribute("question",question);
		return "/qna/updateForm";
	}
	//글수정처리
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents,HttpSession session) {
		
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if(!question.isSameWriter(loginUser)) {
			return "redirect:/users/loginForm";
		}

		question.update(title, contents);
		questionRepository.save(question);
		System.out.println(question);
		return String.format("redirect:/questions/%d", id);

	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id,HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		if(!question.isSameWriter(loginUser)) {
			return "redirect:/users/loginForm";
		}

		questionRepository.delete(id);
		return "redirect:/";
	}

}
