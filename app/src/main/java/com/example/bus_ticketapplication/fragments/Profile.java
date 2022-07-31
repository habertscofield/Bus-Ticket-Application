package com.example.bus_ticketapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bus_ticketapplication.R;
import com.example.bus_ticketapplication.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    private View view;
    private ImageView imageProfile;
    private TextView fullName;

    private TextView textEmail;

    private TextView passText;

    private TextView textPhone;


    private Button editButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    String fName,lName,mail,pass,phn,imageUrl = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        imageProfile = view.findViewById(R.id.mainImage);
        fullName = view.findViewById(R.id.fullText);

        textEmail = view.findViewById(R.id.eamilTxt);

        textPhone = view.findViewById(R.id.phoneTxt);

        passText = view.findViewById(R.id.passwordTxt);
        editButton = view.findViewById(R.id.editButton);



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://bus-ticket-application-b98f2-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("users").child(userId);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);
                fullName.setText(userProfile.getFirstName() +" " + userProfile.getLastName());
                textEmail.setText(userProfile.getEmailId());
                textPhone.setText(userProfile.getPhoneNo());
                passText.setText(userProfile.getPassWord());
                fName = userProfile.getFirstName();
                lName = userProfile.getLastName();

                if(snapshot.child("profileImage").getValue() != null){
                    imageUrl = snapshot.child("profileImage").getValue().toString();
                    // Toast.makeText(getContext(),imageUrl,Toast.LENGTH_LONG).show();
                    Picasso.get().load(imageUrl).placeholder(R.drawable.man).into(imageProfile);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mail = textEmail.getText().toString().trim();

                Bundle dataBundle = new Bundle();
                dataBundle.putString("firstName",fName);
                dataBundle.putString("lastName",lName);
                dataBundle.putString("mail",mail);
                dataBundle.putString("phone",textPhone.getText().toString());
                dataBundle.putString("password",passText.getText().toString());
                dataBundle.putString("profileImage",imageUrl);


                Update_Profile update_profile = new Update_Profile();
                update_profile.setArguments(dataBundle);
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, update_profile).addToBackStack(null).commit();
            }

        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }


}