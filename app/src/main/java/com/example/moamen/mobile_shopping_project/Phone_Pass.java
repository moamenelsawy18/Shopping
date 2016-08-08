package com.example.moamen.mobile_shopping_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Phone_Pass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone__pass);

        final DataBase DB = new DataBase(this);
        final EditText phone = (EditText) findViewById(R.id.phone);
        final EditText password = (EditText) findViewById(R.id.pass);
        Button getpass = (Button) findViewById(R.id.getPass);

        getpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = DB.GetPassword(phone.getText().toString());
                if (pass != null) {
                    password.setText(pass);
                }
                else Toast.makeText(getApplicationContext() , "Invalid Number" , Toast.LENGTH_LONG).show();
            }
        });
    }
}
