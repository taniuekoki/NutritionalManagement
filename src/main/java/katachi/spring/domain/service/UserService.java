package katachi.spring.domain.service;

import java.util.Date;
import java.util.List;

import katachi.spring.domain.model.DBUser;
import katachi.spring.domain.model.Nutrition;

public interface UserService {
	
	/*ログインチェック*/
	public DBUser getLoginUser(String userName);
	
	//ログインユーザー目標取得
	public Nutrition getLoginGoal(String userId);
	
	//食事内容取得
	public List<Nutrition> getUserFoodList(String userId,Date date);
	
	/*摂取量取得*/
	public Nutrition getTotalIntake(String userId,Date date);
	
	/*食事追加*/
	public void addFood(String userId,Nutrition nutrition);
	
	/*マイフード取得*/
	public List<Nutrition> getMyFood(String userId);
	
	/*マイフード一つ取得*/
	public Nutrition getMyFoodOne(int id);
	
	/*マイフード検索*/
	public List<Nutrition> getMyFoodSearch(String search);
	
	/*目標編集*/
	public void editGoal(Nutrition nutrition,String userId);
	
	/*マイフード編集*/
	public void editMyFood(Nutrition nutrition,int id);
	
	/*食事内容削除*/
	public void deleteDiary(int id);
	
	/*食事追加*/
	public void registerMyFood(String userId,Nutrition nutrition);
	
}
