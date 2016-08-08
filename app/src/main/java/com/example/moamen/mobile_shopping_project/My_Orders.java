package com.example.moamen.mobile_shopping_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


 public class My_Orders extends AppCompatActivity {

     ArrayAdapter<String> listAdapter;
     ListView Mylist;
     DataBase db;
     Cursor od;
     Cursor odn;
     int ProdQuan;
     int Ordid;
     int Price;
     int []Id;
     int []quant;
     int x;
     int y;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__orders);

         db = new DataBase(getApplicationContext());
         Mylist = (ListView) findViewById(R.id.list);
         listAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
         Mylist.setAdapter(listAdapter);

         Button confirm = (Button) findViewById(R.id.confirm);

         EditText tp = (EditText) findViewById(R.id.price);

         Ordid = getIntent().getExtras().getInt("OrdID");
         od = db.GetOrderQuan(Ordid);

         listAdapter.clear();

         Id = new int[od.getCount()];
         quant = new int[od.getCount()];

         int p = 0;
         int i=0;
            do {
                Id[i] = od.getInt(0);
                quant[i] = od.getInt(1);
                odn = db.GetOrderName(od.getInt(0));
                listAdapter.add("Product Name: " + odn.getString(0) + "||"  + " Price: " + odn.getInt(1) + " Quantity: " + od.getInt(1));
                p = p + odn.getInt(1);
                i++;
            }
            while (od.moveToNext());
         tp.setText(p+ "");

        Mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                x = Id[position];
                y = quant[position];
                registerForContextMenu(view);
            }
        });

         confirm.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(My_Orders.this,MyProducts.class);
                 startActivity(i);
             }
         });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.edit)
        {
            Cursor c = db.GetProductsDetails(x);
            ProdQuan = c.getInt(2);
            Intent i = new Intent(My_Orders.this , editorder.class);
            i.putExtra("prodquan" , ProdQuan );
            i.putExtra("ordid" , Ordid );
            i.putExtra("prodid" , x );
            i.putExtra("quan" , y );
            startActivity(i);
        }

        if (id == R.id.del)
        {
            Cursor c = db.GetProductsDetails(x);
            ProdQuan = c.getInt(2);
            db.deleteOrders(x,Ordid);
            db.updateProduct(x , ProdQuan+y );
            onRestart();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onRestart() {
        listAdapter.clear();

        int Ordid = getIntent().getExtras().getInt("OrdID");
        Cursor od=  db.GetOrderQuan(Ordid) ;

        if(od.getCount()!=0)
        do {
            Cursor odn = db.GetOrderName(od.getInt(0));
            listAdapter.add("Product Name: " + odn.getString(0) + " Quantity: " + od.getInt(1));
        }
        while (od.moveToNext());
        super.onRestart();
    }
}
