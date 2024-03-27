package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class getall_coupons extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<coupons>arrayList;
    mydatabase db;
    adapter_for_coupons ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getall_coupons);
        db=new mydatabase(this);
        arrayList=db.getallcoupons();
        recyclerView=findViewById(R.id.recyclerView4);
        ad=new adapter_for_coupons(this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ad);

    }
}