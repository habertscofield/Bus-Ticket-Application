package com.example.bus_ticketapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class RegisterUser extends AppCompatActivity {

    public EditText FirstName,LastName,Phone,Email,Password,CPassword;
    private Button registerButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private ProgressDialog loadingbar;
    private AppCompatCheckBox checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Initilizations();
        checkbox = (AppCompatCheckBox) findViewById(R.id.checkbox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
//ProgressDialog=new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null && firebaseAuth.getCurrentUser().isEmailVerified()){
            Toast.makeText(RegisterUser.this, "User Created  ", Toast.LENGTH_SHORT).show();
            Intent ie = new Intent(RegisterUser.this, LoginUser.class);
            ie.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ie);
            finish();
        }


        loadingbar=new ProgressDialog(this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = FirstName.getText().toString();
                String lastName = LastName.getText().toString();
                String emailText = Email.getText().toString();
                String passwordText = Password.getText().toString();
                String conPassText=CPassword.getText().toString();
                String phoneNoText = Phone.getText().toString();

                if(TextUtils.isEmpty(firstName)){
                    FirstName.setError("First Name is Required ! ");
                   FirstName.requestFocus();
                    return;
                }else{

                    FirstName.setError(null);
                }

                if(TextUtils.isEmpty(lastName)){
                    LastName.setError("Last Name is Required !");
                    LastName.requestFocus();
                    return;
                }else{

                    LastName.setError(null);
                }

                if(TextUtils.isEmpty(emailText)){
                    Email.setError("Email is Required !");
                    Email.requestFocus();
                    return;
                }else{

                   Email.setError(null);
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                    Email.setError("Please provide a Valid Email !");
                    Email.requestFocus();
                    return;

                }else{

                    Email.setError(null);
                }
                if(passwordText.length() <= 6){
                    Password.setError("Password must be greater or equalto 6 characters!");
                    Password.requestFocus();
                    return;
                }else{

                    Password.setError(null);
                }
                if(!passwordText.equals(conPassText)){
                    CPassword.setError("Password mismatch");
                    CPassword.requestFocus();
                    return;
                }else{

                    CPassword.setError(null);
                }
                if(TextUtils.isEmpty(phoneNoText)){
                    Phone.setError("Phone is Required !");
                    Phone.requestFocus();
                    return;
                }else{
                    Phone.setError(null);
                }
               // progressDialog.setMessage("Registering Please Wait...");
              //  progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(emailText,passwordText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        loadingbar.setTitle("Creating New Account");
                        loadingbar.setMessage("please wait while we are creating an account for you");
                        loadingbar.setCanceledOnTouchOutside(true);
                        loadingbar.show();

                        firebaseUser = firebaseAuth.getCurrentUser();

                        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterUser.this, "Account Created Successfully,Please verify your email !!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(RegisterUser.this, LoginUser.class);
                                    i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();
                                  //  progressDialog.dismiss();

                                }else{
                                    Toast.makeText(RegisterUser.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        String userId = firebaseUser.getUid();
                        Users users = new Users(firstName,lastName,emailText,passwordText,conPassText,phoneNoText);



                        FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/").getReference("users").child(userId).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    // Toast.makeText(RegisterActivity.this, "database inserted", Toast.LENGTH_SHORT).show();
                                }else{
                                    //  Toast.makeText(RegisterActivity.this, "databse not working", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(RegisterUser.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void Initilizations() {
        FirstName=(EditText)findViewById(R.id.inputName);
        LastName=(EditText)findViewById(R.id.inputlName);
        Phone=(EditText)findViewById(R.id.inputPhone);
        Email=(EditText)findViewById(R.id.inputEmail);
        Password=(EditText)findViewById(R.id.inputPassword);
        CPassword=(EditText)findViewById(R.id.inputcPassword);
        registerButton = (Button)findViewById(R.id.registerButtonId);
    }

}