package com.example.fox.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class ChatAdapter extends ArrayAdapter <Message> {
    private String mUserId;
    public ChatAdapter(Context context, String userId, List<Message> messages) {
        super(context, 0, messages);
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_view, parent, false);
            final MyView myView = new MyView();
            myView.imageView = (ImageView)convertView.findViewById(R.id.chatImageView);
            myView.messageView = (TextView)convertView.findViewById(R.id.chatMessageView);
            myView.loginView = (TextView)convertView.findViewById(R.id.chatLoginView);
            convertView.setTag(myView);
        }
        final Message message = (Message)getItem(position);
        final MyView myView = (MyView)convertView.getTag();
        final ImageView imageView = myView.imageView;
        myView.messageView.setText(message.getBody());
        return convertView;
    }

    final class MyView {
        public ImageView imageView;
        public TextView messageView;
        public TextView loginView;
    }
}


