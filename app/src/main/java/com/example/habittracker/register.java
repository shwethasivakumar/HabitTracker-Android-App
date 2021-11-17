package com.example.habittracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {
    TextView loginbtn;
    EditText ea;
    EditText un;
    TextView am,lr;
    private FirebaseAuth mAuth;
    EditText pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginbtn=findViewById(R.id.lgnbt);
        un=findViewById(R.id.editTextTextUsername);
        ea=findViewById(R.id.editTextTextEmailAddress);
        pwd=findViewById(R.id.editTextTextPassword);
        am=findViewById(R.id.already);
        lr=findViewById(R.id.redirect);
        mAuth = FirebaseAuth.getInstance();
        lr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),authlogin.class);
                startActivity(i);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtemail=ea.getText().toString();
                String txtpwd=pwd.getText().toString();
                if(TextUtils.isEmpty(txtemail)){
                    ea.setError( "Email is required!" );}
                else if(TextUtils.isEmpty(txtpwd)) {
                    pwd.setError( "Password is required!" );
                }
                else if(txtpwd.length()<6){
                    Toast.makeText(getApplicationContext(),"Password too short (6 or more characters required)",Toast.LENGTH_SHORT).show();

                }
                else{
                    registerUser(txtemail,txtpwd);
                    SharedPreferences sf=getSharedPreferences("myfile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=sf.edit();
                    //edit.clear(); // remove existing entries
                    edit.putString("username",un.getText().toString());
                    edit.commit();



                }







            }





        });


    }
      /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }*/


  private void registerUser(String txtemail, String txtpwd) {
    mAuth.createUserWithEmailAndPassword(txtemail,txtpwd).addOnCompleteListener(register.this,new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){

                Intent i=new Intent(getApplicationContext(),authlogin.class);
                startActivity(i);
            }
            else{
                Toast.makeText(getApplicationContext(),"Registration failed.",Toast.LENGTH_SHORT).show();
            }



        }
    });

    }

}