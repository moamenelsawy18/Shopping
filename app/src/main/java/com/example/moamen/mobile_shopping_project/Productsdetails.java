package com.example.moamen.mobile_shopping_project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Productsdetails extends AppCompatActivity {
    String quan;
    String name;
    String price;

    DataBase db;
    Cursor ProdDetails;
    TextView prodname;
    TextView prodprice;
    TextView prodquan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productsdetails);

        prodname = (TextView) findViewById(R.id.ProdName);
        prodprice = (TextView) findViewById(R.id.ProdPrice);
        prodquan = (TextView) findViewById(R.id.ProdQuan);

        Button add = (Button) findViewById(R.id.addtocart);

        db = new DataBase(this);
        final int ProdID = getIntent().getExtras().getInt("ID");
        ProdDetails = db.GetProductsDetails(ProdID);

        name = ProdDetails.getString(0);
        price = ProdDetails.getString(1);
        quan  = ProdDetails.getString(2);
        prodname.setText("Name : " +name);
        prodprice.setText("Price : " +price);
        prodquan.setText("Quantity : " + quan);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Productsdetails.this, Shopping_Cart.class);
                i.putExtra("ProdName", name);
                i.putExtra("ProdQuan", quan);
                i.putExtra("ProdID", ProdID);
                startActivity(i);
                    }
        });

    }

    @Override
    protected void onRestart() {
        final int ProdID = getIntent().getExtras().getInt("ID");

        ProdDetails = db.GetProductsDetails(ProdID);
        name = ProdDetails.getString(0);
        price = ProdDetails.getString(1);
        quan  = ProdDetails.getString(2);
        prodname.setText("Name : " +name);
        prodprice.setText("Price : " +price);
        prodquan.setText("Quantity : " + quan);

        super.onRestart();
    }
}
