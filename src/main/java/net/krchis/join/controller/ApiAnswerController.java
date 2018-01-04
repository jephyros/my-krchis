package net.krchis.join.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.krchis.join.reposiory.Answer;
import net.krchis.join.reposiory.AnswerRepository;
import net.krchis.join.reposiory.Question;
import net.krchis.join.reposiory.QuestionRepository;
import net.krchis.join.reposiory.Result;
import net.krchis.join.reposiory.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;
	
	
	@PostMapping("")
	public Answer create (@PathVariable Long questionId, String contents,HttpSession session){
		if(!HttpSessionUtils.isLoginUser(session)) {
			return null; 
		}
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(sessionedUser,question, contents);
		question.addAnswer();
		
		return answerRepository.save(answer);
	}
	
	@DeleteMapping("/{id}")
	public Result delete (@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이필요합니다.");
		}
		User loginuser= HttpSessionUtils.getUserFromSession(session);
		Answer answer = answerRepository.findOne(id);
		if(!answer.isSamewriter(loginuser)) {
			return Result.fail("자신의글만삭제할수있습니다..");
		}
		answer.deleteCountQuestionAnswer();
		answerRepository.delete(id);
		
		return Result.ok();
	}

}
