package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

	public DBConnect() {}
	private static DBConnect instance;

	public static Connection getConnection() {
		Connection con = null;
		try {
			String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
			String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/rhythmjava?useUnicode=true&characterEncoding=UTF-8", "adminh4RlFlA", "aNpwYs9kMMRD");
//			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/music?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static DBConnect getInstance() {
		if (instance != null) {
			instance = new DBConnect();
		}
		return instance;
	}
	
	public static String stringForRandomMySQL(String tableName, int max){
		/*
		SELECT  *  FROM    (
		        SELECT  @cnt := COUNT(*) + 1,
		                @lim := 10
		        FROM    t_random
		        ) vars
		STRAIGHT_JOIN
		        ( SELECT  r.*,
		                @lim := @lim - 1
		        FROM    t_random r
		        WHERE   (@cnt := @cnt - 1)
		                AND RAND(20090301) < @lim / @cnt
		        ) i
		*/        
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT  *  FROM ( ");
		builder.append("SELECT  @cnt := COUNT(*) + 1, ");
		builder.append("@lim := "+max+" ");
		builder.append("FROM  "+ tableName+" ");
		builder.append(") vars ");
		builder.append("STRAIGHT_JOIN ");
		builder.append("( SELECT  r.*, ");
		builder.append("@lim := @lim - 1 ");
		builder.append("FROM    "+ tableName+" r ");
		builder.append("WHERE   (@cnt := @cnt - 1) ");
		builder.append("AND RAND(20090301) < @lim / @cnt ");
		builder.append(") i ");
		
		return builder.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(DBConnect.getConnection());
	}



}
