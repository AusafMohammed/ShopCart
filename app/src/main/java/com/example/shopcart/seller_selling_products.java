package com.example.shopcart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class seller_selling_products extends AppCompatActivity {
    EditText title,price,category;
    ImageView img;
    Spinner spn;
    String name,who;
    Bitmap recievedimg;
    mydatabase db;
    String categorystr[]={"Books","Electronics","Fashion and Apparel","Beauty Products","Sports and Fitness"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_selling_products);
        name=getIntent().getStringExtra("username");
        who=getIntent().getStringExtra("who");
        img=findViewById(R.id.myimage);
        db=new mydatabase(this);
        title=findViewById(R.id.mytitle);
        spn=findViewById(R.id.spinner);


        price=findViewById(R.id.mypric);
        ArrayAdapter<String >ad=new ArrayAdapter<String >(seller_selling_products.this, android.R.layout.simple_dropdown_item_1line,categorystr);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(ad);




    }
    public void imgisclicked(View v){
        Intent image=new Intent(Intent.ACTION_PICK);
        image.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(image,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1000){
                try{
                    Uri selectedimg=data.getData();
                    String[] filepathcolm={ MediaStore.Images.Media.DATA};
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        Cursor cursor=this.getContentResolver().query(selectedimg,filepathcolm,null,null);
                        cursor.moveToFirst();
                        int collumindex=cursor.getColumnIndex(filepathcolm[0]);
                        String picturepath=cursor.getString(collumindex);
                        cursor.close();
                        recievedimg= BitmapFactory.decodeFile(picturepath);
                        img.setImageBitmap(recievedimg);
                    }



                }catch (Exception e){

                }
            }
        }
    }


    public void uploadbtnisclicked(View v){
        if(title.getText().toString().equals("")||
        price.getText().toString().equals("")||

        recievedimg==null){
            FancyToast.makeText(this,"PLEASE ENTER ALL INFORMATION",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
 return;

        }
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        recievedimg.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[]bytes=byteArrayOutputStream.toByteArray();
        long ans=db.insertprod(name,title.getText().toString(),
                Integer.parseInt(price.getText().toString()),

                spn.getSelectedItem().toString(),
                bytes);
        if(ans==-1)
            FancyToast.makeText(this,"Failed :( ... Plzz try again",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
else {
            FancyToast.makeText(this, "UPLOAD SUCCESSFULL :)", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
            img.setImageResource(R.drawable.upload);
            title.setText("");
            price.setText("");


        }
    }

}