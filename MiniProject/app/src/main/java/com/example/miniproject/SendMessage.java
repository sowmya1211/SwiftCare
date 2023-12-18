package com.example.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessage extends AppCompatActivity {

    IntentFilter intentFilter = new IntentFilter();
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            makeNotification(intent.getExtras().getString("message"));
        }
    };
    String doctor_phone_no="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);


        intentFilter.addAction("SMS_RECEIVED_ACTION");
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(SendMessage.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SendMessage.this, new String[] {Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        Intent intent = getIntent();
        String doctor_name = intent.getStringExtra("doctor_name");
        String doctor_phone_no = intent.getStringExtra("doctor_phone_no");
        String patient_name = intent.getStringExtra("patient_name");
        String appointment_date = intent.getStringExtra("appointment_date");
        String appointment_slot = intent.getStringExtra("appointment_slot");

        String msg2 = "Doctor "+doctor_name+" , you have an appointment with " + patient_name + " on " + appointment_date + " at " + appointment_slot;
        Toast.makeText(getApplicationContext(), msg2, Toast.LENGTH_SHORT).show();
        sendMsg(doctor_phone_no, msg2);

        Intent i = new Intent(SendMessage.this, LoginActivity.class);
        startActivity(i);
    }

    protected void sendMsg(String contactno, String msg) {
        String SENT = "Message Sent";
        String DELIVERED = "Message Delivered";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), PendingIntent.FLAG_IMMUTABLE);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+91"+contactno, null, msg, sentPI, deliveredPI);
    }

    @Override
    protected void onResume() {
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

    public void makeNotification(String msg) {
        String channelID = "CHANNEL_ID_NOTIFICATION";

        //Intent activityIntent = new Intent(this, SymptomsActivity.class);
        //PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelID);
        builder.setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Notification")
                .setContentText(msg)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelID);
            if(notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelID, "Notification", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        notificationManager.notify(0, builder.build());
    }

    private void showErrorDialog(String errorMessage) {
        // Create a custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_error_dialog);
        dialog.setCancelable(true);

        // Set the error message
        TextView errorMessageTextView = dialog.findViewById(R.id.errorMessage);
        errorMessageTextView.setText(errorMessage);

        // Set a listener to dismiss the dialog when clicked
        dialog.findViewById(R.id.errorIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }
}