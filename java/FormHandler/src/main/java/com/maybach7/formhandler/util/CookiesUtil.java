package com.maybach7.formhandler.util;

import com.maybach7.formhandler.entity.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class CookiesUtil {

    public static void setSessionCookie(HttpServletResponse resp, Session session) {
        if(session != null) {
            Cookie sessionId = new Cookie("session_id", session.getSessionId());
            sessionId.setMaxAge(60*60*24*7);
            sessionId.setPath("/");
            resp.addCookie(sessionId);
        } else {
            Cookie sessionId = new Cookie("session_id", null);
            sessionId.setMaxAge(0);
            sessionId.setPath("/");
            resp.addCookie(sessionId);
        }
    }

    public static void setCookie(HttpServletResponse resp, String name, String value, int maxAge) { // сохраняет одинарное значение cookie
        if (value != null) {
            String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie(name, encodedValue);
            cookie.setMaxAge(maxAge);
            cookie.setPath("/");
            resp.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            resp.addCookie(cookie);
        }
    }

    public static void setCookie(HttpServletResponse resp, String name, String value) {
        if (value != null) {
            String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie(name, encodedValue);
            cookie.setPath("/");
            resp.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            resp.addCookie(cookie);
        }
    }

    public static Optional<String> getCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    String encodedValue = cookie.getValue();
                    String decodedValue = URLDecoder.decode(encodedValue, StandardCharsets.UTF_8);
                    return Optional.ofNullable(decodedValue);
                }
            }
        }
        return Optional.empty();
    }

    public static void setCookieArray(HttpServletResponse resp, String name, String[] values, int maxAge) {
        if (values != null && values.length > 0) {
            String value = String.join(",", values);
            setCookie(resp, name, value, maxAge);
        } else {
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");

        }
    }

    public static void setCookieArray(HttpServletResponse resp, String name, String[] values) {
        if (values != null && values.length > 0) {
            String value = String.join(",", values);
            setCookie(resp, name, value);
        } else {
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");

        }
    }

    public static Optional<String[]> getCookieArray(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(name)) {
                    String encodedValue = cookie.getValue();
                    String decodedValue = URLDecoder.decode(encodedValue, StandardCharsets.UTF_8);
                    return Optional.ofNullable(decodedValue.split(","));
                }
            }
        }
        return Optional.empty();
    }
}