package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class buy_products_buyers extends AppCompatActivity {
mydatabase db;
    String categorystr[]={"ALL","Books","Electronics","Fashion and Apparel","Beauty Products","Sports and Fitness"};
    Spinner spn;
    RecyclerView recyclerView;
    Button searchcat;
    ArrayList<products>arrayList;

    String name;

    myadapterforbuyingproducts myadapterforbuyingproducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_products_buyers);

        spn=findViewById(R.id.spinner3);
        searchcat=findViewById(R.id.button10);
        name=getIntent().getStringExtra("username");
        db=new mydatabase(this);
        ArrayAdapter<String > ad=new ArrayAdapter<String >(buy_products_buyers.this, android.R.layout.simple_dropdown_item_1line,categorystr);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(ad);
        arrayList=db.getallproducts();
        recyclerView=findViewById(R.id.recyclerview10);
       myadapterforbuyingproducts= new myadapterforbuyingproducts(this,arrayList,name);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapterforbuyingproducts);

    }
    public void searchbtnisclicked(View v){
        String category;
        ArrayList<products>newdatalist;
        if(spn.getSelectedItem().toString().equals("ALL")) {
            newdatalist=db.getallproducts();
            arrayList.clear();
            arrayList.addAll(newdatalist);
            myadapterforbuyingproducts.notifyDataSetChanged();
            return;

        }
        category=spn.getSelectedItem().toString();
        newdatalist=db.getprofuctscategory(category);
        arrayList.clear();
        arrayList.addAll(newdatalist);
        myadapterforbuyingproducts.notifyDataSetChanged();

    }
}