package com.example.habittracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class calendar extends AppCompatActivity {

    CalendarPickerView calendarPickerView;
    Button bt, bt1;
    int n = 0, count = 0, ongoing = 1, finished = 1;
    String task = "0";
    TextView tv;
    String uid;
    List<String> dates;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        bt1 = findViewById(R.id.check);
        tv = (TextView) findViewById(R.id.d1);

        Bundle b = getIntent().getExtras();
        uid = b.getString("uidn");
        Date today = new Date();
        bt = findViewById(R.id.back);

        bt1 = findViewById(R.id.check);

        tv = (TextView) findViewById(R.id.d1);
        calendarPickerView = findViewById(R.id.calendar);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        calendarPickerView.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ic = new Intent(getApplicationContext(), habits.class);
                ic.putExtra("uidn", uid);
                startActivity(ic);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < n) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(calendar.this);
                    // Setting Dialog Title
                    alertDialog.setTitle("UPDATE...");
                    // Setting Dialog Message
                    alertDialog.setMessage("Did you finish your task today?");
                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.ic_baseline_update_24);

                    alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            count = count + 1;
                            Toast.makeText(getApplicationContext(), "You clicked on YES :   " + which, Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "You clicked on NO :   " + which, Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Setting Netural "Cancel" Button
                    alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // User pressed Cancel button. Write Logic Here
                            Toast.makeText(getApplicationContext(), "You clicked on Cancel :   " + which,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Showing Alert Message
                    alertDialog.show();
                } else {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(calendar.this);
                    // Setting Dialog Title
                    alertDialog.setTitle("FINISHED");
                    // Setting Dialog Message
                    alertDialog.setMessage("You have successfully finished your task");
                    alertDialog.setNeutralButton("EXIT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // User pressed Cancel button. Write Logic Here
                            Toast.makeText(getApplicationContext(), "You clicked on Exit :   " + which,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    ongoing = 0;
                    reference = FirebaseDatabase.getInstance().getReference().child("NewHabitAdding").child(uid).child("otask");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                reference.setValue(Integer.parseInt(snapshot.getValue().toString()) - 1)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Task uploaded successfully", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                            } else {
                                reference.setValue(0)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Task uploaded successfully", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }

                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("NewHabitAdding").child(uid).child("ctask");
                            reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        reference1.setValue(Integer.parseInt(snapshot.getValue().toString()) + 1)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Task uploaded successfully", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        reference1.setValue(1)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Task uploaded successfully", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }


                                    Toast.makeText(getApplicationContext(), "You finsihed " + count + "days of your task", Toast.LENGTH_SHORT).show();
                                    alertDialog.show();
                                    String value = tv.getText().toString();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }


                    });
                }
            }
        });

    }
    public void displayDates(View view) {
        List<Date> dates = calendarPickerView.getSelectedDates();
        n = dates.size();
        for (Date i : dates) {
            Toast.makeText(calendar.this, i.toString(), Toast.LENGTH_SHORT).show();

        }
        ongoing = 1;
        reference = FirebaseDatabase.getInstance().getReference().child("NewHabitAdding").child(uid).child("otask");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    reference.setValue(Integer.parseInt(snapshot.getValue().toString()) + 1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Task uploaded successfully", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                } else {
                    reference.setValue(1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Task uploaded successfully", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.setValue(ongoing)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Task uploaded successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

