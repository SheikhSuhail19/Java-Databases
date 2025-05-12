package org.java.hibernate.primarykeystrategies;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.TableGenerator;

@Entity
public class UserTable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_table_gen")
	@TableGenerator(name = "user_table_gen", table = "id_gen_table", pkColumnName = "gen_name",
			valueColumnName = "gen_value", pkColumnValue = "user_id", allocationSize = 1)
	private Long id;
	private String name;

	public UserTable(String name) {
		this.name = name;
	}
}

