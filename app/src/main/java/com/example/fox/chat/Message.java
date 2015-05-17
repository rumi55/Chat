package com.example.fox.chat;

import com.parse.ParseObject;


public class Message extends ParseObject {

    private static final String LOGIN_KEY = MainActivity.LOGIN_KEY;
    private static final String BODY_KEY = "body";
    private static final String USER_ID_KEY = MainActivity.USER_ID_KEY;

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getBody() {
        return getString(BODY_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setBody(String body) {
        put(BODY_KEY, body);
    }

    public void setLogin(String login) {
        put(LOGIN_KEY, login);
    }

    public String getLogin() {
        return getString(LOGIN_KEY);
    }
}
