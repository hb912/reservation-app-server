package dingulcamping.reservationapp.global.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieManager {

    public final static int ACCESS_EXP=30 * 60 * 1000;
    public final static int REFRESH_EXP=1000 * 60 * 60 * 24 * 14;

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge,
                                  Boolean httpsOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
