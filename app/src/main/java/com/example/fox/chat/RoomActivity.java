package com.example.fox.chat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class RoomActivity extends Activity {

    private static final String login = MainActivity.login;
    private static final String USER_ID = MainActivity.userId;
    private EditText editMessage;
    private final String LOG_TAG = RoomActivity.class.getName();
    private ListView listView;
    private ArrayList<Message> mMessages;
    private ChatAdapter mAdapter;
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ((TextView) findViewById(R.id.roomLoginView)).setText(login);
        setupMessagePosting();
    }

    private void setupMessagePosting() {
        editMessage = (EditText) findViewById(R.id.messageEnter);
        Button send = (Button) findViewById(R.id.sendButton);
        listView = (ListView) findViewById(R.id.listView);
        mMessages = new ArrayList<Message>();
        mAdapter = new ChatAdapter(RoomActivity.this, USER_ID, mMessages);
        listView.setAdapter(mAdapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editMessage.getText().toString();
                Message message = new Message();
                message.setBody(msg);
                message.setUserId(USER_ID);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d(LOG_TAG, "Succesfully saved message on Parse");
                            receiveMessages();
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
                editMessage.setText("");
            }
        });
    }

    private void receiveMessages() {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> list, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    listView.invalidate();
                } else {
                    Log.d(LOG_TAG, "Error while getting messages: " + e.getMessage());
                }
            }
        });
    }

}
