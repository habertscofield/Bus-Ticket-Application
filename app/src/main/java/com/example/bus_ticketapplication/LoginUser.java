package com.example.bus_ticketapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.bus_ticketapplication.Admin.adminlogin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginUser extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private AlertDialog.Builder forgetPassAlert;
    private LayoutInflater inflater;
    private ProgressDialog loadingbar;
    private  AppCompatCheckBox checkbox;


    EditText Email,Password;
    private Button login,forgetPasswordButton;
    private TextView needAcount;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        loadingbar = new ProgressDialog(this);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Initializations();

        firebaseAuth = FirebaseAuth.getInstance();
        forgetPassAlert = new AlertDialog.Builder(LoginUser.this);
        inflater = this.getLayoutInflater();
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

        needAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(registerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validateEmail() && validatePassword())
                {
                    String email=Email.getText().toString().trim();
                    String pass=Password.getText().toString().trim();

                    successFullLogIn(email,pass);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error...", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

        forgetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view1  = inflater.inflate(R.layout.reset_pass,null);
                forgetPassAlert.setTitle("Reset Password ?").setMessage("Enter your email to get reset password link");


                forgetPassAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final EditText resetEmailEditText = view1.findViewById(R.id.reset_passwordEditTxt);

                        String resetEmailText = resetEmailEditText.getText().toString();

                        if(resetEmailText.isEmpty()){
                            resetEmailEditText.setError("Email is Required !");
                            resetEmailEditText.requestFocus();
                            return;
                        }
                        resetForgetPassword(resetEmailText);
                    }
                });



                forgetPassAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                forgetPassAlert.setView(view1);
                forgetPassAlert.show();
            }
        });

    }

    private void resetForgetPassword(String resetEmailText) {
        firebaseAuth.sendPasswordResetEmail(resetEmailText).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(LoginUser.this, "Reset Password Email is sent", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(LoginUser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.loginoptions,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()== R.id.foradmin){
            Intent intent=new Intent(LoginUser.this, adminlogin.class);
            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);

    }

    private boolean validateEmail(){
        String emailInput = Email.getText().toString().trim();
        if(TextUtils.isEmpty(emailInput)){
            Email.setError("Field can't be empty...");
            return false;
        }
        else {
            Email.setError(null);
            //Email.setError(false);
            return true;
        }
    }


    private boolean validatePassword(){
        String passwordInput =  Password.getText().toString().trim();
        if(TextUtils.isEmpty(passwordInput)){
            Password.setError("Field can't be empty...");
            return false;
        }
        else {
            Password.setError(null);
            return true;
        }
    }


    private void successFullLogIn(String emailText,String passText){
        loadingbar.setTitle("Signing in");
        loadingbar.setMessage("Please Wait...");
        loadingbar.setCanceledOnTouchOutside(true);
        loadingbar.show();

        firebaseAuth.signInWithEmailAndPassword(emailText,passText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                    Toast.makeText(LoginUser.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent ig = new Intent(getApplicationContext(),UserDashboard.class);
                    ig.putExtra("Password",passText);
                    startActivity(ig);
                    finish();
                    loadingbar.dismiss();
                }else{
                    Toast.makeText(LoginUser.this, "Please Verify Your Email ", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(LoginUser.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Initializations() {
        Email = (EditText) findViewById(R.id.inputEmail);
        Password = (EditText) findViewById(R.id.inputPassword);


        login = (Button) findViewById(R.id.btnLogin);
        needAcount = (TextView)findViewById(R.id.needAcountId);
        forgetPasswordButton  = (Button)findViewById(R.id.forgetPasswordId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!= null && firebaseAuth.getCurrentUser().isEmailVerified()){
            startActivity(new Intent(getApplicationContext(),UserDashboard.class));
            finish();
        }
    }


}