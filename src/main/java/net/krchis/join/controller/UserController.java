package net.krchis.join.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.krchis.join.reposiory.User;
import net.krchis.join.reposiory.UserRepository;

@Controller
@RequestMapping("users")
public class UserController {
	
	//private List<User> users= new ArrayList();
	
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping("")
	public String join(User user ) {
		
		//users.add(user);
		userRepository.save(user);
		
		
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String userList(Model model) {
		
		model.addAttribute("users",userRepository.findAll());
		
		
		return "/user/list";
	}
	
	
	
	
	@GetMapping("/form")
	public String joinForm() {
		
		return "/user/form";
	}
	
}
