package com.example.habittracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class profile extends AppCompatActivity {
    TextView textView,tb;
    ImageView iv;
    TextView lo,oc,com;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        iv=findViewById(R.id.profpic);
        fab=findViewById(R.id.floatingActionButton);
        tb=findViewById(R.id.tskbt);
        lo=findViewById(R.id.logout);
        com=findViewById(R.id.completed);
        textView = findViewById(R.id.tv_name);
        oc=findViewById(R.id.oc);
        SharedPreferences sf=getSharedPreferences("myfile", Context.MODE_PRIVATE);
        String p=sf.getString("username","Your Name");
        textView.setText(p);
        Bundle b=getIntent().getExtras();
        String uid=b.getString("uidn");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("NewHabitAdding").child(uid).child("otask");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String task = (String) snapshot.getValue().toString();
                oc.setText(task);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("NewHabitAdding").child(uid).child("ctask");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = (String) snapshot.getValue().toString() ;
                com.setText(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), authlogin.class);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(profile.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c=new Intent(getApplicationContext(),calendar.class);
                c.putExtra("uidn",uid);
                startActivity(c);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Uri uri=data.getData();
        iv.setImageURI(uri);
    }


}