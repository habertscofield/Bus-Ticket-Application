package com.example.bus_ticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CancelActivity extends AppCompatActivity {
    private ListView listViewTickets;
    private DatabaseReference databaseTickets;
    private List<TicketActivityItem> ticketList;
    private FirebaseAuth firebaseAuth;
    private String ticketID, travelsName, from, to, Date, Time, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        getSupportActionBar().setTitle("Cancel Ticket section");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ticketList = new ArrayList<>();
        listViewTickets = findViewById(R.id.listViewTicketDetails);
        databaseTickets = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
         FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/").getReference("users").child(userId).child("Tickets").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ticketList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                TicketActivityItem ticketActivityItem = snapshot.getValue(TicketActivityItem.class);
                                ticketList.add(ticketActivityItem);
                            }
                            TicketList adapter = new TicketList(CancelActivity.this, ticketList);
                            listViewTickets.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        listViewTickets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TicketActivityItem ticketActivityItem = ticketList.get(i);
                showUpdateDeleteDialog(ticketActivityItem.getTicketID(),ticketActivityItem.getBusName(),ticketActivityItem.getIssueDate(),ticketActivityItem.getIssueTime(),ticketActivityItem.getFrom(), ticketActivityItem.getTo());
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    private void showUpdateDeleteDialog(String ticketID, String busName, String issuedate, String issuetime, String from, String to){
    AlertDialog.Builder dialogBuilder =new AlertDialog.Builder(this);

        LayoutInflater inflater =getLayoutInflater();
        final View dialogView =inflater.inflate(R.layout.update,null);

        dialogBuilder.setView(dialogView);

        final Button buttonDelete   = dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("Cancel "+busName);

        final AlertDialog alertDialog =dialogBuilder.create();
        alertDialog.show();


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBus(ticketID);
            }
        });



    }
    private void deleteBus(String ticketID){
        DatabaseReference drTravellingPath = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Tickets").child(ticketID);

        drTravellingPath.removeValue();

        Toast.makeText(this, " Ticket Details Deleted Successfully", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(CancelActivity.this,CancelActivity.class);
        startActivity(intent);

    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent=new Intent(getApplicationContext(),UserDashboard.class);
        startActivityForResult(intent,0);
        return true;
    }

}
