package com.example.shopcart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class myadapter extends RecyclerView.Adapter<myadapter.ViewHolder> {
    Context context;
    mydatabase db;
    ArrayList<visacard>arrayList;
    myadapter(Context c, ArrayList<visacard>arrayList,mydatabase d){
        db=d;
        this.arrayList=arrayList;
        context=c;


    }

    @NonNull
    @Override
    public myadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.ViewHolder holder, int position) {
        String num=arrayList.get(position).getCardnumber()+"";
        int length = num.length();
        StringBuilder formattedString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            if (i > 0 && i % 4 == 0) {
                formattedString.append("    "); // Add a space every 4 characters
            }else
                formattedString.append(" ");

            formattedString.append(num.charAt(i));
        }
        holder.number.setText(formattedString.toString());



        holder.name.setText(arrayList.get(position).getCardname().toUpperCase());
        holder.month.setText(arrayList.get(position).getMonth()+"");
        holder.year.setText(arrayList.get(position).getYear()+"");
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("DELETE CARD").setMessage("ARE YOU SURE YOU WANT TO DELETE THIS CARD??")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String nm,numb,user,who;
                                nm=arrayList.get(holder.getAdapterPosition()).getCardname();
                                numb=arrayList.get(holder.getAdapterPosition()).getCardnumber()+"";
                                user=arrayList.get(holder.getAdapterPosition()).getUser();
                                who=arrayList.get(holder.getAdapterPosition()).getWho();
                                db.deleteCardInfo(nm,numb,user,who);
                                arrayList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();


                                notifyDataSetChanged();
                            }
                        }).setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number,name,month,year;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number=itemView.findViewById(R.id.card_number);
            name=itemView.findViewById(R.id.card_name);
            month=itemView.findViewById(R.id.exp_month);
            year=itemView.findViewById(R.id.exp_year);

        }
    }
}
