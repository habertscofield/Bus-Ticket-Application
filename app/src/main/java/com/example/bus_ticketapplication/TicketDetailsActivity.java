package com.example.bus_ticketapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TicketDetailsActivity extends AppCompatActivity {
    private LayoutInflater inflater;
    private Button print;
    private TextView passengerNameTV, mobileTV, emailTV, busNameTV, fromTV, toTV, journeyDateTV, seatTV,seatsTV, timeTV, costTV, ticketIDTV;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private DatabaseReference root, databaseReference1,databaseReference;
    String userID;
    String total,busId,ticketID, seats,time,seatNo, issueDate,issueTime,userId,message;
    //added
    private static final String TAG = "PdfCreatorActivity";
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    private PdfPCell cell;
    private Image imgReportLogo;

    BaseColor headColor = WebColors.getRGBColor("#DEDEDE");
    BaseColor tableHeadColor = WebColors.getRGBColor("#F5ABAB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        getSupportActionBar().setTitle("TicketDetails");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        ticketID = intent.getStringExtra("ticketID");
         time = intent.getStringExtra("issueTime");

        passengerNameTV = findViewById(R.id.passengerNameTicket);
        print = findViewById(R.id.print_ticket);
        mobileTV = findViewById(R.id.mobileNumberTicket);
        emailTV = findViewById(R.id.emailTicket);
        busNameTV = findViewById(R.id.busNameTicket);
        fromTV = findViewById(R.id.fromTicket);
        toTV = findViewById(R.id.toTicket);
        journeyDateTV = findViewById(R.id.journeyDateTicket);
         seatsTV=findViewById(R.id.seatsTicket);
        seatTV = findViewById(R.id.seatTicket);
        timeTV = findViewById(R.id.timeTicket);
        costTV = findViewById(R.id.totalCostTicket);
        ticketIDTV = findViewById(R.id.ticketIDTicket);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("BookYourBus","BookYourBus", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Ticket Downloaded Successfully";

                NotificationCompat.Builder builder = new NotificationCompat.Builder(TicketDetailsActivity.this, "BookYourBus");
                builder.setContentTitle("Ticket Downloading Successful");
                builder.setContentText(message);
                builder.setSmallIcon(R.drawable.receipt);
                builder.setAutoCancel(true);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(TicketDetailsActivity.this);
                notificationManagerCompat.notify(1, builder.build());
                printTicketMethod();
            }        });

        //
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/").getReference("users").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                Users usr = snapshot.getValue(Users.class);
                passengerNameTV.setText(usr.getFirstName() + " " + usr.getLastName());
                mobileTV.setText(usr.getPhoneNo());
                emailTV.setText(usr.getEmailId());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        db = FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/");
       root = db.getReference("users").child(userID).child("Tickets").child(ticketID);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
              TicketDetailsInformation ticketDetailsInformation = snapshot.getValue(TicketDetailsInformation.class);
               busNameTV.setText(ticketDetailsInformation.getBusName());
             fromTV.setText(ticketDetailsInformation.getFrom());
              toTV.setText(ticketDetailsInformation.getTo());
              journeyDateTV.setText(ticketDetailsInformation.getJourneyDate());
              seatTV.setText(ticketDetailsInformation.getSeats());
              seatsTV.setText(ticketDetailsInformation.getSeatNo());
               timeTV.setText(ticketDetailsInformation.getTime());
               costTV.setText(ticketDetailsInformation.getTotalCost());
              ticketIDTV.setText(ticketDetailsInformation.getTicketID());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }



    private void printTicketMethod() {
        if (seatsTV.getText().toString().isEmpty()){
            seatsTV.setError("Body is empty");
            seatsTV.requestFocus();
            return;
        }
        try {
            createPdfWrapper();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void createPdfWrapper() throws FileNotFoundException,DocumentException{
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException{
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmm");
        pdfFile = new File(docsFolder.getAbsolutePath(),"BusTicket_" + dateFormat.format(Calendar.getInstance().getTime()) + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, output);
        document.open();
        Font f=new Font(Font.FontFamily.COURIER,14,Font.BOLDITALIC);
        document.add(new Phrase(Element.ALIGN_RIGHT, "MY BUS TICKET", f));
        Font font=new Font(Font.FontFamily.HELVETICA,12,Font.NORMAL);

        document.add( new Paragraph("Passenger Name  : " + passengerNameTV.getText().toString().trim(), font));
        document.add( new Paragraph("Mobile Number :" + mobileTV.getText().toString().trim(), font));
        document.add(new Paragraph("Email :" + emailTV.getText().toString().trim(), font));
        document.add( new Paragraph("Bus Name  :" + busNameTV.getText().toString().trim(), font));
        document.add(new Paragraph("From  :" + fromTV.getText().toString().trim(), font));
        document.add( new Paragraph("To   :" + toTV.getText().toString().trim(), font));
        document.add( new Paragraph("Journey Date  :" + journeyDateTV.getText().toString().trim(), font));
        document.add( new Paragraph("Seats  :" + seatTV.getText().toString().trim(), font));
        document.add(new Paragraph("SeatNo  :" + seatsTV.getText().toString().trim(), font));
        document.add( new Paragraph("DepartureTime  :" + timeTV.getText().toString().trim(), font));
        document.add( new Paragraph("Total Amount  :Ksh." + costTV.getText().toString().trim(), font));
        document.add( new Paragraph("Dateissued: " + format.format(Calendar.getInstance().getTime())));
        Font FON=new Font(Font.FontFamily.COURIER,14,Font.BOLDITALIC);
        document.add(new Phrase("Have a Safe Journey",FON));

        document.close();
        previewPdf();
    }

    private void previewPdf() {
        Toast.makeText(this,"Ticket Downloaded Successfully",Toast.LENGTH_SHORT).show();

    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent=new Intent(getApplicationContext(),TicketActivity.class);
        startActivityForResult(intent,0);
        return true;
    }
}
