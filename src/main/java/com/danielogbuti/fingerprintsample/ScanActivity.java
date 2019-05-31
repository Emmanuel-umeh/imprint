package com.danielogbuti.fingerprintsample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import asia.kanopi.fingerscan.Fingerprint;
import asia.kanopi.fingerscan.Status;

public class ScanActivity extends AppCompatActivity {
    private TextView statusText;
    private TextView errorText;
    private Fingerprint fingerprint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        statusText = (TextView) findViewById(R.id.tvStatus);
        errorText = (TextView) findViewById(R.id.tvError);
        fingerprint = new Fingerprint();

    }

    Handler updateHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int status = msg.getData().getInt("status");
            errorText.setText("");
            switch (status){
                case Status.INITIALISED:
                    statusText.setText("Setting up reader");
                    break;
                case Status.SCANNER_POWERED_ON:
                    statusText.setText("Reader Powered on");
                    break;
                case Status.READY_TO_SCAN:
                    statusText.setText("Ready to scan Finger");
                    break;
                case Status.FINGER_DETECTED:
                    statusText.setText("Finger Detected");
                    break;
                case Status.RECEIVING_IMAGE:
                    statusText.setText("Receiving image");
                    break;
                case Status.FINGER_LIFTED:
                    statusText.setText("Finger has been lifted off reader");
                    break;
                case Status.SCANNER_POWERED_OFF:
                    statusText.setText("Reader is off");
                    break;
                case Status.SUCCESS:
                    statusText.setText("FingerPrint successfully captured");
                    break;
                case Status.ERROR:
                    statusText.setText("Error");
                    errorText.setText(msg.getData().getString("errorMessage"));
                    break;
                default:
                    statusText.setText(String.valueOf(status));
                    errorText.setText(msg.getData().getString("errorMessage"));

            }
        }
    };


    Handler printHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte[] image;
            String errorMessage = "empty";
            int status = msg.getData().getInt("status");
            Intent intent = new Intent();
            intent.putExtra("status", status);
            if (status == Status.SUCCESS) {
                image = msg.getData().getByteArray("img");
                intent.putExtra("img", image);

            } else {
                errorMessage = msg.getData().getString("errorMessage");
                intent.putExtra("errorMessage", errorMessage);
            }
            setResult(RESULT_OK, intent);


        }
    };

    @Override
    protected void onStart() {
        fingerprint.scan(this,printHandler,updateHandler);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fingerprint.turnOffReader();
    }
}