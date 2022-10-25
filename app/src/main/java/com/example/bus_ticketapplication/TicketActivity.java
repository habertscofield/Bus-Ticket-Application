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
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TicketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String ticketID, travelsName, from, to, Date, Time, userId;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private DatabaseReference root, databaseReference1;
    private TicketActivityAdapter adapter;
    private ArrayList<TicketActivityItem> list;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        getSupportActionBar().setTitle("My Tickets");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.ticketRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<TicketActivityItem>();
        adapter = new TicketActivityAdapter(this, list);
        recyclerView.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/");
        root = db.getReference("users").child(userId).child("Tickets");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ticketID = dataSnapshot.child("ticketID").getValue(String.class);
                        travelsName = dataSnapshot.child("busName").getValue(String.class);
                        from = dataSnapshot.child("from").getValue(String.class);
                        to = dataSnapshot.child("to").getValue(String.class);
                        Time = dataSnapshot.child("issueDate").getValue(String.class);
                        Date = dataSnapshot.child("issueTime").getValue(String.class);
                        list.add(new TicketActivityItem(ticketID, travelsName, from, to, Date, Time));
                    }
                    adapter.notifyDataSetChanged();
                    //Toast.makeText(TicketActivity.this, "Here are your tickets!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(TicketActivity.this, "You Don't Have Any Ticket Yet!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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



