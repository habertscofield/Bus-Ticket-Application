package com.example.bus_ticketapplication.Admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    View view;
     AutoCompleteTextView  autoCompleteTextView1;
    AutoCompleteTextView  autoCompleteTextView2;
    private TextInputLayout textInputLayout;
    private EditText travelsName;
    private EditText busNumber;
    TextView dateBus, timeBus;
    TextView mDisplayDate, mDisplayTime;
    private DatePickerDialog.OnDateSetListener mDatesetListener;

    EditText edt_add_price;
    Button btnadd_price;
    TextView currentPrice;
    DatabaseReference priceRef, priceRetrieve;

    private static final int REQUEST_CODE = 100;
    MaterialCardView addBus, addAdmin, logout, addprice, deleteBus,addbook;
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

    ProgressDialog LoadingBar;


    private StorageReference mMemRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Admin Activities");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        LoadingBar = new ProgressDialog(this);

        addBus = findViewById(R.id.addMember);
        addAdmin = findViewById(R.id.addAdministrator);
        addprice = findViewById(R.id.addprice);
        deleteBus = findViewById(R.id.deleteBus);
        addbook=findViewById(R.id.addbook);
        mBusRef = FirebaseDatabase.getInstance().getReference();
        priceRef = FirebaseDatabase.getInstance().getReference().child("Prices");
        priceRetrieve = FirebaseDatabase.getInstance().getReference().child("Prices");

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
              //  Intent intent = new Intent(getApplicationContext(), DisplayLocationsActivity.class);
             //   startActivity(intent);
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
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, TicketActivity.class);
                startActivity(intent);
            }
        });
        addprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePriceDialog();
            }
        });
    }

    private void CreatePriceDialog() {
        pricedialogBuilder = new AlertDialog.Builder(this);
        View priceView = getLayoutInflater().inflate(R.layout.add_price, null);
        edt_add_price = priceView.findViewById(R.id.edt_busprice);
        btnadd_price = priceView.findViewById(R.id.btnaddprice);
        currentPrice = priceView.findViewById(R.id.current_price);
        pricedialogBuilder.setView(priceView);
        pricedialog = pricedialogBuilder.create();
        pricedialog.show();
        priceRetrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String CurrentPrice = snapshot.child("price").getValue().toString();
                currentPrice.setText("Ksh " + CurrentPrice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnadd_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = edt_add_price.getText().toString();

                Price newPrice = new Price(price);
                priceRef.setValue(newPrice).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminActivity.this, "Price Saved Successfully", Toast.LENGTH_SHORT).show();
                            edt_add_price.setText("");

                        }else {
                            Toast.makeText(AdminActivity.this, "Failed!!Try Again.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminActivity.this, "Something Went wrong!Try Again", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }




    private void CreateAdminDialog() {
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
        dateBus = busView.findViewById(R.id.journeyDate);
        timeBus = busView.findViewById(R.id.journeyTime);
//        spinner1 = busView.findViewById(R.id.busFrom);
//        spinner2 = busView.findViewById(R.id.busTo);
//        spinner3 = busView.findViewById(R.id.busCondition);
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
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AdminActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mDisplayTime.setText( selectedHour + ":" + selectedMinute);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });



        mDisplayDate = busView.findViewById(R.id.journeyDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog2 = new DatePickerDialog(AdminActivity.this
                        , android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , mDatesetListener
                        , year, month, day);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog2.show();
            }
        });
        mDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                Log.d(TAG, "OnDateSet:date :" + day + "/" + (month + 1) + "/" + year);
                String date = day + "/" + (month + 1) + "/" + year;
                // String status="Journey Date";
                // mDisplayDate.setText(status+"\n"+date);
                mDisplayDate.setText(date);
            }
        };
        busSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String travelsNameI = travelsName.getText().toString().trim();
                String busNumberI = busNumber.getText().toString().trim();
                String date = dateBus.getText().toString().trim();
                String time = timeBus.getText().toString().trim();
                String from = autoCompleteTextView1.getText().toString().trim();
                String to = autoCompleteTextView2.getText().toString().trim();


                String busId = mBusRef.push().getKey();

                if (TextUtils.isEmpty(travelsNameI)) {
                    //email is empty
                    Toast.makeText(getApplicationContext(), "Please Enter Travels Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(busNumberI)) {
                    //password is empty
                    Toast.makeText(getApplicationContext(), "Please Enter Bus Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(date)) {
                    //password is empty
                    Toast.makeText(getApplicationContext(), "Please Enter Journey Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(time)) {
                    //password is empty
                    Toast.makeText(getApplicationContext(), "Please Enter Journey Time", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.equals(from,"From")) {
                    //email is empty
                    Toast.makeText(getApplicationContext(), "Please Select Departure Place", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.equals(to,"To")) {
                    //password is empty
                    Toast.makeText(getApplicationContext(), "Please Select Destination Place", Toast.LENGTH_SHORT).show();
                    return;

                }else {
                    LoadingBar.setMessage("Adding Buses Please Wait...");
                    LoadingBar.show();

                    Bus bus = new Bus(busId, travelsNameI, busNumberI,  date,time, from, to);

//                    FirebaseUser user = adminAuth.getCurrentUser();
                    mBusRef.child("BusDetails").child(busId).setValue(bus).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AdminActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                travelsName.setText("");
                                busNumber.setText("");
                                dateBus.setText("");
                                timeBus.setText("");
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