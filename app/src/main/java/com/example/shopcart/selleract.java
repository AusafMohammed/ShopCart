package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selleract extends AppCompatActivity {
    public  String name;
    public  String who;
    Button b,paymentinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selleract);
        b=findViewById(R.id.button5);
        Intent i=getIntent();
        paymentinfo=findViewById(R.id.paymentinfobtn);
        name=i.getStringExtra("username");
        who=i.getStringExtra("who");


    }
    public  void movetomyprofile(View v){
        Intent i=new Intent(this,user_info.class);
        i.putExtra("username",name);
        i.putExtra("who",who);
        startActivity(i);
    }
    public void paymentinfobtnclicked(View v){
        Intent i=new Intent(this,paymentinfo.class);
        i.putExtra("username",name);
        i.putExtra("who",who);
        startActivity(i);
    }
    public void gotosellingactivity(View v){
        Intent i=new Intent(selleract.this,seller_selling_products.class);
        i.putExtra("username",name);
        i.putExtra("who",who);
        startActivity(i);
    }
    public void myordersclicked(View v){
Intent i=new Intent(selleract.this,sellers_myorders.class);
i.putExtra("username",name);
i.putExtra("who",who);
startActivity(i);
    }
}