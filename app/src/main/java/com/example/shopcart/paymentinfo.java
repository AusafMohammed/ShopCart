package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class paymentinfo extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView addbtn;
    mydatabase db;
    String name,who;
    ArrayList<visacard>arrayList;
    myadapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentinfo);
        name=getIntent().getStringExtra("username");
        who=getIntent().getStringExtra("who");
        arrayList=new ArrayList<>();

        db=new mydatabase(this);
        arrayList=db.getcards(name,who);
        ad=new myadapter(this,arrayList,db);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ad);
        addbtn=findViewById(R.id.imageView);
    }
    public void addbtnclicked(View v){
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);


       Button okay_text = dialog.findViewById(R.id.button3);
      Button  cancel_text = dialog.findViewById(R.id.button6);

        okay_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText number,name1,month,year,cvv;
                number=dialog.findViewById(R.id.cardnumber);
                name1=dialog.findViewById(R.id.name);
                month=dialog.findViewById(R.id.month);
                year=dialog.findViewById(R.id.year);
                cvv=dialog.findViewById(R.id.cvv);
                if(number.getText().toString().equals("")||
                name1.getText().toString().equals("")||
                month.getText().toString().equals("")||
                year.getText().toString().equals("")||cvv.getText().toString().equals("")){
                    FancyToast.makeText(paymentinfo.this,"PLEASE ENTER ALL INFORMATION",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    return;

                }
                int numb=Integer.parseInt(number.getText().toString());
                int mon=Integer.parseInt(month.getText().toString());
                int yr=Integer.parseInt(year.getText().toString());
                int c=Integer.parseInt(cvv.getText().toString());
                String nm=name1.getText().toString();
                long result=db.addcardinfo(numb,mon,yr,nm,name,who,c+"");
                if(result==-1)
                    FancyToast.makeText(paymentinfo.this,"Error..Plzz try again",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                else {
                    FancyToast.makeText(paymentinfo.this, "CARD SAVED SUCCESSFULLY", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    arrayList.add(new visacard(numb,mon,yr,c,nm,name,who));
                    ad.notifyDataSetChanged();

                }

                dialog.dismiss();
            }
        });

        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(paymentinfo.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

    }
    }
