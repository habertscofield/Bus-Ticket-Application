package com.example.bus_ticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConfirmTicket extends AppCompatActivity {
    private Button homeButton;
    private ArrayList<Integer> mSelectedSeats;
    private String mBusId;

    String total, seats, nameBus, dateBus,fromBus,toBus,timeBus,issueDate,issueTime,userId,message;
    String firstName,lastName, phoneNo;
    private TextView busNameTextView, journeyDateTextView, ticketIDTextView,busToTextView,busFromTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReferencetwo;
    private FirebaseUser firebaseUser;
    Calendar calendar;
    Map<String,String> ticketDetailsMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_ticket);
        getSupportActionBar().setTitle("Booking Complete");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mBusId = getIntent().getStringExtra("BUS_ID");
       Intent intent = getIntent();
        calendar = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd - M - yyyy");
        issueDate = currentDateFormat.format(calendar.getTime());
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
        issueTime = currentTimeFormat.format(calendar.getTime());
        fromBus=getIntent().getStringExtra("FROM_BUS");
        toBus=getIntent().getStringExtra("TO_BUS");
         dateBus=getIntent().getStringExtra("DATE_BUS");
        nameBus = getIntent().getStringExtra("NAME_BUS");
        total=getIntent().getStringExtra("TOTALCOST");
        seats=getIntent().getStringExtra("TOTALSEAT");
        mSelectedSeats = getIntent().getIntegerArrayListExtra("SEATSET");
        timeBus = getIntent().getStringExtra("TIME_BUS");
        message = getIntent().getStringExtra("noti");
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        firebaseAuth = FirebaseAuth.getInstance();


        ticketDetailsMap.put("busName",nameBus);
        ticketDetailsMap.put("from",fromBus);
        ticketDetailsMap.put("to",toBus);
        ticketDetailsMap.put("journeyDate",dateBus);
        ticketDetailsMap.put("time",timeBus);

        ticketDetailsMap.put("TotalCost",total);
        ticketDetailsMap.put("seats",seats);
        ticketDetailsMap.put("seatNo", String.valueOf((mSelectedSeats)));
        ticketDetailsMap.put("issueDate",issueDate);
        ticketDetailsMap.put("issueTime",issueTime);

        busNameTextView = findViewById(R.id.busName);
        journeyDateTextView = findViewById(R.id.busJourneyDateId);
        ticketIDTextView = findViewById(R.id.ticketID);


        databaseReference = FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/").getReference("users").child(userId).child("Tickets");
        String key = databaseReference.push().getKey();
        ticketDetailsMap.put("ticketID",key);
        databaseReference.child(key).setValue(ticketDetailsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {

                    }

                // Toast.makeText(BookingFinish.this, "Tickets database Created ", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                // Toast.makeText(BookingFinish.this, "Tickets Database creation failed ", Toast.LENGTH_SHORT).show();
            }
        });

        busNameTextView.setText(nameBus);
        journeyDateTextView.setText(dateBus);
        ticketIDTextView.setText(key);


        homeButton = findViewById(R.id.homeButtonId);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
