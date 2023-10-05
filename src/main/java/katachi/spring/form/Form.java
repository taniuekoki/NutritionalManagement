package katachi.spring.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Form {
	
	@NotBlank
	private String userName;
	
	@NotBlank
	private String password;
}
