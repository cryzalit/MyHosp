package com.opylypiv.myhosp;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDoctorList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDoctorList extends Fragment {
    public static ArrayList<Doctor> alldoctors = new ArrayList<Doctor>();

    ExpandableListView listView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_HOSP = null;

    // TODO: Rename and change types of parameters
    private int idhosp;

    public FragmentDoctorList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDoctorList newInstance(String idhosp) {
        FragmentDoctorList fragment = new FragmentDoctorList();
        Bundle args = new Bundle();
        args.putString(ID_HOSP, idhosp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getdata();

        if (getArguments() != null) {
            if (getArguments() != null) {
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_list,
                container, false);

        listView = (ExpandableListView) view.findViewById(R.id.exListView);

        return view;
    }

    public void getdata() {
        idhosp = Integer.parseInt(getArguments().getString("idhosp"));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("idhosp", idhosp + "");


        db.collection("doctors")
                .whereEqualTo("idhosp", idhosp)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<ArrayList<Doctor>> doctorlist = new ArrayList<>();
                            DoctorListSort doctors = new DoctorListSort();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Doctor doctor = document.toObject(Doctor.class);
                                alldoctors.add(doctor);
                                doctorlist = doctors.getDoctorgroups(doctor);
                                for (Iterator<ArrayList<Doctor>> it = doctorlist.iterator(); it
                                        .hasNext(); ) {
                                    ArrayList<Doctor> elem = it.next();
                                    if (elem.isEmpty()) {
                                        it.remove();
                                    }
                                }

                            }
                            DoctorListAdapter adapter = new DoctorListAdapter(getApplicationContext(), doctorlist);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d("FIREBASE_ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    @Override
    public void onResume() {
        getdata();
        super.onResume();
    }
}



