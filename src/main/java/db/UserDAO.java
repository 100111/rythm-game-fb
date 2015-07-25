package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.UserFB;

public class UserDAO {
	public static Vector<UserFB> getAllUser() {
		Connection con = DBConnect.getConnection();
		Vector<UserFB> result = new Vector<UserFB>();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT * " +
					"FROM userfb ");
			ResultSet ketqua = cauTruyVan.executeQuery();
			String usernamefb;
			String usernameGame;
			String email;
			while (ketqua.next()) {
				usernamefb = ketqua.getString("usernamefb");
				usernameGame = ketqua.getString("usernameGame");
				email = ketqua.getString("email");
				UserFB user = new UserFB(ketqua.getString("id"), usernamefb, email);
				user.setUsernameGame(usernameGame);
				result.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static UserFB findUser(String userID) {
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT * " +
					"FROM userfb " +
					"WHERE id like '"+userID+"'");
			ResultSet ketqua = cauTruyVan.executeQuery();
			String usernamefb;
			String usernameGame;
			String email;
			while (ketqua.next()) {
				usernamefb = ketqua.getString("usernamefb");
				usernameGame = ketqua.getString("usernameGame");
				email = ketqua.getString("email");
				UserFB user = new UserFB(userID, usernamefb, email);
				user.setUsernameGame(usernameGame);
				return user;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void registerUser(UserFB user) {
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO userfb (id,usernamefb,usernameGame,email)" +
					"VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, user.getId());
			preparedStatement.setString(2, user.getUsernamefb());
			preparedStatement.setString(3, "");
			preparedStatement.setString(4	, user.getEmail());
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static Vector<UserFB> getRandomUserFromDatabase(int max){
		Connection con = DBConnect.getConnection();
		Vector<UserFB> result = new Vector<UserFB>();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(DBConnect.stringForRandomMySQL("userfb",max));
			ResultSet ketqua = cauTruyVan.executeQuery();
			String usernamefb;
			String usernameGame;
			String email;
			while (ketqua.next()) {
				usernamefb = ketqua.getString("usernamefb");
				usernameGame = ketqua.getString("usernameGame");
				email = ketqua.getString("email");
				UserFB user = new UserFB(ketqua.getString("id"), usernamefb, email);
				user.setUsernameGame(usernameGame);
				result.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Vector<String> checkIfUserInDB(Vector<String> friendIDs) {
		Connection con = DBConnect.getConnection();
		Vector<String> result = new Vector<String>();
		
		StringBuilder builder = new StringBuilder("(");
		for (String s : friendIDs) {
			builder.append(s);
			builder.append(",");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append(")");
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT id " +
					"FROM userfb " +
					"WHERE id IN "+builder.toString());
			ResultSet ketqua = cauTruyVan.executeQuery();
			while (ketqua.next()) {
				result.add(ketqua.getString("id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}	
