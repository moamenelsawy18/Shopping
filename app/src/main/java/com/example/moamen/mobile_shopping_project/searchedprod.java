package com.example.moamen.mobile_shopping_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class searchedprod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchedprod);

        DataBase db = new DataBase(getApplicationContext());
        String query = getIntent().getExtras().getString("search");
        final Cursor c = db.searchname(query);

        final ListView Mylist = (ListView) findViewById(R.id.Slv);
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        Mylist.setAdapter(listAdapter);

        listAdapter.clear();
        if(c != null) {
            while (!c.isAfterLast()) {
                listAdapter.add(c.getString(0));
                c.moveToNext();

            }
        }
        else Toast.makeText(getApplicationContext(), "No Such Name" , Toast.LENGTH_LONG).show();

        Mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = new Intent(searchedprod.this,Productsdetails.class);

                c.moveToPosition(position);

                i.putExtra("ID", c.getInt(0));
                startActivity(i);
            }
        });
    }
}
