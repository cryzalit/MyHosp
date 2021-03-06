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

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CommentsListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Comment> comment;
    Picasso picassoInstance;
    FirebaseStorage storage;
    StorageReference storageRef;


    CommentsListAdapter(Context context, ArrayList<Comment> comments) {
        ctx = context;
        comment = comments;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        picassoInstance = new Picasso.Builder(ctx.getApplicationContext())
                .addRequestHandler(new FireBaseRequestHandler())
                .build();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
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
        Log.d("storage", storageRef.child("hosp" + c.getIdhosp() + "/" + c.getIddoctor() + ".jpg") + "");
        StorageReference pathReference = storageRef.child("hosp" + c.getIdhosp() + "/" + c.getIddoctor() + ".jpg");
        picassoInstance.load(pathReference + "").into((CircleImageView) view.findViewById(R.id.avatar_doctor));


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