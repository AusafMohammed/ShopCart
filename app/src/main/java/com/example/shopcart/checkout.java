package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class checkout extends AppCompatActivity {
    TextView title,price,qty,totalcost,finalcost;
    ArrayList<products>arrayList;
    mydatabase db;
    EditText codeenterd;
    String name;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        title=findViewById(R.id.textView36);
        price=findViewById(R.id.textView37);
        name=getIntent().getStringExtra("username");
        codeenterd=findViewById(R.id.editTextTextPersonName);
        qty=findViewById(R.id.textView39);
        db=new mydatabase(this);
        totalcost=findViewById(R.id.textView38);
        finalcost=findViewById(R.id.textView42);


        totalcost.setText("");
        arrayList=mycart.cartlist;
        displaythebill();

    }
    public void displaythebill(){
        int fans=0,ans,p,q;
        title.setText("");
        price.setText("");
        qty.setText("");
        totalcost.setText("");
        finalcost.setText("");
        for(int i=0;i<arrayList.size();i++){
            title.setText(title.getText()+"\n"+arrayList.get(i).getTitle());
            price.setText(price.getText()+"\n"+arrayList.get(i).getPrice());
            qty.setText(qty.getText()+"\n"+arrayList.get(i).getQty());
            q=arrayList.get(i).getQty();
            p=Integer.parseInt(arrayList.get(i).getPrice());
            ans=p*q;
            fans=fans+ans;
            totalcost.setText(totalcost.getText()+"\n"+ans);


        }
        finalcost.setText(fans+"");
    }


    public void applybtnisclicked(View v){
        displaythebill();
        String code;
        code=codeenterd.getText().toString();
        if(code.equals("")){
            return;
        }
        int ans=db.checkcoupon(code);
        if(ans==-1){
            FancyToast.makeText(this,"PlZZ ENTER A VALID CODE",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
            return;
        }
        int f=Integer.parseInt(finalcost.getText().toString());
        int ans2=f-(f*ans/100);
        finalcost.setText(ans2+"");



    }

    public void placeorderbtnclicked(View v){
        int id=db.getbuyerid(name);
        db.clearproductsfromcart(id);
        FancyToast.makeText(this,"ORDER PLACED :)",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
        db.addtransaction(id,Integer.parseInt(finalcost.getText().toString()));
        String code;
        code=codeenterd.getText().toString();
        int ans=db.checkcoupon(code);
        if(ans!=-1)
            db.addcoupon_used(code);


        for(int i=0;i<arrayList.size();i++){
            db.insertsoldprofucts(id,arrayList.get(i).getProduct_id(),arrayList.get(i).getQty());
        }
        mycart.clearmycart();
        finish();



    }
}