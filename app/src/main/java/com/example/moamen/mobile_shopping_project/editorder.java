package com.example.moamen.mobile_shopping_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editorder extends AppCompatActivity {
    Button Change;
    EditText quan;
    DataBase db;
    String quantity;
    String newquantity;
    int ProdQuan;
    int ordid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorder);

        quan= (EditText) findViewById(R.id.ediquan);

        Change= (Button) findViewById(R.id.Change);
        db = new DataBase(this);

        int orderquan = getIntent().getExtras().getInt("quan");
        final int Prodid = getIntent().getExtras().getInt("prodid");
        ProdQuan = getIntent().getExtras().getInt("prodquan");
        ordid = getIntent().getExtras().getInt("ordid");
        quan.setText(orderquan +"");
        quantity=quan.getText().toString();


        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newquantity=quan.getText().toString();
                int newquan = (ProdQuan +(Integer.parseInt(quantity)-Integer.parseInt(newquantity)));
                db.updateProduct(Prodid ,newquan );
                db.updateOrders(Prodid,ordid,Integer.parseInt(newquantity));
                Toast.makeText(getApplicationContext(),"Order Changed",Toast.LENGTH_LONG).show();
            }
        });



    }
}
