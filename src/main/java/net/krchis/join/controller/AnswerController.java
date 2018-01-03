package net.krchis.join.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.krchis.join.reposiory.Answer;
import net.krchis.join.reposiory.AnswerRepository;
import net.krchis.join.reposiory.Question;
import net.krchis.join.reposiory.QuestionRepository;
import net.krchis.join.reposiory.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	
	
	@PostMapping("")
	public String create (@PathVariable Long questionId, String contents,HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm"; 
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(sessionedUser,question, contents);
		answerRepository.save(answer);
		
		return String.format("redirect:/questions/%d", questionId);
	}

}
