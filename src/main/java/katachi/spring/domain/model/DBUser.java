package katachi.spring.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class DBUser {
	@Id
	private String user;
	private String password;
	private String role;
}
