package com.example.bus_ticketapplication.Admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bus_ticketapplication.R;
import com.example.bus_ticketapplication.TicketActivity;
import com.example.bus_ticketapplication.ViewPayment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    View view;
    int  t1Day, t1Month, t1Year,t1Hour, t1Minute;
     AutoCompleteTextView  autoCompleteTextView1;
    AutoCompleteTextView  autoCompleteTextView2;
    private TextInputLayout textInputLayout;
    private TextInputEditText travelsName,busNumber,dateBus, timeBus,busFare;
    TextView mDisplayDate, mDisplayTime;
    private DatabaseReference root;
    private DatePickerDialog.OnDateSetListener mDatesetListener;
    private FirebaseDatabase db;
    DatabaseReference databaseReference;
    EditText edt_add_price;
    Button btnadd_price;
    TextView currentPrice;
    DatabaseReference priceRef, priceRetrieve;

    private static final int REQUEST_CODE = 100;
    MaterialCardView addBus, addAdmin, logout, addprice, deleteBus,addbook,addpesa;
    AlertDialog.Builder dialogBuilder, pricedialogBuilder, deleteBusDialog;
    AlertDialog dialog, admindialog, pricedialog, deleteDialog;

    private Uri imageUri;

    EditText bus_Id, bus_Number, busFrom, busTo, travels_Name, travelDate, bus_Condition;
    private Button comMPhotoSelect, comMSave, comMCancel, btnadminsave, btnadmincancel, savereading, cancelreading;
    Button busSave, annImageSelect, annSave, annCancel;
    private EditText adminusername, adminpassword;

    EditText readingday, readingdate, englishreading, kiswahilireading, kikuyureading,
            englishinjili, kiswahiliinjili, kikuyuinjili;

    private DatabaseReference mBusRef;
    private DatabaseReference mReadingRef;
    private FirebaseAuth firebaseAuth;
    private  String userId;

    ProgressDialog LoadingBar;
    private StorageReference mMemRef;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Admin Panel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        LoadingBar = new ProgressDialog(this);
        addBus = findViewById(R.id.addMember);
        mBusRef = FirebaseDatabase.getInstance().getReference();
        addAdmin = findViewById(R.id.addAdministrator);
        deleteBus = findViewById(R.id.deleteBus);
        addbook=findViewById(R.id.addbook);
        addpesa=findViewById(R.id.pesa);
        logout = findViewById(R.id.logout);
        deleteBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(getApplicationContext(),ViewBus.class);
                startActivity(intent2);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        addBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateBusDialog();
            }
        });

        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ViewBus.class);
                startActivity(intent);
            }
        });
        addpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ViewPayment.class);
                startActivity(intent);
            }
        });
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), TicketActivity.class);
                startActivity(intent);
            }
        });

    }

    private void CreateBusDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View busView = getLayoutInflater().inflate(R.layout.add_bus, null);

        //From
        autoCompleteTextView1 = busView.findViewById(R.id.busFrom);
        String[] from = getResources().getStringArray(R.array.from);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, from);
       autoCompleteTextView1.setAdapter(arrayAdapter);

        autoCompleteTextView2  = busView.findViewById(R.id.busTo);
        String[] to = getResources().getStringArray(R.array.from);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.dropdown_item, to);
        autoCompleteTextView2.setAdapter(arrayAdapter1);

        travelsName = busView.findViewById(R.id.travelsName);
        busNumber = busView.findViewById(R.id.busNumber);
        busFare=busView.findViewById(R.id.busFare);
        dateBus = busView.findViewById(R.id.journeyDate);
        timeBus = busView.findViewById(R.id.journeyTime);
        busSave = busView.findViewById(R.id.btnaddBus);
        dialogBuilder.setView(busView);
        dialog = dialogBuilder.create();
        dialog.show();

        mDisplayTime = busView.findViewById(R.id.journeyTime);
        mDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                final Calendar newTime=Calendar.getInstance();
                Calendar storedInf = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AdminActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t1Hour = hourOfDay;
                        t1Minute = minute;
                        storedInf.set(t1Year,t1Month,t1Day,t1Hour,t1Minute,0);
                        SimpleDateFormat format = new SimpleDateFormat(" h:mm a");
                        mDisplayTime.setText( format.format(storedInf.getTime()));

                    }
                },newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), false);
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });

        mDisplayDate = busView.findViewById(R.id.journeyDate);
        final Calendar newCalender = Calendar.getInstance();
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        final Calendar newTime = Calendar.getInstance();
                        Calendar newDate = Calendar.getInstance();
                        Calendar storedInf = Calendar.getInstance();
                        Calendar cal = Calendar.getInstance();
                        // TimePickerDialog timePickerDialog = new TimePickerDialog(RemindPage.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                        t1Day = dayOfMonth;
                        t1Month = month;
                        t1Year = year;

                        newDate.set(year, month, dayOfMonth);
                        Calendar tem = Calendar.getInstance();
                        Date getDate = new Date();

                            storedInf.set(t1Year, t1Month, t1Day);
                            SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy");

                            mDisplayDate.setText(format.format(storedInf.getTime()));


                        }
                }, newCalender.get(Calendar.YEAR),
                        newCalender.get(Calendar.MONTH),
                        newCalender.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });
        busSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String travelsNameI = travelsName.getText().toString().trim();
                String busNumberI = busNumber.getText().toString().trim();
                String fare=busFare.getText().toString().trim();
                String date = dateBus.getText().toString().trim();
                String time = timeBus.getText().toString().trim();
                String from = autoCompleteTextView1.getText().toString().trim();
                String to = autoCompleteTextView2.getText().toString().trim();
                String busId = mBusRef.push().getKey();

                if (TextUtils.isEmpty(travelsNameI)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Bus Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(busNumberI)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Bus Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(fare))
                {
                    Toast.makeText(AdminActivity.this, "Please Enter bus Fare", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Travel Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(time)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Departure Time", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(from)) {
                    Toast.makeText(getApplicationContext(), "Please Select Departure Place", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(to)) {
                    Toast.makeText(getApplicationContext(), "Please Select Destination Place", Toast.LENGTH_SHORT).show();
                    return;

                }else {
                    LoadingBar.setMessage("Adding Buses Please Wait...");
                    LoadingBar.show();
                    LoadingBar.setCancelable(false);

                    Bus bus = new Bus( busId,travelsNameI, busNumberI, fare, date,time, from, to);


                    mBusRef.child("BusDetails").child(busId).setValue(bus).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AdminActivity.this, "Bus Added Successfully", Toast.LENGTH_SHORT).show();
                                travelsName.setText("");
                                busNumber.setText("");
                                busFare.setText("");
                                dateBus.setText("");
                                timeBus.setText("");
                                autoCompleteTextView1.setText("");
                                autoCompleteTextView2.setText("");
                                Intent intent=new Intent(getApplicationContext(),ViewBus.class);
                                startActivity(intent);
                                LoadingBar.dismiss();

                            }else {
                                Toast.makeText(AdminActivity.this, "error", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();
                            }
                        }
                    });

                }


//
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }


    @Override
    public void onClick(View view) {
        if (view == addBus) {
            CreateBusDialog();
        }
    }

}