package com.example.bus_ticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BuslistActivity extends AppCompatActivity {

    //private static final String TAG = BuslistActivity;
    private String  busName,travelsName,busNumber, from, to, Date, time, userID,busId;
    private RecyclerView recyclerView;


    private FirebaseDatabase db;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Query root;
    private CustomAdapter adapter;
    private ArrayList<CustomRowItem> list;
    private ValueEventListener valueEventListener;
    Calendar calendar;
    String currentDate, currentTime;
    String From,To,date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);
        getSupportActionBar().setTitle("Available Buses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

         String fromBus=intent.getStringExtra("FROM_BUS");
         String toBus=intent.getStringExtra("TO_BUS");
         String dateBus=intent.getStringExtra("DATE_BUS");
        calendar = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("d - M - yyyy");
       currentDate = currentDateFormat.format(calendar.getTime());
       SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
       currentTime = currentTimeFormat.format(calendar.getTime());

       db = FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/");
      //  root= db.getReference().child("BusDetails");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CustomRowItem>();
        adapter = new CustomAdapter(this, list);
        recyclerView.setAdapter(adapter);
        root= db.getReference().child("BusDetails").orderByChild("from").equalTo(fromBus);
        root.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
               if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                          // if (dataSnapshot.child("from").getValue(String.class).equalsIgnoreCase(fromBus) &&
                          //  dataSnapshot.child("to").getValue(String.class).equalsIgnoreCase(toBus)) {
                       // if(currentDate.equals(dateBus)) {
                             //time = dataSnapshot.child("time").getValue(String.class);
                             //   if(currentTime.compareTo(time)==0 || currentTime.compareTo(time)>0) {
                             // if(currentDate.equals(dateBus)) {
                             //  time = dataSnapshot.child("Time").getValue(String.class);
                             //     if (currentTime.compareTo(time) == 0 || currentTime.compareTo(time) < 0) {
                             busId = dataSnapshot.child("busId").getValue(String.class);
                             busName = dataSnapshot.child("travelsName").getValue(String.class);
                             busNumber = dataSnapshot.child("busNumber").getValue(String.class);
                             Date = dataSnapshot.child("date").getValue(String.class);
                             time = dataSnapshot.child("time").getValue(String.class);
                             from = dataSnapshot.child("from").getValue(String.class);
                             to = dataSnapshot.child("to").getValue(String.class);
                             list.add(new CustomRowItem(busId, busName, busNumber, Date, time, from, to));



                    }
                    adapter.notifyDataSetChanged();
                }

               else{
                    Toast.makeText(BuslistActivity.this, "Sorry!! No buses available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}