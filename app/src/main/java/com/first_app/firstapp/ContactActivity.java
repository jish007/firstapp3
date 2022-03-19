package com.first_app.firstapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactActivity extends AppCompatActivity {

    TextView t1,t2;
    ImageView thumbnailIv,thumbnailIv1;
    FloatingActionButton bf1;
    Button show;
    String numberEm;


    public static final int CONTACT_PERMISSION_CODE = 1;
    public static final int CONTACT_PICK_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        t1=(TextView) findViewById(R.id.contacts);
        t2=(TextView) findViewById(R.id.contacts1);
        show=(Button) findViewById(R.id.Send1);
        thumbnailIv=(ImageView) findViewById(R.id.thumbnailTv);
        thumbnailIv1=(ImageView) findViewById(R.id.thumbnailTv1);
        bf1=(FloatingActionButton) findViewById(R.id.addNumber);

        //sms part
        ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "Help me";

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(numberEm, null, message, null, null);
            }
        });

        //adding num from contacts part

        bf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkConPermission()){
                    //permission granted, pick contacts
                    pickConIntent();
                }
                else {
                    reqConPermission();
                }
            }
        });



       /* show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContactActivity.this,"number is " +numberEm, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private boolean checkConPermission(){
        boolean result = ContextCompat.checkSelfPermission(
                this,Manifest.permission.READ_CONTACTS) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void reqConPermission(){
        String[] per = {Manifest.permission.READ_CONTACTS};

        ActivityCompat.requestPermissions(this, per, CONTACT_PERMISSION_CODE);
    }

    private void pickConIntent(){
        // intent to pick contacts
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //handle permission request results
        if (requestCode == CONTACT_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // permission granted, can pick contacts
                pickConIntent();
            }
            else {
                // permission denied
                Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //handle intent results


        if (resultCode == RESULT_OK){
            // calls when user click a contact from list

            if (requestCode == CONTACT_PICK_CODE){
                t1.setText("");

                Cursor cursor1,cursor2;

                //get data from intent
                Uri uri = data.getData();

                cursor1 = getContentResolver().query(uri, null, null, null, null);

                if (cursor1.moveToFirst()){
                    // get contact details
                    String contactId = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    String contactName = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactThumbnail = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                    String idResults = cursor1.getString(cursor1.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    int idResultHold = Integer.parseInt(idResults);

                    if (idResultHold == 1){
                        cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "+contactId,null,null);
                        //a contact may have multiple phone numbers
                        while (cursor2.moveToNext()){
                            //get phone number
                            String contactNumber = cursor2.getString(cursor2.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //set details


                            t1.append("\nPhone: "+contactNumber);

                            numberEm = t1.getText().toString();

                            //before setting image, check if have or not
                            if (contactThumbnail != null){
                                thumbnailIv.setImageURI(Uri.parse(contactThumbnail));
                            }
                            else {
                                thumbnailIv.setImageResource(R.drawable.ic_baseline_person);

                            }
                        }
                        cursor2.close();
                    }
                    cursor1.close();
                }
            }
        }
        else {
            // calls when user click back button

        }
    }
}