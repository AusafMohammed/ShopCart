package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class sellers_myorders extends AppCompatActivity {
    String name,who;
    ArrayList<products>arrayList;
    mydatabase db;
    myadapterforsellersorders ad;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellers_myorders);
        name=getIntent().getStringExtra("username");
        who=getIntent().getStringExtra("who");
        db=new mydatabase(this);
        arrayList=db.getproducts(name);
        ad=new myadapterforsellersorders(this,arrayList);
        recyclerView=findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ad);


    }
}