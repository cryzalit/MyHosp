package com.opylypiv.myhosp;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;


public class CommentsListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Comment> comment;

    CommentsListAdapter(Context context, ArrayList<Comment> comments) {
        ctx = context;
        comment = comments;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return comment.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        Log.d("size", comment.size() + "");

        return comment.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.comment_view, parent, false);
        }

        Comment c = getComment(position);
        ((TextView) view.findViewById(R.id.author_name)).setText(c.getIduser() + "");
        ((TextView) view.findViewById(R.id.comment)).setText(c.getText());
        ((RatingBar) view.findViewById(R.id.pointofexistcomment)).setRating(Float.parseFloat(c.getPoint()));
        Button answer = view.findViewById(R.id.answerbutton);

        Log.d("commment", c.getId() + "");

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, Answer.class);
                intent.putExtra("id", c.getId() + "");
                Log.d("id", c.getId() + "");
                ctx.startActivity(intent);
            }
        });

        if (c.isAnswer()) {
            Log.d("answer", c.getTextanswer() + "");
            Log.d("isanswer", c.isAnswer() + "");

            ((TextView) view.findViewById(R.id.answer_comment)).setText(c.getTextanswer());


        } else {
            view.findViewById(R.id.ll_answer).setVisibility(View.GONE);
        }

        return view;

    }


    // товар по позиции
    Comment getComment(int position) {
        return ((Comment) getItem(position));
    }


}