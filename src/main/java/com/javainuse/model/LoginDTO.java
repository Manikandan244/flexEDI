package com.javainuse.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class LoginDTO implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private int id;

	@Column(name = "name", nullable = false, updatable = false)
	private String loginName;

	@Column(name = "pswrd", nullable = false, updatable = false)
	private String loginPassword;

	public LoginDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
}
