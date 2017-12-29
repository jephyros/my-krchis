package net.krchis.join;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/create")
	public String join() {
		
		return "welcome";
	}
}
