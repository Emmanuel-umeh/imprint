package com.danielogbuti.fingerprintsample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielogbuti.fingerprintsample.models.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private String id;
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView bloodGroup,FirstName,lastName,gender,maritalStatus,dateOfBirth,phoneNumber;
    ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getIntent() != null){
            id = getIntent().getStringExtra("id");
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Profile");

        bloodGroup = (TextView)findViewById(R.id.bloodGroup);
        FirstName = (TextView)findViewById(R.id.firstName);
        lastName = (TextView)findViewById(R.id.lastName);
        profileImage = (ImageView)findViewById(R.id.profileImage);
        gender = (TextView)findViewById(R.id.gender);
        maritalStatus = (TextView)findViewById(R.id.maritalStatus);
        dateOfBirth = (TextView)findViewById(R.id.dateOfBirth);
        phoneNumber = (TextView)findViewById(R.id.phoneNumber);

        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile activity = dataSnapshot.getValue(Profile.class);

                Picasso.with(getBaseContext()).load(activity.getImage())
                        .into(profileImage);

                bloodGroup.setText(activity.getBloodGroup());
                FirstName.setText(activity.getFirstName());
                lastName.setText(activity.getLastName());
                gender.setText(activity.getGender());
                maritalStatus.setText(activity.getMaritalStatus());
                dateOfBirth.setText(activity.getDateOfBirth());
                phoneNumber.setText(activity.getPhoneNumber());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
