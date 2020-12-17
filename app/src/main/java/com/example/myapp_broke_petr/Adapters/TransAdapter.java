package com.example.myapp_broke_petr.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapp_broke_petr.R;
import com.example.myapp_broke_petr.Transaction;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.ViewHolder>{

    Context context;
    List<Transaction> purchaseData; //List of all transaction by the user

    public TransAdapter(Context context, List<Transaction> purchaseData) {
        this.context = context;
        this.purchaseData = purchaseData;
    }

    //Inflates layout XML - item_trans and returns the viewholder that contains it

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemsView=LayoutInflater.from(context).inflate(R.layout.item_trans,parent,false);
        return new ViewHolder(itemsView);
    }

    //populate data into XML through the viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get item at current position
        Transaction transaction=purchaseData.get(position);
        //bind item data into the viewholder
        holder.bind(transaction);
    }

    //returns the number of items in the recycler view
    @Override
    public int getItemCount() {
        return purchaseData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemCategory;
        TextView tvAmount;
        TextView tvDate;
        TextView tvProduct;
        ImageButton ibDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCategory=itemView.findViewById(R.id.tvCat);
            tvAmount=itemView.findViewById(R.id.tvAmount);
            tvDate=itemView.findViewById(R.id.tvDate);
            tvProduct=itemView.findViewById(R.id.tvProduct);
            ibDelete=itemView.findViewById(R.id.ibDelete);

        }

        public void bind(Transaction transaction) {
            tvProduct.setText(transaction.getProduct());
            itemCategory.setText(transaction.getCategory());
            tvDate.setText(getDateString(transaction));
            tvAmount.setText(getAmountString(transaction));

        }

        public String getAmountString(Transaction transaction){
            DecimalFormat df = new DecimalFormat("#.##");
            String strAmount=df.format(transaction.getAmount());
            return strAmount;
        }

        public String getDateString(Transaction transaction){
            SimpleDateFormat formatter= new SimpleDateFormat("dd MMMM yyyy");
            String strDate=formatter.format(transaction.getDate());
            return strDate;
        }


    }

}
