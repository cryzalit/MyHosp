package com.opylypiv.myhosp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class DoctorList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        ExpandableListView listView = (ExpandableListView)findViewById(R.id.exListView);

        //Создаем набор данных для адаптера
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<String> children1 = new ArrayList<String>();
        ArrayList<String> children2 = new ArrayList<String>();
        children1.add("Child_1");
        children1.add("Child_2");
        groups.add(children1);
        children2.add("Child_1");
        children2.add("Child_2");
        children2.add("Child_3");
        groups.add(children2);
        //Создаем адаптер и передаем context и список с данными
        DoctorListAdapter adapter = new DoctorListAdapter(getApplicationContext(), groups);
        listView.setAdapter(adapter);
    }
}