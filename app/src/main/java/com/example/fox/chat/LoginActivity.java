package com.example.fox.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends Activity {

    private EditText loginEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEnter = (EditText)findViewById(R.id.loginEnter);
    }

    public void sendLogin(View v) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.LOGIN_KEY, loginEnter.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
