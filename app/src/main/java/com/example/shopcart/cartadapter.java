package com.example.shopcart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class cartadapter extends RecyclerView.Adapter<cartadapter.ViewHolder> {

    public final Context context;
    public   ArrayList<products> arraylist;
    mydatabase dbs;
    int id;

    public cartadapter(Context context, ArrayList<products>arrayList,int buyer_id){
        this.context=context;
        this.arraylist=arrayList;
        dbs=new mydatabase(context);
        this.id=buyer_id;
    }
    @NonNull
    @Override
    public cartadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cart_layout,parent,false);
        cartadapter.ViewHolder viewHolder=new cartadapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull cartadapter.ViewHolder holder, int position) {
        holder.title.setText("Title : "+arraylist.get(position).getTitle());
        holder.category.setText("Category : "+arraylist.get(position).getCategory());
        holder.quantity.setText("Quantity : "+arraylist.get(position).getQty()+"");
        holder.price2.setText("Price : "+arraylist.get(position).getPrice());

        int q=arraylist.get(position).getQty();
        int price=Integer.parseInt(arraylist.get(position).getPrice());
        int ans=q*price;
        holder.price.setText("Total Cost : "+ans);
       Bitmap bitmap=BitmapFactory.decodeByteArray(arraylist.get(position).getImg(),0,arraylist.get(holder.getAdapterPosition()).getImg().length);
       holder.img.setImageBitmap(bitmap);


       // DELETE PRODUCTS
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("REMOVE PRODUCTS").setMessage("ARE YOU SURE YOU WANT TO REMOVE THIS PRODUCT FROM CART??")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbs.removefromcart(id,arraylist.get(holder.getAdapterPosition()).getProduct_id());

                                arraylist.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();


                                notifyDataSetChanged();
                            }
                        }).setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });
        holder.inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbs.incrementQuantity(id,arraylist.get(holder.getAdapterPosition()).getProduct_id(),1);
                arraylist.get(holder.getAdapterPosition()).setQty(arraylist.get(holder.getAdapterPosition()).getQty()+1);
                holder.quantity.setText("Quantity : "+arraylist.get(holder.getAdapterPosition()).getQty()+"");
                int q=arraylist.get(position).getQty();
                int price=Integer.parseInt(arraylist.get(position).getPrice());
                int ans=q*price;
                holder.price.setText("Total Cost : "+ans);
            }
        });
        holder.dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arraylist.get(holder.getAdapterPosition()).getQty()==1){
                    return;
                }
                dbs.incrementQuantity(id,arraylist.get(holder.getAdapterPosition()).getProduct_id(),-1);
                arraylist.get(holder.getAdapterPosition()).setQty(arraylist.get(holder.getAdapterPosition()).getQty()-1);
                holder.quantity.setText("Quantity : "+arraylist.get(holder.getAdapterPosition()).getQty()+"");
                int q=arraylist.get(position).getQty();
                int price=Integer.parseInt(arraylist.get(position).getPrice());
                int ans=q*price;
                holder.price.setText("Total Cost : "+ans);

            }
        });



    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,price,quantity,category,price2;
        ImageView img,inc,dec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textView17);
            category=itemView.findViewById(R.id.textView18);
            quantity=itemView.findViewById(R.id.textView19);
            price2=itemView.findViewById(R.id.textView21);
            price=itemView.findViewById(R.id.textView20);
            img=itemView.findViewById(R.id.imageView6);
            inc=itemView.findViewById(R.id.imageView7);
            dec=itemView.findViewById(R.id.imageView8);
        }
    }
}
