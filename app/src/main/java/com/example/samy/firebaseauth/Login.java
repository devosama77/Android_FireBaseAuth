package com.example.samy.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class Login extends AppCompatActivity {
    private static final String TAG = "info";
    EditText edit_email,edit_pass;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        edit_email=findViewById(R.id.edit_email_login);
        edit_pass=findViewById(R.id.edit_password_login);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progress_login);
    }

    public void login(View view) {
        progressBar.setVisibility(View.VISIBLE);
      String  email=edit_email.getText().toString();
      String pass=edit_pass.getText().toString();
      if(email.isEmpty()||pass.isEmpty()){
          Toast.makeText(getApplicationContext(),"Email or Password is required",Toast.LENGTH_LONG).show();
          progressBar.setVisibility(View.GONE);
      }else {
          mAuth.signInWithEmailAndPassword(email, pass)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          progressBar.setVisibility(View.GONE);
                          if (task.isSuccessful()) {
                              // Sign in success, update UI with the signed-in user's information
                              Log.d(TAG, "signInWithEmail:success");
                              FirebaseUser user = mAuth.getCurrentUser();
                              updateUI(user);
                          } else {
                              // If sign in fails, display a message to the user.
                              Log.w(TAG, "signInWithEmail:failure", task.getException());
                              Toast.makeText(Login.this, "Authentication failed.",
                                      Toast.LENGTH_SHORT).show();
                              updateUI(null);
                          }

                      }
                  });
      }

    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Intent intent=new Intent(this,profileActivity.class);
            startActivity(intent);
        }
    }
}
