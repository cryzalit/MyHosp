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
    String currentlatitude;
    String currentlongtitude;

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
            currentlatitude = getArguments().getString(ARG_PARAM1);
            currentlongtitude = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

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

                                Double currentlatitude = MainActivity.currentlatitude;
                                Double currentlongtitude = MainActivity.currentlongtitude;


                                Hospital hospital = document.toObject(Hospital.class);
                                hospitallist.add(hospital);
                                HospitalListAdapter hla = new HospitalListAdapter(getActivity(), currentlatitude, currentlongtitude, hospitallist);
                                hospitallistview.setAdapter(hla);

                            }
                        }
                    }
                });

    }


    @Override
    public void onPause() {

        super.onPause();
    }

}