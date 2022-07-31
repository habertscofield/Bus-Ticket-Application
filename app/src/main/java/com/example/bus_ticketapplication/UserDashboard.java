package com.example.bus_ticketapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bus_ticketapplication.Maps.MapsActivity;
import com.example.bus_ticketapplication.fragments.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDashboard extends AppCompatActivity {
    private static final String TAG = "Navigation";
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AlertDialog.Builder logOutBuilder, exitAppBuilder;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private TextView fullName;
    private String userId, firstName, lastName;
    private View headerView;
    private CircleImageView circleImageView;
    private TextView mDisplayDate,dateBus;
    private DatePickerDialog.OnDateSetListener mDatesetListener;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView textViewUserEmail;
    private TextView textViewUserName;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    AutoCompleteTextView autoCompleteTextView1;
    AutoCompleteTextView autoCompleteTextView2;
    Button angry_btn;
    //  Toolbar toolbar;
    private int journeyDate,journeyMonth,journeyYear;

    private TextInputEditText journeydate;
    private TextInputLayout textInputLayout;

//    public static final String FROM_BUS = "firstlocation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Intent intent = getIntent();
        String fromBus=intent.getStringExtra("FROM_BUS");
        String toBus=intent.getStringExtra("TO_BUS");
        String dateBus=intent.getStringExtra("DATE_BUS");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = findViewById(R.id.NavigationView);
        firebaseAuth = FirebaseAuth.getInstance();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        autoCompleteTextView1 = findViewById(R.id.busFrom);
        autoCompleteTextView2 = findViewById(R.id.busTo);
        journeydate = findViewById(R.id.journeydate);


        angry_btn = findViewById(R.id.angry_btn);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginUser.class));
        }
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent iin = getIntent();
        Bundle data = iin.getExtras();
        if (data != null) {
            String password = (String) data.get("Password");
            firebaseUser = firebaseAuth.getCurrentUser();
            userId = firebaseUser.getUid();
            FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/").getReference("users").child(userId).child("passWord").setValue(password);
        }

        headerView = navigationView.getHeaderView(0);
        fullName = headerView.findViewById(R.id.textFullName);
        circleImageView = (CircleImageView) headerView.findViewById(R.id.profileImageCirculerId);

        String userIddd = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/").getReference("users").child(userIddd);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Users usr = snapshot.getValue(Users.class);
                fullName.setText(usr.getFirstName() + " " + usr.getLastName());

                if (snapshot.child("profileImage").getValue() != null) {
                    String imageUrl = snapshot.child("profileImage").getValue().toString();
                    Picasso.get().load(imageUrl).placeholder(R.drawable.man).into(circleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        // showFragments(new HomeFragment());

        String[] from = getResources().getStringArray(R.array.from);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, from);
        autoCompleteTextView1.setAdapter(arrayAdapter);

        String[] to = getResources().getStringArray(R.array.from);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.dropdown_item, to);
        autoCompleteTextView2.setAdapter(arrayAdapter1);
        journeydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                journeyDate = calendar.get(Calendar.DATE);
                journeyMonth = calendar.get(Calendar.MONTH);
                journeyYear = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(UserDashboard.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                                journeydate.setText(date+" - "+(month+1)+" - "+year);
                            }
                        },
                        journeyYear,
                        journeyMonth,
                        journeyDate);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);;
                datePickerDialog.show();
            }
        });


        drawerLayout.closeDrawer(GravityCompat.START);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent h = new Intent(UserDashboard.this, UserDashboard.class);
                        startActivity(h);
                       // Intent intentSeat = new Intent(getApplicationContext(), UserDashboard.class);
                     //   intentSeat.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       // startActivity(intentSeat);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.profile:
                        // Toast.makeText(MainActivity.this, "Profile Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                        showFragmentsss(new Profile());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.location:
                       Intent intentlocation=new Intent(getApplicationContext(), MapsActivity.class);
                       intentlocation.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(intentlocation);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.cancel:
                        Intent intentCancel=new Intent(getApplicationContext(),CancelActivity.class);
                        intentCancel.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentCancel);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.notify:
                        Intent intentNotify= new Intent(getApplicationContext(), Notification.class);
                        intentNotify.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentNotify);


                    case R.id.tickets:
                        //Toast.makeText(MainActivity.this, "Tickets Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                        Intent intentTickets = new Intent(getApplicationContext(), TicketActivity.class);
                       intentTickets.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(intentTickets);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.print:
                        Intent intentPrint = new Intent(getApplicationContext(), Print.class);
                        intentPrint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentPrint);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.logout:
                        //Toast.makeText(MainActivity.this, "Log out  Menu is Clicked !!", Toast.LENGTH_SHORT).show();
                        logOutBuilder = new AlertDialog.Builder(UserDashboard.this);
                        //setTitle
                        logOutBuilder.setTitle("Log Out");
                        //Set message
                        logOutBuilder.setMessage("Are You Sure to Logout ???");
                        //positive yes button
                        logOutBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // log out
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getApplicationContext(), LoginUser.class));
                                finish();
                            }
                        });

                        //log out cancel button
                        logOutBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //dismiss dialogue;
                                dialogInterface.dismiss();
                            }
                        });
                        // show  dialogue
                        logOutBuilder.show();
                        return true;
                }

                return true;
            }

        });
        angry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchBuses();
            }
        });
    }

    private void SearchBuses() {
        String from = autoCompleteTextView1.getText().toString().trim();
        String to = autoCompleteTextView2.getText().toString().trim();
        String date = journeydate.getText().toString().trim();
       //  String key = databaseReference.push().getKey();

        if (from.isEmpty()) {
            //email is empty
            Toast.makeText(this, "Please select departure place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (to.isEmpty()) {
            //password is empty
            Toast.makeText(this, "Please select destination place", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date.isEmpty()) {
            //password is empty
            Toast.makeText(this, "Please select journey date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(from.equals(to))
        {
            Toast.makeText(this, "Source and Destination cannot be the same", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Searching Buses Please Wait...");
        progressDialog.show();
        BookingDetail bookingDetail = new BookingDetail(from, to, date);
        databaseReference.child("BookingDetails").push().setValue(bookingDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent1 = getIntent();
                    Bundle extras = intent1.getExtras();
                    Intent intent = new Intent(UserDashboard.this, BuslistActivity.class);
                    intent.putExtra("FROM_BUS", from);
                    intent.putExtra("TO_BUS", to);
                    intent.putExtra("DATE_BUS", date);
                    startActivity(intent);
                    progressDialog.dismiss();

                }
            }
        });
    }




    public void showFragmentsss(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

}

