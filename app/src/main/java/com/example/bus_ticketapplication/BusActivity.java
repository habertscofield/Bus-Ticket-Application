package com.example.bus_ticketapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus_ticketapplication.Admin.Bus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView recyclerView;
    private BusAdapter adapter;
    private List<Bus> busList;
    private DatabaseReference dbBuses;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    Calendar calendar;
   // String Date, currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
       // toolbar = findViewById(R.id.app_bar);
       // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Available Buses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        busList = new ArrayList<>();
        adapter = new BusAdapter(this, busList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
       String fromBus=getIntent().getStringExtra("FROM_BUS").toString();
         String toBus=getIntent().getStringExtra("TO_BUS").toString();
         String dateBus=getIntent().getStringExtra("DATE_BUS").toString();

        FirebaseDatabase.getInstance().getReference()
                .child("BusDetails")
                .orderByChild("from")
                .equalTo(fromBus)
                .addValueEventListener(new ValueEventListener()  {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        busList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                              //  if (dataSnapshot.child("date").getValue(String.class).equalsIgnoreCase(dateBus)){
                                    Bus bus = snapshot.getValue(Bus.class);
                                    busList.add(bus);

                            }
                            adapter.notifyDataSetChanged();
                        }
                    } @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(BusActivity.this, "Firebase Database Error", Toast.LENGTH_LONG).show();
                    }
                });
        FirebaseDatabase.getInstance().getReference()
                .child("BusDetails")
                .orderByChild("to")
                .equalTo(toBus)
                .addValueEventListener(new ValueEventListener()  {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        busList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                              //  if (dataSnapshot.child("date").getValue(String.class).equalsIgnoreCase(dateBus)){
                                    Bus bus = snapshot.getValue(Bus.class);
                                    busList.add(bus);

                            }
                            adapter.notifyDataSetChanged();
                        }
                    } @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(BusActivity.this, "Firebase Database Error", Toast.LENGTH_LONG).show();
                    }
                });


                    }



   /* ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            busList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bus bus = snapshot.getValue(Bus.class);
                    busList.add(bus);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };*/

    @Override
    public void onClick(View view, int position) {
       // Intent intent1 = getIntent();
     //  Bundle extras = intent1.getExtras();


        Bus bus = busList.get(position);
        String busId=bus.getBusId();
        String travelsName=bus.getTravelsName();
        String busNumber=bus.getBusNumber();
        String date=bus.getDate();
        String time = bus.getTime();
        String from=bus.getFrom();
        String to=bus.getTo();

        Bus busDetail=new Bus(busId,travelsName,busNumber,date,time,from,to);
        FirebaseUser user1=firebaseAuth.getCurrentUser();
        databaseReference.child(user1.getUid()).child("BusBookingDetails").push().setValue(busDetail);

        Toast.makeText(getApplicationContext(),""+travelsName,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(BusActivity.this,SeatActivity.class);
        intent.putExtra("NAME_BUS",travelsName);
        intent.putExtra("BUS_ID",busId);
        intent.putExtra("DATE_BUS",date);
        intent.putExtra("TIME_BUS",time);
        intent.putExtra("FROM_BUS",from);
        intent.putExtra("TO_BUS",to);
        startActivity(intent);


    }
}