package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BuyerAct extends AppCompatActivity {
    Button myprofile,paymentinfo;
     String name,who;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        Intent i=getIntent();
        paymentinfo=findViewById(R.id.paymentinfo);
        name=i.getStringExtra("username");
        who=i.getStringExtra("who");

        myprofile=findViewById(R.id.button4);

    }
    public void myprofileclkd(View v){
        Intent j=new Intent(this,user_info.class);
        j.putExtra("username",name);
        j.putExtra("who",who);
        startActivity(j);

    }
    public void paymentinfobtnclicked(View v){
        Intent i=new Intent(this,paymentinfo.class);
        i.putExtra("username",name);
        i.putExtra("who",who);
        startActivity(i);
    }
    public void buyproductsclicked(View v){
        Intent i=new Intent(this,buy_products_buyers.class);
        i.putExtra("username",name);
        i.putExtra("who",who);
        startActivity(i);

    }
    public  void cartbynclicked(View v){
        Intent i=new Intent(this,mycart.class);
        i.putExtra("username",name);
        i.putExtra("who",who);
        startActivity(i);
    }
    public void couponbtnisclicked(View v){
        Intent i=new Intent(this,getall_coupons.class);
        startActivity(i);
    }
}