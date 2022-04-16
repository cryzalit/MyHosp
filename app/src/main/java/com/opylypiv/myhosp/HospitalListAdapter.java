package com.opylypiv.myhosp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HospitalListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Hospital> hospital;
    Double current_latitude;
    Double current_longtide;
    Location locationc = new Location("point C");
    Location locationh = new Location("point H");


    public HospitalListAdapter(Context context, Double latitude, Double longtidure, ArrayList<Hospital> hospitals) {
        ctx = context;
        hospital = hospitals;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        current_latitude = latitude;
        current_longtide = longtidure;
        locationc.setLatitude(current_latitude);
        locationc.setLongitude(current_longtide);

    }


    // кол-во элементов
    @Override
    public int getCount() {
        return hospital.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        Log.d("size", hospital.size() + "");

        return hospital.get(position);
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
            view = lInflater.inflate(R.layout.hospital_view, parent, false);
        }


        Hospital h = getHospital(position);
        ((TextView) view.findViewById(R.id.namaorganization)).setText(h.getNameorganization() + "");
        locationh.setLongitude(h.getLongitude());
        locationh.setLatitude(h.getLatitude());
        float distance = locationc.distanceTo(locationh);
        ((TextView) view.findViewById(R.id.distance)).setText(distance / 1000.0 + "");


        Log.d("distance", distance + "");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, Answer.class);
                intent.putExtra("id", h.getId() + "");
                Log.d("id", h.getId() + "");
                ctx.startActivity(intent);
            }
        });

        return view;

    }


    // товар по позиции
    Hospital getHospital(int position) {
        return ((Hospital) getItem(position));
    }


}

