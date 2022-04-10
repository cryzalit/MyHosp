package com.opylypiv.myhosp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class DoctorProfile extends AppCompatActivity {
    int currentiddoctor;
    String currentidhosp;
    String currentnamedoctor;
    String currentUID;
    String currentspec;
    String currentpoint;
    String currentuser;
    String currentphoto;

    String user_name;
    String user_email;
    Uri user_photoUrl;

    EditText textcomment;
    RatingBar setpoint;

    Doctor currentdoc;
    FirebaseFirestore db_sendcoment;
    ListView listcomments;
    ArrayList<Comment> comments;
    Comment c;
    static FirebaseUser user;
    String uiduser;
    Button send_comment;
    ImageButton messeges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db_sendcoment = FirebaseFirestore.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        setContentView(R.layout.activity_doctor_profile);
        TextView name = findViewById(R.id.name_doctor_profile);
        TextView profession = findViewById(R.id.profession_doctor_profile);
        RatingBar point = findViewById(R.id.ratingbar_doctror_profile);
        CircleImageView avatarProfile = findViewById(R.id.photo_doctor_profile);
        send_comment = findViewById(R.id.send);
        textcomment = findViewById(R.id.comment);
        setpoint = findViewById(R.id.setRating);
        listcomments = findViewById(R.id.listcomments);
        messeges = findViewById(R.id.massages);

        comments = new ArrayList<Comment>();

        Intent intent = getIntent();
        currentiddoctor = Integer.parseInt(intent.getStringExtra("iddoctor"));
        currentidhosp = intent.getStringExtra("idhosp");
        currentUID = intent.getStringExtra("UID");

        Picasso picassoInstance = new Picasso.Builder(this.getApplicationContext())
                .addRequestHandler(new FireBaseRequestHandler())
                .build();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uiduser = user.getUid();
        }

        getComments();

        for (int i = 150; i > 0; --i) {
            if (DoctorList.alldoctors.get(i).getId() == currentiddoctor) {
                Log.d("Error", currentiddoctor + "");
                currentdoc = DoctorList.alldoctors.get(i);
                currentnamedoctor = DoctorList.alldoctors.get(i).getFullname() + "";
                name.setText(DoctorList.alldoctors.get(i).getFullname() + "");
                profession.setText(DoctorList.alldoctors.get(i).getSpec() + "");
                point.setRating(Float.parseFloat(DoctorList.alldoctors.get(i).getPoint() + ""));
                point.setStepSize(0.1f);

                StorageReference pathReference = storageRef.child("hosp" + DoctorList.alldoctors.get(i).getIdhosp() + "/" + DoctorList.alldoctors.get(i).getId() + ".jpg");
                picassoInstance.load(pathReference + "").into(avatarProfile);

                return;

            }
        }
    }

    public void getComments() {
        c = new Comment();
        db_sendcoment.collection("comments").whereEqualTo("iddoctor", currentiddoctor)
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
                            CommentsListAdapter cma = new CommentsListAdapter(DoctorProfile.this, comments);
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

        db_sendcoment.collection("comments").document(timestamp.getTime() + "")
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
        onBackPressed();


    }

    public void setSetpoint() {

        DocumentReference docRef = db_sendcoment.collection("hosp_" + currentidhosp).document(currentiddoctor + "");
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
        answer.collection("hosp_" + currentidhosp).document(currentiddoctor + "").update(newpoint).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        Intent intent = new Intent(this, SignInActivity.class);
        intent.putExtra("iddoctor", currentiddoctor + "");
        intent.putExtra("idhosp", currentidhosp + "");
        intent.putExtra("UID", currentUID + "");

        Log.d("iddoctor", currentiddoctor + "");
        Log.d("idhosp", currentidhosp + "");
        Log.d("UID", currentUID + "");

        startActivity(intent);
    }

    public void goMesseges(View v) {

        Intent msg = new Intent(this, MessagesActivity.class);
        msg.putExtra("iddoctor", currentiddoctor + "");
        msg.putExtra("idhosp", currentidhosp + "");
        msg.putExtra("UID", currentUID);
        msg.putExtra("currentnamedoctor", currentnamedoctor);

        Log.d("iddoctor", currentiddoctor + "");
        Log.d("idhosp", currentidhosp + "");
        Log.d("UID", currentUID + "");

        startActivity(msg);

    }

    public void updateUI(FirebaseUser fuser) {

        Log.d("iddoctor", currentiddoctor + "");
        Log.d("idhosp", currentidhosp + "");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Операции для выбранного пункта меню
        switch (item.getItemId()) {
            case R.id.massages:
                item.setTitle(user_name);
            case R.id.singoutitem:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DoctorProfile.this, DoctorList.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    protected void onStart() {
        getUserProfile();
        super.onStart();
    }


}