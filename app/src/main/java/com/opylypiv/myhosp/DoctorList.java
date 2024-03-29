package com.opylypiv.myhosp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
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
import java.util.Iterator;

public class DoctorList extends Activity {
    ExpandableListView listView;
    String line = "";
    String user_name;
    String currentUID;

    String deepLink = null;

    FirebaseFirestore db;
    ArrayList<ArrayList<Doctor>> doctorgroups = new ArrayList<>();
    public static ArrayList<Doctor> alldoctors = new ArrayList<Doctor>();

    ArrayList<Doctor> anesthesiologist = new ArrayList<Doctor>();
    ArrayList<Doctor> bacteriologist = new ArrayList<Doctor>();
    ArrayList<Doctor> cardiologist = new ArrayList<Doctor>();
    ArrayList<Doctor> ceo = new ArrayList<Doctor>();
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

    FirebaseUser user;
    static boolean isDoctor;

    String idhosp;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        intent = getIntent();
        getInvitation();
        listView = findViewById(R.id.exListView);

        if (user != null) {
            String name = user.getDisplayName();
            Log.d("nameuser", name);
        } else {
            Log.d("nameuser", "null");

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Операции для выбранного пункта меню
        switch (item.getItemId()) {
            case R.id.profile:

            case R.id.massages:
                return true;
            case R.id.singoutitem:
                item.setTitle("ВЫЙТИ");
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DoctorList.this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.profile);
        if (user != null) {
            user.getDisplayName();
            item.setTitle(user_name);
            invalidateOptionsMenu();
        }
        return true;
    }


    public void senddata() {
        FirebaseFirestore db_send = FirebaseFirestore.getInstance();

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
                db_send.collection("hosp_1").document(doctor.getId() + "")
                        .set(doctor, SetOptions.merge());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        FirebaseApp.initializeApp(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user_name = user.getDisplayName();
            invalidateOptionsMenu();
        }
        super.onStart();
    }

