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
	
	
	@PostMapping("/login")
	public String login(String userId,String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		
		
		if (user == null) {
			return "redirect:/users/loginForm";
		}
		if(!password.equals(user.getPassword())){
			return "redirect:/users/loginForm";
		}
		session.setAttribute("sessionedUser", user);
		
		
		return "redirect:/";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}
	
	@GetMapping("")
	public String userList(Model model) {

		model.addAttribute("users", userRepository.findAll());

		return "/user/list";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model,HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = (User) tempUser;		
		if (!id.equals(sessionedUser.getId())) {
			throw new IllegalStateException("You can't update the another user!");
		}	
		
		
		
		//model.addAttribute("users", userRepository.findOne(sessionedUser.getId()));
		model.addAttribute("users", userRepository.findOne(id));

		return "/user/updateForm";
	}
	@PutMapping("")
	public String updateUser(User user,HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "redirect:/users/loginForm";		}
		
		User sessionedUser = (User) tempUser;		

		// users.add(user);
		if (!user.getId().equals(sessionedUser.getId())) {
			throw new IllegalStateException("You can't update the another user!");
		}	
		
		
		User updateUser = userRepository.findOne(user.getId());
		updateUser.updateUser(user);
				
		userRepository.save(updateUser);

		return "redirect:/users";
	}

	

}
