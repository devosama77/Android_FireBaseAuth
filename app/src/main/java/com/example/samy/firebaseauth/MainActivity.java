package com.example.samy.firebaseauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "info";
    private FirebaseAuth mAuth;
   EditText edit_email,edit_pass;
   ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        edit_email=findViewById(R.id.edit_email_signup);
        edit_pass=findViewById(R.id.edit_pass_signup);
        progressBar=findViewById(R.id.progress_main_activity);
    }

    public void signUp(View view) {
        progressBar.setVisibility(View.VISIBLE);
        final String email=edit_email.getText().toString();
        String password=edit_pass.getText().toString();

         if((email.isEmpty()||email=="")||(
                 password.isEmpty()||password=="")){
             Toast.makeText(getApplicationContext(),
                     "Please add email and password",Toast.LENGTH_LONG).show();
             progressBar.setVisibility(View.GONE);
         }else {
             mAuth.createUserWithEmailAndPassword(email, password)
                     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             progressBar.setVisibility(View.GONE);
                             if (task.isSuccessful()) {
                                 // Sign in success, update UI with the signed-in user's information
                                 Log.d(TAG, "createUserWithEmail:success");
                                 FirebaseUser user = mAuth.getCurrentUser();
                                 user.sendEmailVerification()
                                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {
                                                 if (task.isSuccessful()) {
                                                     Log.d(TAG, "Email sent.");
                                                 }
                                             }
                                         });
                                 if(user.isEmailVerified()){
                                  Intent intent=new Intent(getApplicationContext(),Login.class);
                                  startActivity(intent);
                                 }else {
                                     Toast.makeText(getApplicationContext(), "please verify your email address",
                                             Toast.LENGTH_SHORT).show();
                                 }
                                 edit_email.setText("");
                                 edit_pass.setText("");
                             } else {
                                 // If sign in fails, display a message to the user.
                                 Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                 Toast.makeText(getApplicationContext(), "createUserWithEmail:failure.",
                                         Toast.LENGTH_SHORT).show();
                                 //    updateUI(null);
                             }

                         }
                     });
         }
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null&&user.isEmailVerified()){
            Intent intent=new Intent(this,profileActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
     //   Toast.makeText(getApplicationContext(),currentUser.getEmail()+"",Toast.LENGTH_LONG).show();
        updateUI(currentUser);
    }

    public void loginPage(View view) {
        Intent intent=new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }

    public void forgetPassword(View view) {
        Intent intent=new Intent(this,ForgetPassword.class);
        startActivity(intent);
    }
}
