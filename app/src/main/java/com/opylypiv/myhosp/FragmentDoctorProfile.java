package com.opylypiv.myhosp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDoctorProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDoctorProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "idhosp";
    private static final String ARG_PARAM2 = "iddoc";

    // TODO: Rename and change types of parameters
    private String idhosp;
    private String iddoc;

    int currentiddoctor;

    String namedoctor;
    String imagereference;
    String currentuser;

    FirebaseStorage storage;
    StorageReference storageRef;
    TextView name;
    TextView profession;
    RatingBar point;
    CircleImageView avatarProfile;

    String user_name;
    String user_email;
    Uri user_photoUrl;

    EditText textcomment;
    RatingBar setpoint;

    Doctor currentdoc;

    FirebaseFirestore db_profile;

    ListView listcomments;
    ArrayList<Comment> comments;
    Comment c;
    static FirebaseUser user;
    String uiduser;
    Button send_comment;
    ImageButton messeges;


    public FragmentDoctorProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDoctorProfiel.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDoctorProfile newInstance(String param1, String param2) {
        FragmentDoctorProfile fragment = new FragmentDoctorProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idhosp = getArguments().getString(ARG_PARAM1);
            iddoc = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_profile,
                container, false);
        db_profile = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        name = view.findViewById(R.id.name_doctor_profile);
        profession = view.findViewById(R.id.profession_doctor_profile);
        point = view.findViewById(R.id.ratingbar_doctror_profile);
        avatarProfile = view.findViewById(R.id.photo_doctor_profile);
        send_comment = view.findViewById(R.id.send);
        textcomment = view.findViewById(R.id.comment);
        setpoint = (RatingBar) view.findViewById(R.id.setRating);
        listcomments = view.findViewById(R.id.listcomments);
        messeges = view.findViewById(R.id.massages);

        comments = new ArrayList<Comment>();

        imagereference = getArguments().getString("imagereference");

        Picasso picassoInstance = new Picasso.Builder(this.getActivity().getApplicationContext())
                .addRequestHandler(new FireBaseRequestHandler())
                .build();
        getProfile();
        getComments();
        getUserProfile();

        return view;

    }

    public void getProfile() {

        Picasso picassoInstance = new Picasso.Builder(this.getActivity().getApplicationContext())
                .addRequestHandler(new FireBaseRequestHandler())
                .build();
        db_profile.collection("doctors")
                .whereEqualTo("id", iddoc)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    currentdoc = document.toObject(Doctor.class);
                                    name.setText(currentdoc.getFullname());
                                    profession.setText(currentdoc.getSpec());
                                    point.setRating(Float.parseFloat(currentdoc.getPoint() + ""));
                                    point.setMax(5);
                                    point.setStepSize(0.1f);
                                    picassoInstance.load(imagereference + "").into(avatarProfile);


                                    Log.d("CURRENTDOG", currentdoc.getFullname());
                                } else {
                                    Log.d("FIREBASEPROBLEM", "get failed with ", task.getException());

                                }
                            }
                        }
                    }


                });
    }



    public void getComments() {
        c = new Comment();
        db_profile.collection("comments").whereEqualTo("iddoctor", currentiddoctor)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                c = document.toObject(Comment.class);
                                Log.d("SCS", "DocumentSnapshot data: " + document.getData());
                                comments.add(c);

                            }
                            CommentsListAdapter cma = new CommentsListAdapter(getActivity(), comments);
                            listcomments.setAdapter(cma);

                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }

                    }


                });

    }


    public void sendcomment(View v) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        send_comment.setTextColor(Color.parseColor("#f6ff00"));
        Comment comment = new Comment();
        comment.setId(timestamp.getTime());
        comment.setText(textcomment.getText().toString().trim());
        comment.setPoint(setpoint.getRating() + "");
        comment.setIdhosp(currentdoc.getIdhosp());
        comment.setIddoctor(currentiddoctor);
        comment.setIduser(currentuser);
        comment.setTextanswer(null);

        db_profile.collection("comments").document(timestamp.getTime() + "")
                .set(comment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Succes", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ErrorSENDCOMMENT", "Error writing document", e);
                    }
                });
        setSetpoint();
        getActivity().onBackPressed();


    }

    public void setSetpoint() {

        DocumentReference docRef = db_profile.collection("hosp_" + idhosp).document(iddoc);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        currentdoc = document.toObject(Doctor.class);
                        Log.d("VSEOK", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("ERROR", "No such document");
                    }
                } else {
                    Log.d("ERROR", "get failed with ", task.getException());
                }
            }
        });

        FirebaseFirestore answer = FirebaseFirestore.getInstance();
        Map<String, Object> newpoint = new HashMap<>();
        newpoint.put("sumpoints", currentdoc.getSumpoints() + setpoint.getRating());
        newpoint.put("points", currentdoc.getPoints() + 1);
        answer.collection("hosp_" +idhosp).document(currentiddoctor + "").update(newpoint).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Succes", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Error", "Error writing document", e);
                    }
                });


    }

    public void goLogin() {

        Intent intent = new Intent(getActivity(), SignInActivity.class);
        intent.putExtra("iddoctor", currentiddoctor + "");
        intent.putExtra("idhosp", idhosp + "");

        Log.d("iddoctor", currentiddoctor + "");
        Log.d("idhosp", idhosp + "");

        startActivity(intent);
    }

    public void goMesseges(View v) {

        Intent msg = new Intent(getActivity(), MessagesActivity.class);
        msg.putExtra("iddoctor", currentiddoctor + "");
        msg.putExtra("idhosp", idhosp + "");
        msg.putExtra("currentnamedoctor", namedoctor);

        Log.d("iddoctor", currentiddoctor + "");
        Log.d("idhosp", idhosp + "");

        startActivity(msg);

    }

    public void updateUI(FirebaseUser fuser) {

        Log.d("iddoctor", currentiddoctor + "");
        Log.d("idhosp", idhosp + "");

    }


    public void getUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user_name = user.getDisplayName();
            user_email = user.getEmail();
            user_photoUrl = user.getPhotoUrl();
            currentuser = user.getUid();
            textcomment.setEnabled(true);
            textcomment.setHint("Залиште відгук про лікаря");
            send_comment.setText("ВІДПРАВИТИ");
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
            messeges.setVisibility(View.VISIBLE);

        } else {
            setpoint.setEnabled(false);
            textcomment.setEnabled(false);
            textcomment.setHint("Увійдіть. щоб залишити відгук");
            send_comment.setText("УВІЙТИ");
            messeges.setVisibility(View.GONE);

            send_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goLogin();
                }
            });
        }
        // [END get_user_profile]
    }

}