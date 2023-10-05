package katachi.spring.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data

public class NutritionForm {
	private int id;
	
	@NotBlank
	private String mealName;
	
	private Integer carbohydrates;
	
	private Integer lipid;
	
	private Integer protein;
}
