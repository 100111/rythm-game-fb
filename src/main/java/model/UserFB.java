package model;

import util.GeneralUtil;

import com.restfb.types.User;

public class UserFB {
	private String id;
	private String usernamefb;
	private String usernameGame;
	private String email;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsernamefb() {
		return usernamefb;
	}
	public void setUsernamefb(String usernamefb) {
		this.usernamefb = usernamefb;
	}
	public String getUsernameGame() {
		return usernameGame;
	}
	public void setUsernameGame(String usernameGame) {
		this.usernameGame = usernameGame;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserFB() {
	}
	public UserFB(String id, String usernamefb, String email) {
		this.id = id;
		this.usernamefb = usernamefb;
		this.usernameGame = "";
		this.email = email;
	}
	public UserFB(User user) {
		this.id = user.getId();
		this.usernameGame = "";
		this.email = user.getEmail();
		
		//Check user
		this.usernamefb = user.getName();
		if(GeneralUtil.isEmpty(this.usernamefb)) {
			this.usernamefb = user.getUsername();
		}
	}
	
}
