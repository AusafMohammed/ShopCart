package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shashank.sony.fancytoastlib.FancyToast;

public class coupon_activity extends AppCompatActivity {
    EditText code,sponsor,discount;
    Button b;
    mydatabase dbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        sponsor=findViewById(R.id.sponsors);
        dbs=new mydatabase(this);
        code=findViewById(R.id.coupon_code);
        discount=findViewById(R.id.discount);
        b=findViewById(R.id.button16);

    }
    public void uploadbtnclicked(View v){
        String codes,sponsors,disc;
        codes=code.getText().toString();
        sponsors=sponsor.getText().toString();
        disc=discount.getText().toString();
        if(codes.equals("")||disc.equals("")||sponsors.equals(""))
            return;
        if(dbs.checkcoupon(codes)!=-1){
            FancyToast.makeText(this,"CODE ALREADY EXIST",FancyToast.LENGTH_SHORT,FancyToast.WARNING,true).show();
            return;
        }

        dbs.insertcoupons(codes,sponsors,disc);
        FancyToast.makeText(this,"COUPON ADDED SUCCESSFULL",FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,true).show();
        code.setText("");
        discount.setText("");
        sponsor.setText("");

    }
}