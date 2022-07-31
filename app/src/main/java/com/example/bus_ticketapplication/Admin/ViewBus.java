package com.example.bus_ticketapplication.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bus_ticketapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBus extends AppCompatActivity {

    private ListView listViewBuses;
    private DatabaseReference databaseBus;
    private List<Bus2> busList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus);
//
//
        busList = new ArrayList<>();

        listViewBuses = findViewById(R.id.listViewBusDetails);
        databaseBus = FirebaseDatabase.getInstance().getReference();
//         = FirebaseDatabase.getInstance().getReference("BusDetails");
        FirebaseDatabase.getInstance().getReference("BusDetails")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        busList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Bus2 bus = snapshot.getValue(Bus2.class);
                                busList.add(bus);
                            }
                            BusList adapter = new BusList(ViewBus.this, busList);
                            listViewBuses.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        listViewBuses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bus2 bus =busList.get(i);
                showUpdateDeleteDialog(bus.getBusId(),bus.getTravelsName(),bus.getBusNumber(),bus.getDate(),bus.getFrom(),bus.getTo());
            }
        });
//        listViewBuses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Bus bus =busList.get(i);
//                showUpdateDeleteDialog(bus.getBusId(),bus.getTravelsName(),bus.getBusNumber(),bus.getDate(),bus.getFrom(),bus.getTo(),bus.getBusCondition());
//                return false;
//            }
//        });
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    // update ticket fees details
    private void showUpdateDeleteDialog(final String busId, String travelsName, String busNumber, String date, String from, String to){
        AlertDialog.Builder dialogBuilder =new AlertDialog.Builder(this);

        LayoutInflater inflater =getLayoutInflater();

        final View dialogView =inflater.inflate(R.layout.update_dialog,null);

        dialogBuilder.setView(dialogView);

     /*   final EditText editTravelsName  = dialogView.findViewById(R.id.editTexttravelsName);
        final EditText editBusNumber = dialogView.findViewById(R.id.editTextbusNumber);
        final EditText editDate       = dialogView.findViewById(R.id.editTextjourneyDate);

        final EditText editFromBus= findViewById(R.id.editTextbusFrom);
        final EditText editToBus= findViewById(R.id.editTextbusTo);*/

      //  final Button buttonUpdate   = dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete   = dialogView.findViewById(R.id.buttonDelete);

//        editTravelsName.setText(travelsName);
//        editBusNumber.setText(busNumber);
//        editDate.setText(date);
//        editFromBus.setText(from);
//        editToBus.setText(to);
//        editConditionBus.setText(busCondition);

        dialogBuilder.setTitle("Delete "+travelsName);

        final AlertDialog alertDialog =dialogBuilder.create();
        alertDialog.show();


     /*  buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bus_1 = editTravelsName.getText().toString().trim();
                String bus_2 = editBusNumber.getText().toString().trim();
                String bus_3 = editDate.getText().toString().trim();
                String bus_4 = editFromBus.getText().toString();
                String bus_5 = editToBus.getText().toString();



                updateBusDetail(busId,bus_1,bus_2,bus_3,bus_4,bus_5);
                alertDialog.dismiss();
            }
        });*/

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBus(busId);
            }
        });



    }
    private void deleteBus(String busId){
        DatabaseReference drTravellingPath = FirebaseDatabase.getInstance().getReference("BusDetails").child(busId);

        drTravellingPath.removeValue();

        Toast.makeText(this, " Bus Detail Deleted Successfully", Toast.LENGTH_LONG).show();


    }
    private boolean updateBusDetail(String busId, String travelsNameI, String busNumberI, String date,String time, String from, String to){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BusDetails").child(busId);

        Bus2 bus = new Bus2(busId, travelsNameI, busNumberI, date,time, from, to);
        databaseReference.setValue(bus);

        Toast.makeText(this, "Bus Detail Updated Successfully ", Toast.LENGTH_LONG).show();
        return true;
    }

}

