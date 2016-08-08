package com.example.moamen.mobile_shopping_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Shopping_Cart extends AppCompatActivity {

    int year_x,month_x,day_x;
    String Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping__cart);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        Date = year_x+"/"+month_x+"/"+day_x;

        final DataBase db = new DataBase(getApplicationContext());

        final String ProductName= getIntent().getExtras().getString("ProdName");
        final int ProdID = getIntent().getExtras().getInt("ProdID");
        final String ProdQuan = getIntent().getExtras().getString("ProdQuan");

        TextView tv = (TextView) findViewById(R.id.productname);
        final EditText quan = (EditText) findViewById(R.id.quan);
        final EditText phone = (EditText) findViewById(R.id.phonenum);
        final EditText OrderAddress = (EditText) findViewById(R.id.address);

        Button buy = (Button) findViewById(R.id.buy);

        tv.setText(ProductName);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String address = OrderAddress.getText().toString();
                final String phonenum = phone.getText().toString();
                final String quantity = quan.getText().toString();
                if(quantity.isEmpty() || phonenum.isEmpty() || address.isEmpty())
                    Toast.makeText(getApplicationContext(), "Enter Valid Data", Toast.LENGTH_LONG).show();
                else {

                    int EnteredQuan = Integer.parseInt(quantity);
                    if (EnteredQuan > Integer.parseInt(ProdQuan))
                    {
                        Toast.makeText(getApplicationContext(), "Enter Valid Quantity", Toast.LENGTH_LONG).show();
                    }
                    else {
                        db.updateProduct(ProdID, Integer.parseInt(ProdQuan) - EnteredQuan);
                        int custid = db.GetCustID(phonenum);
                        if(custid != 0)
                        {
                        int ordid = db.InsertNewOrder(Date, custid, address);
                        db.InsertNewOrderDetails(ordid, ProdID, EnteredQuan);

                        Intent i = new Intent(Shopping_Cart.this, My_Orders.class);
                        i.putExtra("OrdID", ordid);
                        startActivity(i);
                    }
                        else Toast.makeText(getApplicationContext(), "Enter Valid Phone", Toast.LENGTH_LONG).show();

                    }
                }
            }

        });

    }
}