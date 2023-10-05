package katachi.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import katachi.spring.form.Form;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String getLogin(@ModelAttribute Form form) {
		return "login";
	}
}