    public void getInvitation() {

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink().getLastPathSegment() + "";
                            idhosp = deepLink.replace("hosp", "");
                            Log.i("hospital_id", idhosp + "");
                            getdata();

                        } else {
                            Bundle extras = getIntent().getExtras();
                            if (extras != null) {
                                idhosp = extras.getString("idhosp");
                                Log.i("hospital_id", idhosp + "");
                                getdata();

                            }
                        }

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "getDynamicLink:onFailure", e);
                    }
                });
    }

    public void getdata() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("hosp_" + idhosp)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Doctor doctor = document.toObject(Doctor.class);
                                alldoctors.add(doctor);
                                switch (doctor.getCodespec()) {
                                    case "anesthesiologist":
                                        anesthesiologist.add(doctor);
                                        break;
                                    case "bacteriologist":
                                        bacteriologist.add(doctor);
                                        break;
                                    case "cardiologist":
                                        cardiologist.add(doctor);
                                        break;
                                    case "CEO":
                                        ceo.add(doctor);
                                        break;
                                    case "dentist":
                                        dentist.add(doctor);
                                        break;
                                    case "dermatologist":
                                        dermatologist.add(doctor);
                                        break;
                                    case "dermatovenereologist":
                                        dermatovenereologist.add(doctor);
                                        break;
                                    case "endocrinologist":
                                        endocrinologist.add(doctor);
                                        break;
                                    case "endoscopist":
                                        endoscopist.add(doctor);
                                        break;
                                    case "ginekologist":
                                        ginekologist.add(doctor);
                                        break;
                                    case "infectious":
                                        infectious.add(doctor);
                                        break;
                                    case "medical_director":
                                        medical_director.add(doctor);
                                        break;
                                    case "narcologist":
                                        narcologist.add(doctor);
                                        break;
                                    case "nephrologist":
                                        nephrologist.add(doctor);
                                        break;
                                    case "neurologist":
                                        neurologist.add(doctor);
                                        break;
                                    case "ophthalmologist":
                                        ophthalmologist.add(doctor);
                                        break;
                                    case "traumatologist":
                                        traumatologist.add(doctor);
                                        break;
                                    case "otolaryngologist":
                                        otolaryngologist.add(doctor);
                                        break;
                                    case "pediatric_gynecologist":
                                        pediatric_gynecologist.add(doctor);
                                        break;
                                    case "pediatric_anesthesiologist":
                                        pediatric_anesthesiologist.add(doctor);
                                        break;
                                    case "pediatric_cardiorheumatologist":
                                        pediatric_cardiorheumatologist.add(doctor);
                                        break;
                                    case "pediatric_dentist":
                                        pediatric_dentist.add(doctor);
                                        break;
                                    case "pediatric_dermatovenereologist":
                                        pediatric_dermatovenereologist.add(doctor);
                                        break;
                                    case "pediatric_endocrinologist":
                                        pediatric_endocrinologist.add(doctor);
                                        break;
                                    case "pediatric_neurologist":
                                        pediatric_endocrinologist.add(doctor);
                                        break;
                                    case "pediatric_ophthalmologist":
                                        pediatric_endocrinologist.add(doctor);
                                        break;
                                    case "pediatric_psychiatrist":
                                        pediatric_psychiatrist.add(doctor);
                                        break;
                                    case "pediatrician":
                                        pediatrician.add(doctor);
                                        break;
                                    case "pediatrician_hospital":
                                        pediatrician.add(doctor);
                                        break;
                                    case "pediatrician_neonatologist":
                                        pediatrician_neonatologist.add(doctor);
                                        break;
                                    case "physician":
                                        physician.add(doctor);
                                        break;
                                    case "physiotherapist":
                                        physiotherapist.add(doctor);
                                        break;
                                    case "radiologist":
                                        radiologist.add(doctor);
                                        break;
                                    case "rheumatologist":
                                        rheumatologist.add(doctor);
                                        break;
                                    case "surgeon":
                                        surgeon.add(doctor);
                                        break;
                                    case "urologist":
                                        urologist.add(doctor);
                                        break;
                                }

                            }
                            doctorgroups.add(anesthesiologist);
                            doctorgroups.add(bacteriologist);
                            doctorgroups.add(ceo);
                            doctorgroups.add(dentist);
                            doctorgroups.add(dermatologist);
                            doctorgroups.add(dermatovenereologist);
                            doctorgroups.add(endocrinologist);
                            doctorgroups.add(endoscopist);
                            doctorgroups.add(gynecologist);
                            doctorgroups.add(nephrologist);
                            doctorgroups.add(neurologist);
                            doctorgroups.add(ophthalmologist);
                            doctorgroups.add(traumatologist);
                            doctorgroups.add(otolaryngologist);
                            doctorgroups.add(pediatric_gynecologist);
                            doctorgroups.add(pediatric_anesthesiologist);
                            doctorgroups.add(pediatric_cardiorheumatologist);
                            doctorgroups.add(pediatric_dentist);
                            doctorgroups.add(pediatric_dermatovenereologist);
                            doctorgroups.add(pediatric_endocrinologist);
                            doctorgroups.add(pediatric_neurologist);
                            doctorgroups.add(pediatric_ophthalmologist);
                            doctorgroups.add(pediatric_psychiatrist);
                            doctorgroups.add(pediatrician);
                            doctorgroups.add(pediatrician_hospital);
                            doctorgroups.add(pediatrician_neonatologist);
                            doctorgroups.add(physician);
                            doctorgroups.add(physiotherapist);
                            doctorgroups.add(radiologist);
                            doctorgroups.add(rheumatologist);
                            doctorgroups.add(surgeon);
                            doctorgroups.add(urologist);
                            for (Iterator<ArrayList<Doctor>> it = doctorgroups.iterator(); it
                                    .hasNext(); ) {
                                ArrayList<Doctor> elem = it.next();
                                if (elem.isEmpty()) {
                                    it.remove();
                                }
                            }
                            for (int i = 150; i > 0; --i) {
                                if (DoctorList.alldoctors.get(i).getDoctorUID().equals(currentUID)) {
                                    user_name = DoctorList.alldoctors.get(i).getFullname();
                                    isDoctor = true;
                                    Log.d("username", user_name);
                                }
                            }

                        }

                        DoctorListAdapter adapter = new DoctorListAdapter(getApplicationContext(), doctorgroups);
                        listView.setAdapter(adapter);

                    }
                });

    }
}

