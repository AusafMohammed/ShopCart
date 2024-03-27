package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class stat_activity extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6;
    mydatabase db;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        t1=findViewById(R.id.textView25);
        db=new mydatabase(this);
        t6=findViewById(R.id.textView46);
        String ans=db.getBuyerWithHighestTotalTransactionAmount();
        t1.setText(ans);
        t2=findViewById(R.id.textView27);
        ans=db.getSellerWithMaxQuantitySold();
        t2.setText(ans);
        t3=findViewById(R.id.textView29);
        ans=db.getCategoryWithMaxCount();
        t3.setText(ans);
        t4=findViewById(R.id.textView31);
        int n=db.getTotalOrdersPlaced();
        t4.setText(n+"");
        t5=findViewById(R.id.textView44);
        n=db.getTotalTransactionAmount();
        t5.setText("Rs "+n+"");
        n=db.getBiggestTransactionAmount();
        t6.setText("Rs "+n);


    }
}