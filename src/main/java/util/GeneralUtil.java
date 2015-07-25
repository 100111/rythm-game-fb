package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class GeneralUtil {
	
	@SuppressWarnings("unchecked")
	public static String processJsonRequest(HttpServletRequest request){
		Map<String, String[]> paras = request.getParameterMap();
		for (Entry<String, String[]> e : paras.entrySet()) {
			String[] temp = e.getValue();
			if (temp[0].equals(""))
				return e.getKey();
		}
		return "";
	}
	
	public static TreeMap<Long, String> processJsonData(String json){
		Gson gson = new Gson();
		return gson.fromJson(json, TreeMap.class);
	}
	
	public static void redirect(String url, HttpServletResponse response) throws IOException{
		StringBuilder builder = new StringBuilder("<body><script>");
		builder.append("top.location.href = '");
		builder.append(url);
		builder.append("'</script></body>");
		  
		PrintWriter out = response.getWriter();
		out.print(builder.toString());
	}
	
	public static java.sql.Date convertUtilDtoSQLD(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	public static java.util.Date convertSQLDtoUtilD(java.sql.Date date) {
		return date;
	}
	
	public static String formatDatetime(java.util.Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return dateFormat.format(date);
	}
	
	public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }
	
}
