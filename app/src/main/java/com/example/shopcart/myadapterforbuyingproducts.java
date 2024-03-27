package com.example.shopcart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class myadapterforbuyingproducts extends RecyclerView.Adapter<myadapterforbuyingproducts.ViewHolder> {

    Context c;
    ArrayList<products>arrayList;
    mydatabase dbs;
    String name;
    public  myadapterforbuyingproducts(Context c,ArrayList<products>arrayList,String name){
        this.c=c;
        this.arrayList=arrayList;
        this.name=name;
    }
    @NonNull
    @Override
    public myadapterforbuyingproducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.layoutforbuyingproducts,parent,false);
        myadapterforbuyingproducts.ViewHolder viewHolder=new myadapterforbuyingproducts.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myadapterforbuyingproducts.ViewHolder holder, int position) {
        holder.title.setText("Title : "+arrayList.get(position).getTitle());
        holder.price.setText("Rs "+arrayList.get(position).getPrice());
        holder.cat.setText("Category : "+arrayList.get(position).getCategory());
        Bitmap bitmap = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
        holder.img.setImageBitmap(bitmap);
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbs=new mydatabase(c);
                int buyer_id=dbs.getbuyerid(name);
                int prod_id=arrayList.get(holder.getAdapterPosition()).getProduct_id();
               int ans= dbs.checkproductincart(buyer_id,prod_id,1);
               if(ans==-1){
                   FancyToast.makeText(c,"Product already in your cart",FancyToast.LENGTH_LONG,FancyToast.CONFUSING,true).show();
                   return;
               }else{
                   FancyToast.makeText(c,"Product successfully added to cart!!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
               }

            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price,title,cat;
        ImageView img,cart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textView14);
            price=itemView.findViewById(R.id.textView15);
            cat=itemView.findViewById(R.id.textView16);
            img=itemView.findViewById(R.id.imageView3);
            cart=itemView.findViewById(R.id.imageView5);
        }
    }
}
