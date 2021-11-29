package com.opylypiv.myhosp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Answer extends AppCompatActivity {
    String iddocument;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        EditText etanswer = findViewById(R.id.etanswer);
        Button sendanswer = findViewById(R.id.sendanswer);
        Intent intent = getIntent();
        iddocument = intent.getStringExtra("id");
        Log.d("iddocument", iddocument);

        sendanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore answer = FirebaseFirestore.getInstance();
                Map<String, Object> answerdata = new HashMap<>();
                answerdata.put("answer", true);
                answerdata.put("textanswer", etanswer.getText().toString());
                answer.collection("comments").document(iddocument).update(answerdata).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                onBackPressed();


            }
        });
    }
}