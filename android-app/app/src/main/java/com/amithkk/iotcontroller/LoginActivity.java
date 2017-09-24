package com.amithkk.iotcontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference mRef= FirebaseDatabase.getInstance().getReference("users/1/u");
    DatabaseReference mRef2= FirebaseDatabase.getInstance().getReference("users/1/p");
    String uname;
    String pass;
    EditText u;
    EditText p;
    String uS,pS;

    public void onLogin(View view){

        uS = u.getText().toString();

        pS = p.getText().toString();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uname = dataSnapshot.getValue(String.class);
                mRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        pass= dataSnapshot.getValue(String.class);
                        if(uname.equals(uS) && pass.equals(pS)){
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(), "Username or Password Invalid", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button lb = (Button) findViewById(R.id.loginBtn);
        u=(EditText)findViewById(R.id.editText);
        p=(EditText)findViewById(R.id.editText2);
    }
}
