package katachi.spring.domain.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import katachi.spring.domain.model.DBUser;
import katachi.spring.domain.model.Nutrition;
import katachi.spring.domain.service.UserService;
import katachi.spring.repository.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper mapper;
	
	/*ログインユーザー情報取得*/
	@Override
	public DBUser getLoginUser(String userName) {
		return mapper.findLoginUser(userName);
	}
	
	//ログインユーザー目標取得
	@Override
	public Nutrition getLoginGoal(String userId) {
		return mapper.getGoal(userId);
	}
	
	//食事内容取得
	@Override
	public List<Nutrition> getUserFoodList(String userId,Date date){
		return mapper.getFoodList(userId, date);
	}
	
	/*摂取量取得*/
	@Override
	public Nutrition getTotalIntake(String userId,Date date) {
		return mapper.getSumIntake(userId, date);
	}
	
	/*食事追加*/
	@Override
	public void addFood(String userId,Nutrition nutrition) {
		mapper.addDiary(userId, nutrition);
	}
	
	/*マイフード取得*/
	@Override
	public List<Nutrition> getMyFood(String userId){
		return mapper.getMyFood(userId);
	}
	
	/*マイフード一つ取得*/
	@Override
	public Nutrition getMyFoodOne(int id) {
		return mapper.getMyFoodOneRecord(id);
	}
	
	/*マイフード検索*/
	@Override
	public List<Nutrition> getMyFoodSearch(String search){
		return mapper.getMyFoodSearchName(search);
	}
	
	/*目標編集*/
	@Override
	public void editGoal(Nutrition nutrition,String userId) {
		mapper.updateEditGoal(nutrition,userId);
	}
	
	/*マイフード編集*/
	@Override
	public void editMyFood(Nutrition nutrition,int id) {
		mapper.updateEditMyFood(nutrition, id);
	}
	/*食事内容削除*/
	@Override
	public void deleteDiary(int id) {
		mapper.deleteDiaryOne(id);
	}
	
	/*マイフード登録*/
	@Override
	public void registerMyFood(String userId,Nutrition nutrition) {
		mapper.addMyFood(userId, nutrition);
	}
}
