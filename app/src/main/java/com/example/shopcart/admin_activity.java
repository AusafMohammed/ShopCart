package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class admin_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
    public void addcouponbtnclicked(View v){
        Intent i=new Intent(this,coupon_activity.class);
        startActivity(i);

    }

    public void couponsbtnclciked(View v){
        Intent i=new Intent(this,getall_coupons.class);
        startActivity(i);
    }
    public void statsactivtyubtnclicked(View v){
        Intent i=new Intent(this,stat_activity.class);
        startActivity(i);
    }
}