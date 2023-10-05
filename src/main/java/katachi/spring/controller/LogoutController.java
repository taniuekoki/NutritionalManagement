package katachi.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

	/*ログイン画面にリダイレクト*/
	@GetMapping("/logout")
	public String postLogout() {
		return "redirect:/login";
	}


}
