package com.example.moamen.mobile_shopping_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Laptops extends Fragment {

    public Laptops() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_laptops, container, false);

        DataBase db = new DataBase(getActivity().getApplicationContext());

        final Cursor prodname = db.GetProductsName(2);


        final ListView Mylist = (ListView) v.findViewById(R.id.lv1);
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        Mylist.setAdapter(listAdapter);

        listAdapter.clear();
        if(prodname != null) {
            while (!prodname.isAfterLast()) {
                listAdapter.add(prodname.getString(1));
                prodname.moveToNext();
            }
        }

        Mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = new Intent(getActivity(),Productsdetails.class);

                prodname.moveToPosition(position);

                i.putExtra("ID", prodname.getInt(0));
                startActivity(i);
            }
        });





        return v;
    }



}
