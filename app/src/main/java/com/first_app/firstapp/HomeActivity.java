package com.first_app.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    EditText n1,n2;
    Button btn1,btn2;
    NumDb nDB;
    public static String Number1;
    public static String Number2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        n1=(EditText) findViewById(R.id.num1);
        n2=(EditText) findViewById(R.id.num2);
        btn1=(Button) findViewById(R.id.subbtn);
        btn2=(Button) findViewById(R.id.show);
        nDB = new NumDb(this);
        Addnum();
        showNum();
        displayNum();
    }


    //for storing num
    public void Addnum(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = nDB.insertNum(n1.getText().toString(), n2.getText().toString());
                if (isInserted = true){
                    Toast.makeText(HomeActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    //showNum();
                }
                else{
                    Toast.makeText(HomeActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
                }
        });
    }


   public void showNum(){
        Cursor res = nDB.getData();
        if (res.getCount() == 0){
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
           buffer.append(res.getString(0)).toString();
           buffer.append(res.getString(1)).toString();
        }
    }

    public void displayNum(){
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),Number1 ,Toast.LENGTH_LONG).show();
            }
        });
    }


}