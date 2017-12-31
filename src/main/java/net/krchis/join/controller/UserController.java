package net.krchis.join.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.krchis.join.reposiory.User;
import net.krchis.join.reposiory.UserRepository;

@Controller
@RequestMapping("users")
public class UserController {

	// private List<User> users= new ArrayList();

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginform() {

		return "/user/login";
	}
	
	@GetMapping("/form")
	public String form() {

		return "/user/form";
	}
	
	@PostMapping("")
	public String join(User user) {

		// users.add(user);
		userRepository.save(user);

		return "redirect:/users";
	}
	
	@PutMapping("")
	public String updateUser(User user) {

		// users.add(user);
		userRepository.save(user);

		return "redirect:/users";
	}
	@PostMapping("/login")
	public String login(String userId,String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		
		if (user == null) {
			return "redirect:/users/loginForm";
		}
		if(!password.equals(user.getPassword())){
			return "redirect:/users/loginForm";
		}
		session.setAttribute("user", user);
		
		
		return "redirect:/";
	}
	@GetMapping("")
	public String userList(Model model) {

		model.addAttribute("users", userRepository.findAll());

		return "/user/list";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model) {

		model.addAttribute("user", userRepository.findOne(id));

		return "/user/updateForm";
	}

	

}
