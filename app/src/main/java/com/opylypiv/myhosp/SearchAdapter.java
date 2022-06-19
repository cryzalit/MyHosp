package com.opylypiv.myhosp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.getstream.avatarview.AvatarView;

public class SearchAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Doctor> doctor;
    ImageView searchimageprofile;
    StorageReference searchimagepathReference;


    public SearchAdapter(Context context, ArrayList<Doctor> doctors) {
        ctx = context;
        doctor = doctors;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    // кол-во элементов
    @Override
    public int getCount() {
        return doctor.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        Log.d("size", doctor.size() + "");

        return doctor.get(position);
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
            view = lInflater.inflate(R.layout.searchitem, parent, false);
        }

        Doctor d = getDoctor(position);
        ((TextView) view.findViewById(R.id.search_doctor_name)).setText(d.getFullname() + "");
        ((TextView) view.findViewById(R.id.search_doctor_spec)).setText(d.getSpec() + "");
        AvatarView imageprofile = convertView.findViewById(R.id.profile_image);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        searchimagepathReference = storageRef.child("hosp" + d.getIdhosp() + "/" + d.getId() + ".jpg");
        Picasso picassoInstance = new Picasso.Builder(ctx.getApplicationContext())
                .addRequestHandler(new FireBaseRequestHandler())
                .build();
        picassoInstance.load(searchimagepathReference + "").into(imageprofile);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", d.getId() + "");
                MainActivity.navController.navigate(R.id.fragmentDoctorProfile, bundle);

            }
        });

        return view;

    }


    // товар по позиции
    Doctor getDoctor(int position) {
        return ((Doctor) getItem(position));
    }


}

