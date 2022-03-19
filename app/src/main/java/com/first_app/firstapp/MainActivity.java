package com.first_app.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button regBtn,loginBtn;
    EditText username,password;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.uname);
        password=(EditText)findViewById(R.id.editText);
        loginBtn=(Button)findViewById(R.id.login);
        regBtn=(Button)findViewById(R.id.regid);
        dbHelper = new DbHelper(this);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RegPage.class);
                startActivity(i);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString().trim();
                String psw = password.getText().toString().trim();

                if (uname.isEmpty() | psw.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter data", Toast.LENGTH_SHORT).show();
                }
                else {
                  boolean result = dbHelper.checkUsernamePassword(uname,psw);
                  if (result == true){
                      Intent i=new Intent(getApplicationContext(),ContactActivity.class);
                      startActivity(i);
                      finish();
                  }
                  else {
                      Toast.makeText(MainActivity.this, "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                  }
                }

            }
        });
    }
}