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

import katachi.spring.domain.model.Nutrition;
import katachi.spring.domain.service.UserService;
import katachi.spring.form.EditGoalForm;
import katachi.spring.form.NutritionForm;

@Controller
@RequestMapping("/setting")
public class SettingController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public String getSetting() {
		return "/setting";
	}
	
	/*目標編集ページ用*/
	@GetMapping("/editGoal")
	public String getEditGoal(@ModelAttribute EditGoalForm form,Model model,
			 @AuthenticationPrincipal UserDetails loginUser) {
		//html判定用
		model.addAttribute("success",false);
		
		//現在の目標取得
		Nutrition nutrition = userService.getLoginGoal(loginUser.getUsername());
		EditGoalForm editGoalForm = modelMapper.map(nutrition, EditGoalForm.class);
		model.addAttribute("editGoalForm",editGoalForm);
		
		return "/setting/editGoal";
	}
	
	@PostMapping("/editGoal")
	public String postEdirGoal(@AuthenticationPrincipal UserDetails loginUser,Model model,
			@ModelAttribute @Validated EditGoalForm form,
			 BindingResult bindingResult) {
		
		//入力チェック結果
		if(bindingResult.hasErrors()) {
			return getEditGoal(form,model,loginUser);
		}
		
		//DB用に入れ替え
		Nutrition edit = modelMapper.map(form,Nutrition.class);
		//目標をアップデート
		userService.editGoal(edit,loginUser.getUsername());
		
		//再取得
		Nutrition nutrition = userService.getLoginGoal(loginUser.getUsername());
		EditGoalForm editGoalForm = modelMapper.map(nutrition, EditGoalForm.class);
		model.addAttribute("editGoalForm",editGoalForm);
		
		//html判定用
		model.addAttribute("success",true);
		
		return "/setting/editGoal";
	}
	
	/*マイフード編集ページ用*/
	@GetMapping("/myFoodEdit")
	public String getMyFoodEdit(@AuthenticationPrincipal UserDetails loginUser,
			Model model) {
		//ログインユーザーのマイフード取得
		List<Nutrition> myFoodList = userService.getMyFood(loginUser.getUsername());
		model.addAttribute("foodList",myFoodList);
		return "/setting/myFoodEdit";
	}
	
	@GetMapping("/myFoodEdit/{id:.+}")
	public String getMyFoodEditDetail(@PathVariable("id")int id,
			@AuthenticationPrincipal UserDetails loginUser,Model model) {
		//取得したidからマイフード一つ取得
		Nutrition myFood = userService.getMyFoodOne(id);
		NutritionForm myFoodForm = modelMapper.map(myFood, NutritionForm.class);
		model.addAttribute("nutritionForm",myFoodForm);
		
		return "/setting/myFoodEditDetail";
	}
	
	@PostMapping("/myFoodEditDetail")
	public String postMyFoodEditDetail(@ModelAttribute @Validated NutritionForm form,
			BindingResult bindingResult,Model model,
			@AuthenticationPrincipal UserDetails loginUser) {
		//入力チェック結果
		if(bindingResult.hasErrors()) {
			return getMyFoodEditDetail(form.getId(),loginUser,model);
		}
		Nutrition nutrition = modelMapper.map(form, Nutrition.class);
		
		//マイフード編集
		userService.editMyFood(nutrition,nutrition.getId());
		
		return getMyFoodEdit(loginUser,model);
	}
	
	@GetMapping("/myFoodAdd")
	public String getAddQuick(@ModelAttribute NutritionForm form) {
		return "/setting/myFoodAdd";
	}
	
	@PostMapping("/myFoodAdd")
	public String postAddQuick(@ModelAttribute @Validated NutritionForm form,
			BindingResult bindingResult,Model model,
			@AuthenticationPrincipal UserDetails loginUser) {
		//入力チェック結果
		if(bindingResult.hasErrors()) {
			return getAddQuick(form);
		}
		
		Nutrition nutrition = modelMapper.map(form,Nutrition.class);
		
		//今日の食事内容に追加
		userService.registerMyFood(loginUser.getUsername(),nutrition);
		
		return "redirect:/home";
	}
}
