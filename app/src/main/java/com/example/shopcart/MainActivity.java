package com.example.shopcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {
    EditText name,pass1,pass2;
    TextView t;
    int f=0;
    Button b;
    RadioGroup rg;
    RadioButton r1,r2,r3;
   static mydatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.mydesc);
        db=new mydatabase(this);
        db.addadmininfo("admin","123@");



        pass1=findViewById(R.id.editTextTextPassword);
        pass2=findViewById(R.id.editTextTextPassword2);
        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton2);
        r3=findViewById(R.id.radioButton5);

        t=findViewById(R.id.textView);
        t.setVisibility(View.VISIBLE);
        rg=findViewById(R.id.radioGroup);
        b=findViewById(R.id.button);


    }

    public void btnclicked(View v){

        if(f==0) {
            String user, p1, p2,w;
            user = name.getText().toString();
            p1 = pass1.getText().toString();
            p2 = pass2.getText().toString();
            if (user.equals("")) {
                name.setError("Please Enter valid user-name");
                return;
            }
            if (p1.equals("")) {
                pass1.setError("Please Enter Valid Password");
                return;
            }
            if (p2.equals("")) {
                pass1.setError("Please Enter Valid Password");
                return;
            }
            if (!p1.equals(p2)) {
                FancyToast.makeText(this, "Password Not Matching :(", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                return;

            }
            if(r1.isChecked()){
                w=r1.getText().toString();

            }else
                w=r2.getText().toString();
            boolean checkuser=db.checkusername(user,w);
            if(checkuser==false){


                boolean result=db.insertdata(user,p1,w);
                if(result==true){
                    if(w.equals("Seller"))
                        db.insertseller(user);
                    else
                        db.insertbuyer(user);

                    FancyToast.makeText(this,"Sign Up Successfull",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    movetoanotheract(user,w);



                }
            }else{
                FancyToast.makeText(this,"User Already Exist :(",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }



        }else{
            if(r3.isChecked()){
                checkadmin();
                return;

            }
            String user,p,w;
            if(r1.isChecked())
                w=r1.getText().toString();
            else
                w=r2.getText().toString();
            user=name.getText().toString();
            p=pass1.getText().toString();
            if(user.equals("")){
                name.setError("Please Enter valid user-name");
                return;
            }
            if(p.equals("")){
                name.setError("Please Enter valid password");
                return;
            }
            boolean m=db.checkinfo(user,p,w);
            if(m==true){
                FancyToast.makeText(this,"Welcome "+user+" :)",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                movetoanotheract(user,w);
            }else{
                FancyToast.makeText(this,"Invalid Credentials",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            }


        }
    }

    private void checkadmin() {
        String code,adminpass;
        code=name.getText().toString();
        adminpass=pass1.getText().toString();
        if(code.equals("")||adminpass.equals("")){
            FancyToast.makeText(this,"Invalid Credentials",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
return;
        }
        int ans=db.checkadmin(code,adminpass);
        if(ans==-1)
        {
            FancyToast.makeText(this,"Invalid Credentials",FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
            return;

        }
        Intent i=new Intent(this,admin_activity.class);
        startActivity(i);


    }

    public void movetoanotheract(String name,String w){
        Intent i;
        if(w.equals("Seller")){
            i=new Intent(MainActivity.this,selleract.class);
            i.putExtra("username",name);
            i.putExtra("who",w);
            startActivity(i);
        }else{
            i=new Intent(MainActivity.this,BuyerAct.class);
            i.putExtra("username",name);
            i.putExtra("who",w);
            startActivity(i);
        }
    }
    public void textviewclicked(View v){
        if(f==0){
            t.setText("Click Here To Sign Up");
            f=1;
            pass2.setVisibility(View.GONE);
            r3.setVisibility(View.VISIBLE);

            b.setText("Log In");

        }else{
            t.setText("Click Here To Log In");
            f=0;
            r3.setVisibility(View.GONE);
            pass2.setVisibility(View.VISIBLE);

            b.setText("Sign In");
        }
    }
}