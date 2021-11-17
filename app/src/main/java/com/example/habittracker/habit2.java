package com.example.habittracker;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class habit2 extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener
{
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    TextView t1,t2,t3;
    EditText e1,e2;
    Button b1,b2,b3;
    String days[] = {"Sunday", "Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday"};
    Spinner sp1,sp2;
    HashMap<String,Object> map=new HashMap<>();
    String txthbttitle;
    Switch s;
    final Calendar myCalendar = Calendar. getInstance () ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit2);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        t1 = (TextView) findViewById(R.id.tv);
        t2 = (TextView) findViewById(R.id.tv_dateSelected);
        t3 = (TextView) findViewById(R.id.tv_timeSelected);
        e1 = (EditText) findViewById(R.id.et_habitTitle);
        e2 = (EditText) findViewById(R.id.et_habitDescription);
        b1 = (Button) findViewById(R.id.btn_pickDate);
        b2 = (Button) findViewById(R.id.btn_pickTime);
        b3 = (Button) findViewById(R.id.btn_confirm);
        s = (Switch) findViewById(R.id.switch1);
        Bundle b=getIntent().getExtras();
        String uid=b.getString("zz");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calendar c = Calendar.getInstance();
                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                Log.d("MainActivity", "" + myCalendar.get(Calendar.DATE));
                Log.d("MainActivity", "year = " + year +
                        " month = " + month +
                        " day = " + day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(habit2.this, habit2.this, year, month, day);
                datePickerDialog.show();
 /* if (android.os.Build.VERSION.SDK_INT >=
android.os.Build.VERSION_CODES.N) {
 DatePickerDialog datePickerDialog1 = new
DatePickerDialog(MainActivity.this);
 }*/
            txthbttitle=e1.getText().toString();
            String txtdes=e2.getText().toString();
            map.put("Habitname",txthbttitle);
            map.put("Habitdescription",txtdes);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            /*Calendar c = Calendar.getInstance();*/
            int hour = myCalendar.get(Calendar.HOUR);
            int minute = myCalendar.get(Calendar.MINUTE);

            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(habit2.this,
                        (timePicker1, i, i1) -> {
                            Log.d("MainActivity", "Got time - " + i + "-" + i);
                            t3.setText(i + ":" + i1);
                            String txttime=t3.getText().toString();
                            map.put("Time",txttime);
                        },
                        hour, minute, true);
                timePicker.show();


            }
        });
        b3.setOnClickListener(v -> {
            if(s.isChecked()){
                updateLabel();
            }

            if (e1.getText().toString().length() == 0) {
                e1.setError("Habit not entered");
                e1.requestFocus();
            }
            if (e2.getText().toString().length() == 0) {
                e2.setError("Description not entered");
                e2.requestFocus();
            }
           /* if (t2.getText().toString().equals("(not selected)")) {
                t2.setError("Date not selected");
                t2.requestFocus();
            }
            if (t3.getText().toString().equals("(not selected)")) {
                t3.setError("Time not selected");
                t3.requestFocus();
            }*/
            else {
                FirebaseDatabase.getInstance().getReference().child("NewHabitAdding").child(uid).child("activities").child(txthbttitle).updateChildren(map);
                Intent ha=new Intent(getApplicationContext(),habits.class);
                ha.putExtra("uidn",uid);
                startActivity(ha);

            }
        });
    }
    private void scheduleNotification (Notification notification , long delay) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , 10000 , pendingIntent) ;
    }
    private Notification getNotification (String content) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( e1.getText().toString() ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build();
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
        Log.d("MainActivity","Got date - "+ year+"-"+(month+1)+"-"+date);
        t2.setText(year+"-"+(month+1)+"-"+date);
        myCalendar.set(Calendar.YEAR,year);
        myCalendar.set(Calendar.MONTH,month);
        myCalendar.set(Calendar.DAY_OF_MONTH,date);
        // updateLabel();
    }
    private void updateLabel () {
        String myFormat = "dd/MM/yy" ;
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
        Date date = myCalendar.getTime() ;
        b1 .setText(sdf.format(date)) ;
        scheduleNotification(getNotification(b1.getText().toString()) , date.getTime()) ;
    }
}







