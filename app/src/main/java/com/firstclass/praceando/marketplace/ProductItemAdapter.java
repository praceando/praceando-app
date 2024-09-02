package com.firstclass.praceando.marketplace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder> {
    private List<Product> productList;
    public ProductItemAdapter(List<Product> productList) {
        this.productList = productList;
    }
    @NonNull
    @Override
    public ProductItemAdapter.ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductItemViewHolder(viewItem);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductItemAdapter.ProductItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(productList.get(position).getTitle());
        holder.price.setText("R$:"+productList.get(position).getPrice());
        holder.description.setText(productList.get(position).getDescription());

        Picasso.get()
                .load(productList.get(position).getImageUrl())
                .into(holder.imageUrl);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", productList.get(position).getTitle());
                bundle.putString("description", productList.get(position).getDescription());
                bundle.putDouble("price", productList.get(position).getPrice());
                bundle.putString("image", productList.get(position).getImageUrl());

                Intent intent = new Intent(holder.itemView.getContext(), Payment.class);
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class ProductItemViewHolder extends RecyclerView.ViewHolder {
        TextView title,  description, price;
        ImageView imageUrl;
        public ProductItemViewHolder(@NonNull View viewItem) {
            super(viewItem);
            title = viewItem.findViewById(R.id.productTitle);
            description = viewItem.findViewById(R.id.productDescription);
            imageUrl = viewItem.findViewById(R.id.productImage);
            price = viewItem.findViewById(R.id.productPrice);
        }
    }
}