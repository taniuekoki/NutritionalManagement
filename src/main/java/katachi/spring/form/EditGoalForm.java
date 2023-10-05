package katachi.spring.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EditGoalForm {
	@NotNull
	@Min(1)
	private Integer carbohydrates;
	@NotNull
	@Min(1)
	private Integer lipid;
	@NotNull
	@Min(1)
	private Integer protein;
	@NotNull
	@Min(1)
	private Integer calorie;
}
