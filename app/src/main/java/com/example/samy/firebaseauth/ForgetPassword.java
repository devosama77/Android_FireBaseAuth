package com.example.samy.firebaseauth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;



public class ForgetPassword extends Activity {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        editText=findViewById(R.id.edit_forget_password);
    }

    public void restPassword(View view) {

        String emailAddress = editText.getText().toString();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Password send to email",
                                    Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
