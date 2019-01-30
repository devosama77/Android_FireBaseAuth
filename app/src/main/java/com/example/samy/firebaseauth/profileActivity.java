package com.example.samy.firebaseauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView=findViewById(R.id.text_id);
        button=findViewById(R.id.bn_logout);

         mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        textView.setText("Email is : "+currentUser.getEmail());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
