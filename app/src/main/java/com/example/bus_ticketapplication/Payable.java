package com.example.bus_ticketapplication;

import static com.example.bus_ticketapplication.Constants.BUSINESS_SHORT_CODE;
import static com.example.bus_ticketapplication.Constants.CALLBACKURL;
import static com.example.bus_ticketapplication.Constants.PARTYB;
import static com.example.bus_ticketapplication.Constants.PASSKEY;
import static com.example.bus_ticketapplication.Constants.TRANSACTION_TYPE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bus_ticketapplication.Daraja.DarajaApiClient;
import com.example.bus_ticketapplication.MODE.AccessToken;
import com.example.bus_ticketapplication.MODE.STKPush;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class Payable extends AppCompatActivity implements View.OnClickListener{
    private String  saveCurrentDate, saveCurrentTime;
    private String paymentRandomKey;
    public static final String TAG = Payable.class.getSimpleName();
    private SweetAlertDialog sweetAlertDialog;

    private DarajaApiClient mApiClient;
    private ProgressDialog mProgressDialog;
    private String phone_number;
    private String names;
    private DatabaseReference paymentRef;

    TextView mAmount;
    EditText mPhone,mName;
    Button mPay;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferencePayment;
    TextView a,b,c;
    TextView totalCost;
    TextView totalSeat;
    String total, seats, nameBus, dateBus, fromBus,toBus,timeBus;
    private ArrayList<Integer> mSelectedSeats;
    private String mBusId;
    private SeatDetails mSeatDetails;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payable);
        getSupportActionBar().setTitle("Payment Section");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        paymentRef = FirebaseDatabase.getInstance().getReference().child("Payments");
        mAmount = findViewById(R.id.etAmount);
        mPhone = findViewById(R.id.etPhone);
       // mName=findViewById(R.id.name);
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReferencePayment = FirebaseDatabase.getInstance().getReference();

        a= findViewById(R.id.textView11);
      //  b= findViewById(R.id.textView21);

        a.setText(nameBus);
      //  b.setText(dateBus);
        total=getIntent().getStringExtra("TOTALCOST");
        seats=getIntent().getStringExtra("TOTALSEAT");
        fromBus=getIntent().getStringExtra("FROM_BUS");
        toBus=getIntent().getStringExtra("TO_BUS");
        timeBus=getIntent().getStringExtra("TIME_BUS");
        nameBus=getIntent().getStringExtra("NAME_BUS");
        dateBus=getIntent().getStringExtra("DATE_BUS");
        mSelectedSeats = getIntent().getIntegerArrayListExtra("SEATSET");
        mSeatDetails = (SeatDetails) getIntent().getSerializableExtra("SEAT_DETAILS");
        mBusId = getIntent().getStringExtra("BUS_ID");

        mPay = findViewById(R.id.btnPay);

        totalCost= findViewById(R.id.etAmount);
        totalSeat= findViewById(R.id.totalSeatsFinal);
        totalCost.setText(total);
        totalSeat.setText("Number Of Seats : "+seats);



        mProgressDialog = new ProgressDialog(this);
      mApiClient = new DarajaApiClient();
      mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.

     mPay.setOnClickListener(this);

      getAccessToken();

    }
    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }
    public void onClick(View view) {
        if (view== mPay){
            String phone_number = mPhone.getText().toString();
            String amount = mAmount.getText().toString();
            performSTKPush(phone_number,amount);
        }
    }

    public void performSTKPush(String phone_number,String amount) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = Utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount),
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "SmartLoan Ltd", //Account reference
                "SmartLoan STK PUSH by TDBSoft"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        Timber.d("post submitted to API. %s", response.body());
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                        final String strDate = formatter.format(date);

                        PaymentDetails newPayment = new PaymentDetails(amount, strDate, seats);
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        databaseReferencePayment.child(user.getUid()).child("PaymentDetails").push().setValue(newPayment).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    databaseReferencePayment.child(user.getUid()).child("SeatDetails").child(mBusId).push().setValue(mSeatDetails);
                                    DatabaseReference tempRef2= databaseReferencePayment.child("BusDetails").child(mBusId);
                                    for(int seat :  mSelectedSeats) {
                                        tempRef2.child("BookedSeats").child(user.getUid()).push().setValue(seat);
                                    }

                                    Intent intent = new Intent(Payable.this, ConfirmTicket.class);
                                intent.putExtra("TOTALCOST", total);
                                intent.putExtra("TOTALSEAT", seats);
                                intent.putExtra("NAME_BUS", nameBus);
                                intent.putExtra("DATE_BUS", dateBus);
                                intent.putExtra("SEATSET", new ArrayList<>(mSelectedSeats));
                                intent.putExtra("TOTALCOST", total);
                                intent.putExtra("FROM_BUS", fromBus);
                                intent.putExtra("TO_BUS", toBus);
                                intent.putExtra("TIME_BUS", timeBus);
                                intent.putExtra("BUS_ID", mBusId);

                                startActivity(intent);

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Payable.this, "Something Went Wrong!Try Again again.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Payable.this,Payable.class);
                                startActivity(intent);

                            }
                        });




                    } else {
                        Toast.makeText(Payable.this, "Something Went Wrong!Try Again.", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Payable.this,Payable.class);
                        startActivity(intent);
                        Timber.e("Response %s", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }





            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });


    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}