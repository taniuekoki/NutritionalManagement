package katachi.spring.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import katachi.spring.domain.model.Nutrition;
import katachi.spring.domain.service.UserService;
import katachi.spring.form.NutritionForm;

@Controller
@RequestMapping("/addFood")
public class AddFoodController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public String getAddFood() {
		return "/addFood";
	}
	
	@GetMapping("/addQuick")
	public String getAddQuick(@ModelAttribute NutritionForm form) {
		return "/addFood/addQuick";
	}
	
	@PostMapping("/addQuick")
	public String postAddQuick(@ModelAttribute @Validated NutritionForm form,
			BindingResult bindingResult,Model model,
			@AuthenticationPrincipal UserDetails loginUser) {
		//入力チェック結果
		if(bindingResult.hasErrors()) {
			return getAddQuick(form);
		}
		
		Nutrition nutrition = modelMapper.map(form,Nutrition.class);
		
		//今日の食事内容に追加
		userService.addFood(loginUser.getUsername(),nutrition);
		
		return "redirect:/home";
	}
	
	@GetMapping("/myFood")
	public String getMyFood(@AuthenticationPrincipal UserDetails loginUser,
			Model model) {
		//ログインユーザーのマイフード取得
		List<Nutrition> myFoodList = userService.getMyFood(loginUser.getUsername());
		model.addAttribute("foodList",myFoodList);
		return "/addFood/myFood";
	}
	
	@GetMapping("/addMyFood/{id:.+}")
	public String getAddMyFood(@PathVariable("id")int id,
			@AuthenticationPrincipal UserDetails loginUser) {
		//取得したidからマイフード一つ取得
		Nutrition myFood = userService.getMyFoodOne(id);
		
		//今日の食事内容に追加
		userService.addFood(loginUser.getUsername(),myFood);
		
		return "redirect:/home";
	}
	
	@GetMapping("/myFoodSearch")
	public String getMyFoodSearch(Model model) {
		model.addAttribute("request","get");
		return "/addFood/myFoodSearch";
	}
	
	@PostMapping("/myFoodSearch")
	public String postMyFoodSearch(@RequestParam("search") String search,
			@AuthenticationPrincipal UserDetails loginUser,
			Model model) {
		model.addAttribute("request","post");	//htmlの判定用
		model.addAttribute("search",search);	//検索フォームに文字を残すため
		
		//マイフード検索
		List<Nutrition> myFoodList = userService.getMyFoodSearch(search);
		model.addAttribute("foodList",myFoodList);
		
		return "/addFood/myFoodSearch";
	}
}
