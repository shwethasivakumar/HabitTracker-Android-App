package com.example.habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class habits extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4;
    CardView c;
    Button fab1;
    Button pf,hb,hb1,hb2,hb3,hb4;
    ListView clv;
    FirebaseListAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);
        clv=(ListView)findViewById(R.id.clist);
        tv1 = (TextView) findViewById(R.id.t1);
        tv2 = (TextView) findViewById(R.id.t2);
        tv3 = (TextView) findViewById(R.id.t3);
        hb1=findViewById(R.id.hb1);
        hb2=findViewById(R.id.hb2);
        hb3=findViewById(R.id.hb3);
        hb4=findViewById(R.id.hb4);
        tv4 = (TextView) findViewById(R.id.t4);
        fab1 = (Button) findViewById(R.id.fab);
        pf = (Button) findViewById(R.id.prof);
        Bundle b=getIntent().getExtras();
        String uid=b.getString("uidn");
        Query query =FirebaseDatabase.getInstance().getReference().child("NewHabitAdding").child(uid).child("activities");
        FirebaseListOptions<datamodel> options = new FirebaseListOptions.Builder<datamodel>().setLayout(R.layout.mylist).setQuery(query, datamodel.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView hn = v.findViewById(R.id.ti);
                TextView hd = v.findViewById(R.id.sti);
                Button hb=v.findViewById(R.id.hb);
                datamodel dm = (datamodel) model;
                hn.setText(dm.getHabitname());
                hd.setText(dm.getHabitdescription());
                hb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), calendar.class);
                        intent.putExtra("uidn",uid);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        };
        clv.setAdapter(adapter);

        /*DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("datamodel");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    datamodel dm=snapshot1.getValue(datamodel.class);
                    String txt=dm.getTitle().toString();
                    list.add(snapshot1.getValue());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        hb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                intent.putExtra("uidn",uid);
                startActivity(intent);
                finish();
            }
        });

        hb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                intent.putExtra("uidn",uid);
                startActivity(intent);
                finish();
            }
        });
        hb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                intent.putExtra("uidn",uid);
                startActivity(intent);
                finish();
            }
        });
        hb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                intent.putExtra("uidn",uid);
                startActivity(intent);
                finish();
            }
        });

        hb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                intent.putExtra("uidn",uid);
                startActivity(intent);
                finish();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                intent.putExtra("uidn",uid);
                startActivity(intent);
                finish();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                intent.putExtra("uidn",uid);
                startActivity(intent);
                finish();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                intent.putExtra("uidn",uid);
                startActivity(intent);
                finish();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                intent.putExtra("uidn",uid);
                startActivity(intent);
                finish();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),habit2.class);
                intent.putExtra("zz",uid);
                startActivity(intent);
                finish();
            }
        });
        pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), profile.class);
                intent2.putExtra("uidn",uid);
                startActivity(intent2);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }}

