package net.krchis.join.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.krchis.join.reposiory.User;

@Controller
@RequestMapping("users")
public class UserController {
	
	List<User> users= new ArrayList();
	
	
	
	@PostMapping("")
	public String join(User user ) {
		
		users.add(user);
		
		
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String userList(Model model) {
		
		model.addAttribute("users",users);
		
		
		return "/user/list";
	}
	
	
	
	
	@GetMapping("/form")
	public String joinForm() {
		
		return "/user/form";
	}
	
}
