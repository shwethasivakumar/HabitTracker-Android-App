package com.example.habittracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class authlogin extends AppCompatActivity {
    TextView lb;
    EditText ea;
    private FirebaseAuth mAuth;
    EditText pwd;
    String uid;
    String te;
    private  FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authlogin);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        lb=findViewById(R.id.lgnbt);
        ea=findViewById(R.id.editTextTextEmailAddress);
        pwd=findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();
        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                te=ea.getText().toString();
                String pe=pwd.getText().toString();
                loginUser(te,pe);
            }
        });
    }

    private void loginUser(String te, String pe) {
        mAuth.signInWithEmailAndPassword(te,pe).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                uid =user.getUid();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference userNameRef = rootRef.child("NewHabitAdding").child(uid);
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()) {
                            FirebaseDatabase.getInstance().getReference().child("NewHabitAdding").child(uid).setValue(uid);

                        }
                        Intent i=new Intent(getApplicationContext(), habits.class);
                        i.putExtra("uidn",uid);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("error message", databaseError.getMessage()); //Don't ignore errors!
                    }

                };
                userNameRef.addListenerForSingleValueEvent(eventListener);
            }
        });
    }
}