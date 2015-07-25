package util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class TempUtil {

	public static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }


    public static String findCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return "";
    }
    
    public static Map<String, String> processHashParam(String queryString) {
    	HashMap<String, String> resutl = new HashMap<String, String>();
    	
    	//Xoa dau # dau neu co
    	if(queryString.startsWith("#")) queryString = queryString.substring(1);
    	
    	//Xu ly
    	StringTokenizer tokenizerPair = new StringTokenizer(queryString, "&");
    	while (tokenizerPair.hasMoreElements()) {
			String pair = tokenizerPair.nextToken();
			StringTokenizer tokenizerValue = new StringTokenizer(pair, "=");
			resutl.put(tokenizerValue.nextToken(), tokenizerValue.nextToken());
			
		}
		return resutl;
	}
    
    public static void main(String[] args) {
		Map<String, String> temp = processHashParam("#access_token=CAAIJaAVxZBn4BAK8yXamaRuZAgpVtbLt4FXfao3ZCp6kOi2BvfMYrDvKigj1Hpdf2F3VSy6kbEYv1fhWQxSShq6tjFgsTSlnrxpMUUKzZBMTkeDWs6YHY2dRaF0DTfvFlbZCZB1L7mqmz2ZBYveCCqyj7LEMIZCeQxJL5SpeNqMCTwZDZD&expires_in=4262");
		
		for (Entry<String, String> string : temp.entrySet()) {
			System.out.println(string.getKey()+": "+string.getValue());
		}
		
	}
}
