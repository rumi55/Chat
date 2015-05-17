package com.example.fox.chat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivity extends Activity {

    public final static String USER_ID_KEY = "userID";
    private static final int LOGIN_REQUEST_ID = 1;
    public static final String LOGIN_KEY = "LOGIN_KEY";
    private TextView loginView;
    public static final String DEF_VALUE = "defValue";
    public static String login;
    private final static String LOG_TAG = MainActivity.class.getName();
    private static final String APP_ID = "6JNux6OBnmf5F4yy3aoddLpOCebkhNM5vRCltzcV";
    private static final String CLIENT_KEY = "G5dUFTLJufAQgpm4miKAlPOgFbyCIJ4MLkAOCIZU";
    private SharedPreferences mPreferences;
    protected static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, APP_ID, CLIENT_KEY);
        setContentView(R.layout.main);
        loginView = (TextView)findViewById(R.id.LoginView);
        mPreferences = getPreferences(MODE_PRIVATE);
        login = mPreferences.getString(LOGIN_KEY, DEF_VALUE);
        if (ParseUser.getCurrentUser() != null) {
            startWithCurrentUser();
        } else {
            login();
        }
        if (login == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_ID);
            Log.d(LOG_TAG, "Starting activity for result");
        } else {
            Log.d(LOG_TAG, "Restored login = " + login);
        }
        loginView.setText(login);
    }

    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e != null) {
                    Log.d(LOG_TAG, "Error while logging Parse user");
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }

    private void startWithCurrentUser() {
        userId = ParseUser.getCurrentUser().getObjectId();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        login = data.getStringExtra(LOGIN_KEY);
        SharedPreferences.Editor ed = mPreferences.edit();
        ed.putString(LOGIN_KEY, login);
        ed.apply();
        loginView.setText(login);
        Log.d(LOG_TAG, "Login succesful. Login = " + login);
    }
}
