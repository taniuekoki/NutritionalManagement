package katachi.spring.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import katachi.spring.domain.model.DBUser;
import katachi.spring.domain.model.Nutrition;

@Mapper
public interface UserMapper {
	
	/*ログインユーザー取得*/
	public DBUser findLoginUser(String userName);
	
	/*目標取得*/
	public Nutrition getGoal(String userId);
	
	/*食事内容取得*/
	public List<Nutrition> getFoodList(String userId,Date date);
	
	/*摂取量取得*/
	public Nutrition getSumIntake(String userId,Date date);
	
	/*diaryテーブルに追加*/
	public void addDiary(String userId,Nutrition nutrition);
	
	/*一人分のマイフード取得*/
	public List<Nutrition> getMyFood(String userId);
	
	/*マイフード一つ取得*/
	public Nutrition getMyFoodOneRecord(int id);
	
	/*マイフード食事名から検索*/
	public List<Nutrition> getMyFoodSearchName(String search);
	
	/*一人の目標編集*/
	public void updateEditGoal(Nutrition nutrition,String userId);
	
	/*マイフードアップデート*/
	public void updateEditMyFood(Nutrition nutrition,int id);
	
	/*diary1レコード削除*/
	public void deleteDiaryOne(int id);
	
	/*マイフード登録*/
	public void addMyFood(String userId,Nutrition nutrition);
}
