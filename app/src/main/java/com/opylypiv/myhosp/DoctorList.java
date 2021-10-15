package com.opylypiv.myhosp;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DoctorList extends AppCompatActivity {
    ExpandableListView listView;
    String line = "";
    ArrayList<ArrayList<Doctor>> doctorgroups = new ArrayList<ArrayList<Doctor>>(200);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        listView = findViewById(R.id.exListView);
        FirebaseApp.initializeApp(this);


        readDoctorsData();
        //Создаем набор данных для адаптера

    }
    public void readDoctorsData()  {
        InputStream is = getResources().openRawResource(R.raw.myhosp_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("Windows-1251")));
        ArrayList<Doctor> anesthesiologist = new ArrayList<Doctor>();
        ArrayList<Doctor> bacteriologist = new ArrayList<Doctor>();
        ArrayList<Doctor> cardiologist = new ArrayList<Doctor>();
        ArrayList<Doctor> CEO = new ArrayList<Doctor>();
        ArrayList<Doctor> dentist = new ArrayList<Doctor>();
        ArrayList<Doctor> dentist_orthodontist = new ArrayList<Doctor>();
        ArrayList<Doctor> dentist_surgeon = new ArrayList<Doctor>();
        ArrayList<Doctor> dentist_therapist = new ArrayList<Doctor>();
        ArrayList<Doctor> dermatologist = new ArrayList<Doctor>();
        ArrayList<Doctor> dermatovenereologist = new ArrayList<Doctor>();
        ArrayList<Doctor> usd = new ArrayList<Doctor>();
        ArrayList<Doctor> endocrinologist = new ArrayList<Doctor>();
        ArrayList<Doctor> endoscopist = new ArrayList<Doctor>();
        ArrayList<Doctor> ginekologist = new ArrayList<Doctor>();
        ArrayList<Doctor> infectious = new ArrayList<Doctor>();
        ArrayList<Doctor> medical_director = new ArrayList<Doctor>();
        ArrayList<Doctor> narcologist = new ArrayList<Doctor>();
        ArrayList<Doctor> nephrologist = new ArrayList<Doctor>();
        ArrayList<Doctor> neurologist = new ArrayList<Doctor>();
        ArrayList<Doctor> gynecologist = new ArrayList<Doctor>();
        ArrayList<Doctor> ophthalmologist = new ArrayList<Doctor>();
        ArrayList<Doctor> traumatologist = new ArrayList<Doctor>();
        ArrayList<Doctor> otolaryngologist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatric_gynecologist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatric_anesthesiologist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatric_cardiorheumatologist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatric_dentist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatric_dermatovenereologist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatric_endocrinologist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatric_neurologist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatric_ophthalmologist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatric_psychiatrist = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatrician = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatrician_hospital = new ArrayList<Doctor>();
        ArrayList<Doctor> pediatrician_neonatologist = new ArrayList<Doctor>();
        ArrayList<Doctor> physician = new ArrayList<Doctor>();
        ArrayList<Doctor> physiotherapist = new ArrayList<Doctor>();
        ArrayList<Doctor> radiologist = new ArrayList<Doctor>();
        ArrayList<Doctor> rheumatologist = new ArrayList<Doctor>();
        ArrayList<Doctor> surgeon = new ArrayList<Doctor>();
        ArrayList<Doctor> urologist = new ArrayList<Doctor>();




        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");
                Doctor doctor = new Doctor();
                List<Map<String, Doctor>> doctorlist = new ArrayList<>();

                doctor.setId(tokens[0]);
                doctor.setIdhosp(tokens[1]);
                doctor.setFullname(tokens[2]);
                doctor.setSpec(tokens[3]);
                doctor.setCodespec(tokens[4]);
                doctor.setPhotoURL(tokens[5]);
                doctor.setPoint(Double.parseDouble(tokens[6]));

            }
        }
        catch (IOException e){

        }



        doctorgroups.add(anesthesiologist);
        doctorgroups.add(surgeon);
        //Создаем адаптер и передаем context и список с данными
        DoctorListAdapter adapter = new DoctorListAdapter(getApplicationContext(), doctorgroups);
        listView.setAdapter(adapter);
    }
}