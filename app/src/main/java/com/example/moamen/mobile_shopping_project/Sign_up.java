package com.example.moamen.mobile_shopping_project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Sign_up extends AppCompatActivity {

    int year_x,month_x,day_x;
    static final int DialogID =0;
    String BDate;
    TextView ChoosenBDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final DataBase SDB =new DataBase(Sign_up.this);

        final RadioButton male = (RadioButton) findViewById(R.id.male);
        RadioButton female = (RadioButton) findViewById(R.id.female);
        final EditText EnteredName = (EditText) findViewById(R.id.name);
        final EditText EnteredUserName = (EditText) findViewById(R.id.username);
        final EditText EnteredPassword = (EditText) findViewById(R.id.password);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        ChoosenBDate = (TextView) findViewById(R.id.birthdatechoosen);
        final EditText EnteredJob= (EditText) findViewById(R.id.job);
        final EditText EnteredPhone= (EditText) findViewById(R.id.phone);
        final Button SignUp = (Button) findViewById(R.id.sign_up_button);

        ChoosenBDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DialogID);
            }
        });


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Gender;

                if(male.isChecked())
                Gender = "Male";
                else
                Gender = "Female";

                final String name = EnteredName.getText().toString();
                final String UserName = EnteredUserName.getText().toString();
                final String Password = EnteredPassword.getText().toString();
                final String Job = EnteredJob.getText().toString();
                final String Phone = EnteredPhone.getText().toString();

                if ((name.isEmpty())||(UserName.isEmpty())||(Password.isEmpty())||(Gender.isEmpty())||(BDate.isEmpty())||(Job.isEmpty()||(Phone.isEmpty())))

                Toast.makeText(getApplicationContext(), "Enter Valid Data", Toast.LENGTH_LONG).show();

                 if((!name.isEmpty())&&(!UserName.isEmpty())&&(!Password.isEmpty())&&(!Gender.isEmpty())&&(!BDate.isEmpty())&&(!Job.isEmpty()&&(!Phone.isEmpty())))
                {
                    SDB.createNewAccount(name , UserName ,Password , Gender , BDate ,Job , Phone);
                    Intent i = new Intent(Sign_up.this, MyProducts.class);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id== DialogID)
            return new DatePickerDialog(this , dpickerListner , year_x,month_x,day_x);
        return null;
    }

    DatePickerDialog.OnDateSetListener dpickerListner
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x= year;
            month_x=monthOfYear;
            day_x=dayOfMonth;
            BDate = year_x+"/"+month_x+"/"+day_x;
            ChoosenBDate.setText(BDate);
        }
    };

}
