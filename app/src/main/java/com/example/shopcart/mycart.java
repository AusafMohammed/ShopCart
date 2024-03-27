package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class mycart extends AppCompatActivity {
     mydatabase dbs;
    String name,type;
    public static ArrayList<products>arrayList;
    public static ArrayList<products>cartlist;
    RecyclerView recyclerView;
   static cartadapter ad;
    TextView t;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);
        dbs=new mydatabase(this);
        t=findViewById(R.id.textView23);
        recyclerView=findViewById(R.id.recyclerView3);

        name=getIntent().getStringExtra("username");
        type=getIntent().getStringExtra("who");
        id=dbs.getbuyerid(name);
        arrayList=dbs.getcartproducts(id);
        if(arrayList.size()>0){
            t.setVisibility(View.GONE);
        }
        ad=new cartadapter(this,arrayList,id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ad);

    }



    public void gotocheckoutbtnclicked(View v)
    {
        Intent i=new Intent(this,checkout.class);
        i.putExtra("username",name);
        cartlist=dbs.getcartproducts(id);
        startActivity(i);
    }
    static public void clearmycart(){
        arrayList.clear();
        ad.notifyDataSetChanged();

    }

}