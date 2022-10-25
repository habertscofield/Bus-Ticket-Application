package com.example.bus_ticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.Calendar;

public class BuslistActivity extends AppCompatActivity {

    //private static final String TAG = BuslistActivity;
    private String  busName,travelsName,busNumber, from, to,fare,Date, time,date, userID,busId;
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
    private  String mBusId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);
        getSupportActionBar().setTitle("Available Buses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mBusId = extras.getString("BUS_ID");
         String fromBus=intent.getStringExtra("FROM_BUS").toString();
         String toBus=intent.getStringExtra("TO_BUS").toString();
         String dateBus=intent.getStringExtra("DATE_BUS").toString();
        calendar = Calendar.getInstance();

       db = FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/");
      //  root = db.getReference().child("BusDetails").orderByChild("from").equalTo(fromBus);
      // root=db.getReference().child("BusDetails").orderByChild("to").equalTo(toBus);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CustomRowItem>();
        adapter = new CustomAdapter(this, list);
        recyclerView.setAdapter(adapter);
         root=db.getReference().child("BusDetails");
        root.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              //  if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                      //  System.out.println(">>>>>>DATE FROM FB >>>>>" + dataSnapshot.child("date").getValue(String.class));
                        if (dataSnapshot.child("date").getValue(String.class).equalsIgnoreCase(dateBus)) {
                            if (dataSnapshot.child("from").getValue(String.class).equalsIgnoreCase(fromBus) &&
                                    dataSnapshot.child("to").getValue(String.class).equalsIgnoreCase(toBus)) {
                                busId = dataSnapshot.child("busId").getValue(String.class);
                                busName = dataSnapshot.child("travelsName").getValue(String.class);
                                busNumber = dataSnapshot.child("busNumber").getValue(String.class);
                                fare = dataSnapshot.child("fare").getValue(String.class);
                                Date = dataSnapshot.child("date").getValue(String.class);
                                time = dataSnapshot.child("time").getValue(String.class);
                                from = dataSnapshot.child("from").getValue(String.class);
                                to = dataSnapshot.child("to").getValue(String.class);
                                list.add(new CustomRowItem(busId, busName, busNumber, fare, Date, time, from, to));
                            }
                            else {
                                Toast.makeText(BuslistActivity.this, "Sorry!! No buses available", Toast.LENGTH_LONG).show();
                            }


                        }
                        else{
                            Toast.makeText(BuslistActivity.this, "Sorry!! No buses available", Toast.LENGTH_LONG).show();
                        }
                        adapter.notifyDataSetChanged();

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent=new Intent(getApplicationContext(),UserDashboard.class);
        startActivityForResult(intent,0);
        return true;
    }

}