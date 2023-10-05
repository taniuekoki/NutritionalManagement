package katachi.spring.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import katachi.spring.domain.model.Nutrition;
import katachi.spring.domain.service.UserService;
import katachi.spring.form.IntakeForm;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String getHome(@ModelAttribute IntakeForm form,Model model,
			@AuthenticationPrincipal UserDetails loginUser) {
		//Getは今日の日付表示
		Date date = new Date();
		
		dispayData(date,model,loginUser);
		
		return "/home";
	}
	
	 @RequestMapping(params = "back", method = RequestMethod.POST)
	public String postBackHome(@RequestParam("back") String strDate,Model model,
			 @AuthenticationPrincipal UserDetails loginUser) {
        try {
        	System.out.println(strDate);
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdFormat.parse(strDate);
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            //Date型の持つ日時の1日前を表示(日時の減算)
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            date = calendar.getTime();
            
            dispayData(date,model,loginUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return "home";
	}
	 
	@RequestMapping(params = "next", method = RequestMethod.POST)
	public String postNextHome(@RequestParam("next") String strDate,Model model,
			 @AuthenticationPrincipal UserDetails loginUser) {
        try {
        	System.out.println(strDate);
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdFormat.parse(strDate);
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date = calendar.getTime();
            
            dispayData(date,model,loginUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return "home";
	}
	
	@GetMapping("/delete/{id:.+}")
	public String getDelete(@PathVariable("id")int id) {
		//食事内容削除
		userService.deleteDiary(id);
		
		return "redirect:/home";
	}
	
	
	public void dispayData(Date date,Model model,
		 @AuthenticationPrincipal UserDetails loginUser) {
	
		 model.addAttribute("date",date);
	
		//ログインユーザーの目標取得
		Nutrition userGoal = userService.getLoginGoal(loginUser.getUsername());
		model.addAttribute("userGoal",userGoal);
		
		//食事内容取得
		List<Nutrition> foodList = userService.getUserFoodList(loginUser.getUsername(),date);
		model.addAttribute("foodList",foodList);
		
		//摂取量取得
		Nutrition totalIntake = userService.getTotalIntake(loginUser.getUsername(),date);
		
		//各栄養素からカロリーを計算してModelに追加
		if(totalIntake != null) {
			model.addAttribute("totalIntake",totalIntake);
			model.addAttribute("calorieIntake",((totalIntake.getCarbohydrates()+totalIntake.getProtein())*4)+totalIntake.getLipid()*9);
		}else {
			Nutrition totalIntake2 = new Nutrition();
			model.addAttribute("totalIntake",totalIntake2);
			model.addAttribute("calorieIntake",0);
		}
	 }
}
