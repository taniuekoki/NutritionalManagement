package katachi.spring.domain.model;

import lombok.Data;

@Data
public class Nutrition {
	private int id;
	private String mealName;
	private int carbohydrates;
	private int lipid;
	private int protein;
	private int calorie;
}
