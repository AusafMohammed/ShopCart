package com.example.shopcart;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class adapter_for_coupons extends RecyclerView.Adapter<adapter_for_coupons.ViewHolder> {
    Context context;
    ArrayList<coupons>arrayList;
    static ArrayList<String>colorlist=new ArrayList<>();
    static {
        colorlist.add("#F80E1A");
        colorlist.add("#878CFF");
        colorlist.add("#F8EC0E");
        colorlist.add("#5865F2");
        colorlist.add("#1AF80E");

    }
    public adapter_for_coupons(Context context, ArrayList<coupons>arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public adapter_for_coupons.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.display_coupons,parent,false);
        adapter_for_coupons.ViewHolder viewHolder=new adapter_for_coupons.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_for_coupons.ViewHolder holder, int position) {
        holder.code.setText("CODE : "+arrayList.get(position).getCode());
        holder.sponsor.setText("SPONSOR : "+arrayList.get(position).getSponsor());
        holder.disc.setText("DISCOUNT : "+arrayList.get(position).getDiscount());
        Random random = new Random();


        int randomNumber = random.nextInt(5);

        int color = Color.parseColor(colorlist.get(randomNumber));
        holder.mycard.setCardBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView code,sponsor,disc;
        CardView mycard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.coupon_code);
            sponsor=itemView.findViewById(R.id.sponsors);
            disc=itemView.findViewById(R.id.discount);
            mycard=itemView.findViewById(R.id.mycard);
        }
    }
}
