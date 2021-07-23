package com.example.coffeebakery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CSKHActivity extends AppCompatActivity {

    LinearLayout cskh_hotline, cskh_sms, cskh_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cskhactivity);

        AnhXa();

        //Chức năng gọi điện thoại
        cskh_hotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = "0838532354";
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + sdt));
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(CSKHActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CSKHActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        startActivity(callIntent);
                    }
                } else {
                    startActivity(callIntent);
                }
            }
        });

        //Chức năng gửi tin nhắn SMS
        cskh_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });


        //CHức năng gửi mail
        cskh_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", new String("0838532354"));
        smsIntent.putExtra("sms_body", "");

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Đang gửi tin nhắn ...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CSKHActivity.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"tientien.6899@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Đang gửi mail..."));
            finish();
            Log.i("Đã hoàn thành gửi mail...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CSKHActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void AnhXa() {
        cskh_hotline = (LinearLayout) findViewById(R.id.CSKH_hotline);
        cskh_sms = (LinearLayout) findViewById(R.id.CSKH_sms);
        cskh_email = (LinearLayout) findViewById(R.id.CSKH_email);
    }
}