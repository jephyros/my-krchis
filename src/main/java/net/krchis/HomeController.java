package net.krchis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.krchis.join.reposiory.QuestionRepository;

@Controller
public class HomeController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	
	@GetMapping("/")
	public String homeindex(Model model) {		
		
		model.addAttribute("questions",questionRepository.findAll());
		
		return "index";
	}
}
