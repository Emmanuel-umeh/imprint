package com.danielogbuti.fingerprintsample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielogbuti.fingerprintsample.models.Profile;
import com.danielogbuti.fingerprintsample.models.ProfileDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import asia.kanopi.fingerscan.Status;

public class MainActivity extends AppCompatActivity  {

    private TextView messageText;
    private ImageView fingerDisplay;
    private Button scanButton;
    private static final int REQUEST_CODE = 0;
    byte[] img;
    Bitmap bm;
    List<DataSnapshot> profileList;
    List<ProfileDetails> detailsList;
    ProfileDetails details;
    FirebaseDatabase database;
    DatabaseReference profile;
    FingerprintTemplate template;
    double high = 0;
    FingerprintTemplate finger;
    Profile data;
    Profile dataProfile;
    ProfileDetails match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageText = (TextView)findViewById(R.id.tvMessage);
        fingerDisplay = (ImageView)findViewById(R.id.ivFingerDisplay);
        scanButton = (Button)findViewById(R.id.buttonScan);
        database = FirebaseDatabase.getInstance();
        profile = database.getReference("Profile");
        fireBaseData();




        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ScanActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        int status;
        String errorMessage;

        switch (requestCode){
            case REQUEST_CODE:
            {
                if (resultCode == RESULT_OK){
                    status = data.getIntExtra("status", Status.ERROR);

                    if (status == Status.SUCCESS){

                        img = data.getByteArrayExtra("img");
                        bm = BitmapFactory.decodeByteArray(img,0,img.length);


                         template = new FingerprintTemplate()
                                .dpi(500)
                                .create(img);


                        match = find(template,detailsList);
                        //ProfileDetails matchDetails = match;


                        /*Resources res = getResources();
                        Drawable drawable = res.getDrawable(R.drawable.finger_image_a2);
                        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] bitMapData = stream.toByteArray();*/


                       /*profile.child("03").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 dataProfile = dataSnapshot.getValue(Profile.class);

                                 byte[] imgSrc = Base64.getDecoder().decode(dataProfile.getFingerPrint());
                                 FingerprintTemplate fingerprintTemplate = new FingerprintTemplate()
                                         .dpi(500)
                                         .create(imgSrc);

                                 double score = new FingerprintMatcher()
                                         .index(template)
                                         .match(fingerprintTemplate);

                                if (score>=40){
                                    messageText.setText("Match");

                                }else {
                                    messageText.setText("error");

                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });*/

                        /*FingerprintTemplate candidate = new FingerprintTemplate()
                                .dpi(500)
                                .create(bitMapData);
                        double score = new FingerprintMatcher()
                                .index(template)
                                .match(finger);*/
                        if (match != null) {

                            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                            intent.putExtra("id", match.getId());
                            startActivity(intent);
                        }else {
                            fingerDisplay.setImageBitmap(bm);
                            messageText.setText("error");
                        }


                    }else {
                        errorMessage = data.getStringExtra("errorMessage");
                        messageText.setText("The error: "+errorMessage);
                    }
                }
                break;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void fireBaseData(){
        detailsList = new ArrayList<>();
        profile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileList = new ArrayList<DataSnapshot>();
                for (DataSnapshot dsp: dataSnapshot.getChildren()){
                   data = dsp.getValue(Profile.class);
                   Log.i("Tag",data.getFingerPrint());
                   byte[] img = Base64.getDecoder().decode(data.getFingerPrint());
                   FingerprintTemplate template = new FingerprintTemplate()
                           .dpi(500)
                           .create(img);
                   //updateTask(img);
                    details = new ProfileDetails(dsp.getKey(),template);
                    detailsList.add(details);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ProfileDetails find(FingerprintTemplate probe, Iterable<ProfileDetails> candidates) {
        FingerprintMatcher matcher = new FingerprintMatcher()
                .index(probe);
        ProfileDetails match = null;
        double high = 0;
        for (ProfileDetails candidate : candidates) {
            double score = matcher.match(candidate.getImageTemplate());
            if (score > high) {
                high = score;
                match = candidate;
            }else{
                messageText.setText("Error occurred");
            }
        }
        double threshold = 40;
        return high >= threshold ? match : null;
    }

    private void updateTask(byte[] image){
        new TemplateTask().execute(image);
    }

    public class TemplateTask extends AsyncTask<byte[],Void,FingerprintTemplate> {
        @Override
        protected FingerprintTemplate doInBackground(byte[]... bytes) {
            FingerprintTemplate template = new FingerprintTemplate()
                    .dpi(500)
                    .create(bytes[0]);
            return template;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(FingerprintTemplate template) {
            //templates = template;
            Log.i("Tag",template.toString());
        }
    }
}
