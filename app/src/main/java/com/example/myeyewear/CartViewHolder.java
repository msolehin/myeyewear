package com.example.myeyewear;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    private ItemClickListener itemclickListner;

    public CartViewHolder (View itemView){
        super(itemView);
        txtProductName = itemView.findViewById (R.id.cart_product_name);
        txtProductPrice= itemView.findViewById (R.id.orderDate);
        txtProductQuantity = itemView.findViewById (R.id.cart_product_quantity);

    }

    @Override
    public void onClick(View view){
        itemclickListner.onClick(view, getAdapterPosition(), false);

    }

    public void setItemclickListner(ItemClickListener itemclickListner){

        this.itemclickListner=itemclickListner;
    }

}
