package com.example.shopcart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapterforsellersorders extends RecyclerView.Adapter<myadapterforsellersorders.ViewHolder> {
  Context context;
  ArrayList<products>arrayList;
  mydatabase d;
  int n;

    public myadapterforsellersorders(Context c,ArrayList<products>arrayList){
        this.arrayList=arrayList;
        context=c;
        d=new mydatabase(c);

   }

    @NonNull
    @Override
    public myadapterforsellersorders.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.myordersforseller_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myadapterforsellersorders.ViewHolder holder, int position) {
        holder.title.setText("Title : "+arrayList.get(position).getTitle());
        holder.price.setText("Rs "+arrayList.get(position).getPrice());
        holder.category.setText("Category : "+arrayList.get(position).getCategory());
        holder.seller.setText("Seller : "+arrayList.get(position).getSeller().toUpperCase());
        n=d.getTotalQuantitySold(arrayList.get(position).getProduct_id());
        holder.qty.setText("Sold : "+n);
        Bitmap bitmap = BitmapFactory.decodeByteArray(arrayList.get(position).getImg(), 0, arrayList.get(position).getImg().length);
        holder.img.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,price,category,seller,qty;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textView9);
            price=itemView.findViewById(R.id.textView10);
            category=itemView.findViewById(R.id.textView12);
            seller=itemView.findViewById(R.id.textView13);
            qty=itemView.findViewById(R.id.textView47);
            img=itemView.findViewById(R.id.imageView2);
        }
    }
}
