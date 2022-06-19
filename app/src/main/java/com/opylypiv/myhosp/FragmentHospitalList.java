package com.opylypiv.myhosp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHospitalList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHospitalList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "currentlatitude";
    private static final String ARG_PARAM2 = "currentlongtitude";

    // TODO: Rename and change types of parameters
    private Double mParam1;
    private Double mParam2;

    ListView hospitallistview;
    Double currentlatitude;
    Double currentlongtitude;

    public FragmentHospitalList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment hospital_list.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHospitalList newInstance(Double param1, Double param2) {
        FragmentHospitalList fragment = new FragmentHospitalList();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, param1);
        args.putDouble(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentlatitude = getArguments().getDouble(ARG_PARAM1);
            currentlongtitude = getArguments().getDouble(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_list,
                container, false);

        hospitallistview = (ListView) view.findViewById(R.id.hospitallistview);
        getNearHospitals();


        return view;
    }

    public void getNearHospitals() {
        ArrayList<Hospital> hospitallist = new ArrayList<Hospital>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("hospital_data")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("succeschechfirebase", task.isSuccessful() + "");
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Hospital hospital = document.toObject(Hospital.class);
                                hospitallist.add(hospital);
                                HospitalListAdapter hla = new HospitalListAdapter(getActivity(), currentlatitude, currentlongtitude, hospitallist);
                                hospitallistview.setAdapter(hla);

                            }
                        }
                    }
                });
    }

    public void senddata() {
        FirebaseFirestore db_send = FirebaseFirestore.getInstance();
        String line;

        InputStream is = getResources().openRawResource(R.raw.myhosp_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("Windows-1251")));
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");

                Doctor doctor = new Doctor();
                doctor.setId(Integer.parseInt(tokens[0]));
                doctor.setIdhosp(Integer.parseInt(tokens[1]));
                doctor.setFullname(tokens[2]);
                doctor.setSpec(tokens[3]);
                doctor.setCodespec(tokens[4]);
                doctor.setPhotoURL(tokens[5]);
                doctor.setPoint(Integer.parseInt(tokens[6]));
                doctor.setPoints(Integer.parseInt(tokens[6]));
                doctor.setSumpoints(Integer.parseInt(tokens[8]));
                doctor.setDoctorUID(tokens[9]);
                db_send.collection("doctors").document(doctor.getId() + "")
                        .set(doctor, SetOptions.merge());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {

        super.onPause();
    }
}