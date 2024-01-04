package com.opylypiv.myhosp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignInActivity extends Activity {

    private FirebaseAuth auth;

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText nameEditText;
    private TextView toggleLoginSignUpTextView;
    private Button loginbutton;

    private ImageButton facebookbutton;
    private ImageButton googleButton;

    static int RC_SIGN_IN = 123;
    String TAG = "SignUp_Activity";

    private boolean loginModeActive;

    private FirebaseDatabase database;
    private DatabaseReference usersDatabaseReference;
    private CallbackManager mCallbackManager;

    String currentiddoctor;
    String currentidhosp;

    private Button activity_button_sign_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        auth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        toggleLoginSignUpTextView = findViewById(R.id.toggleLoginSignUpTextView);
        loginbutton = findViewById(R.id.loginSignUpButton);
        googleButton = findViewById(R.id.logingoogleButton);
        facebookbutton = findViewById(R.id.loginfacebookButton);
        mCallbackManager = CallbackManager.Factory.create();

        Intent intent = getIntent();
        currentiddoctor = intent.getStringExtra("iddoctor");
        currentidhosp = intent.getStringExtra("idhosp");

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_email = emailEditText.getText().toString().trim();
                String s_password = passwordEditText.getText().toString().trim();
                createuser(s_email, s_password);
            }
        });


        facebookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, FacebookSignIn.class);
                intent.putExtra("iddoctor", currentiddoctor + "");
                intent.putExtra("idhosp", currentidhosp + "");

                Log.d("iddoctor", currentiddoctor + "");
                Log.d("idhosp", currentidhosp + "");

                startActivity(intent);

            }
        });
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, GoogleSignInActivity.class);
                intent.putExtra("iddoctor", currentiddoctor + "");
                intent.putExtra("idhosp", currentidhosp + "");

                Log.d("iddoctor", currentiddoctor + "");
                Log.d("idhosp", currentidhosp + "");

                startActivity(intent);

            }
        });


    }

    public void createuser(String email, String password) {
        if (loginModeActive) {
            if (passwordEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(this, "Password must be at least 7 characters",
                        Toast.LENGTH_SHORT).show();
            } else if (emailEditText.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Please input your email",
                        Toast.LENGTH_SHORT).show();
            } else {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    Intent intent = new Intent(SignInActivity.this,
                                            DoctorList.class);
                                    intent.putExtra("userName", emailEditText.getText().toString().trim());
                                    startActivity(intent);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
            }

        } else {
            if (!passwordEditText.getText().toString().trim().equals(
                    repeatPasswordEditText.getText().toString().trim()
            )) {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            } else if (passwordEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(this, "Password must be at least 7 characters",
                        Toast.LENGTH_SHORT).show();
            } else if (emailEditText.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Please input your email",
                        Toast.LENGTH_SHORT).show();
            } else {

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            startActivity(new Intent(SignInActivity.this, DoctorList.class));
                            Log.i("Succes", "new user created");

                        } else {

                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            switch (errorCode) {

                                case "ERROR_INVALID_CUSTOM_TOKEN":
                                    Toast.makeText(SignInActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                    Toast.makeText(SignInActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_CREDENTIAL":
                                    Toast.makeText(SignInActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(SignInActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                    emailEditText.setError("The email address is badly formatted.");
                                    emailEditText.requestFocus();
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    Toast.makeText(SignInActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                    passwordEditText.setError("password is incorrect ");
                                    passwordEditText.requestFocus();
                                    passwordEditText.setText("");
                                    break;

                                case "ERROR_USER_MISMATCH":
                                    Toast.makeText(SignInActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_REQUIRES_RECENT_LOGIN":
                                    Toast.makeText(SignInActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    Toast.makeText(SignInActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(SignInActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                    emailEditText.setError("The email address is already in use by another account.");
                                    emailEditText.requestFocus();
                                    break;

                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                    Toast.makeText(SignInActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_DISABLED":
                                    Toast.makeText(SignInActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_TOKEN_EXPIRED":
                                    Toast.makeText(SignInActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_NOT_FOUND":
                                    Toast.makeText(SignInActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_USER_TOKEN":
                                    Toast.makeText(SignInActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    Toast.makeText(SignInActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    Toast.makeText(SignInActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                    passwordEditText.setError("The password is invalid it must 6 characters at least");
                                    passwordEditText.requestFocus();
                                    break;

                            }
                        }
                    }
                });
            }
        }
    }


    public void toggleLoginMode(View view) {

        if (loginModeActive) {
            loginModeActive = false;
            loginbutton.setText("Зареєструватмсь");
            toggleLoginSignUpTextView.setText("Увійти");
            repeatPasswordEditText.setVisibility(View.VISIBLE);

        } else {
            loginModeActive = true;
            loginbutton.setText("Увійти");
            toggleLoginSignUpTextView.setText("Зареєстуватись");
            repeatPasswordEditText.setVisibility(View.GONE);
        }

    }

    private void createUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(emailEditText.getText().toString().trim());
        usersDatabaseReference.push().setValue(user);
    }


    @Override
    public void onStart() {
        FirebaseUser currentUser = auth.getCurrentUser();
        super.onStart();
    }


}
