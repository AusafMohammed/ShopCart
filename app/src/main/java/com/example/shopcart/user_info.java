package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class user_info extends AppCompatActivity {
    TextView t1,t2;
    String name,who;
    Button b;
    EditText dob,mobile,state,address;
    userinfo u;
    mydatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        db=new mydatabase(this);



        t1=findViewById(R.id.textView5);
        t2=findViewById(R.id.textView6);
        name=getIntent().getStringExtra("username");
        who=getIntent().getStringExtra("who");



        t1.setText(name.toUpperCase());
        b=findViewById(R.id.button2);
        t2.setText(who.toUpperCase());
        dob=findViewById(R.id.dob);
        mobile=findViewById(R.id.mobile);
        state=findViewById(R.id.state);
        address=findViewById(R.id.adress);

        u=db.getuserinfos(name,who);


        if(u!=null){
            dob.setText(u.getDob());
            state.setText(u.getState());
            address.setText(u.getAddress());
            mobile.setText(u.getMobile());

        }




    }
    public void savebtnclicked(View v){
       int a= db.saveinfp(name,who,dob.getText().toString(),state.getText().toString(),address.getText().toString(),mobile.getText().toString());
       if(a>0){
           FancyToast.makeText(this,"Updated Successfull",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

       }


    }

}