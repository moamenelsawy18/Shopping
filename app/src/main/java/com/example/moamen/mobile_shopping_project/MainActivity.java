package com.example.moamen.mobile_shopping_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {
    Boolean matchedusers;
    DataBase SDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText username = (EditText) findViewById(R.id.username_edit_text);
        final EditText password = (EditText) findViewById(R.id.PW_edit_text);

        SDB =new DataBase(getApplicationContext());
        Button bnt = (Button) findViewById(R.id.sign_in_button);

        Cursor Remember = SDB.remember_me();
        username.setText(Remember.getString(0));
        password.setText(Remember.getString(1));

        TextView tv = (TextView) findViewById(R.id.forget);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , Phone_Pass.class);
                startActivity(i);
            }
        });



        Button bnt1 = (Button) findViewById(R.id.sign_up2_button);
            bnt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                    {
                      Toast.makeText(getApplicationContext(), "Enter Your UserName & Password", Toast.LENGTH_LONG).show();
                    }
                    else {
                        matchedusers = SDB.check_pass(username.getText().toString(), password.getText().toString());
                        if (matchedusers == true)
                    {
                        Intent i = new Intent(MainActivity.this, MyProducts.class);
                        startActivity(i);
                    }
                        else
                        Toast.makeText(getApplicationContext(), "Enter Valid UserName & Password", Toast.LENGTH_LONG).show();
                         }
                }

            });

        bnt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Sign_up.class);
                startActivity(i);
            }
        });

    }

}
