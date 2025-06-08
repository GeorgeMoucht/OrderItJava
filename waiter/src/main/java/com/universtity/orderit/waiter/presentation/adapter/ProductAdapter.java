package com.universtity.orderit.waiter.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.universtity.orderit.waiter.R;
import com.universtity.orderit.waiter.domain.model.Product;


public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductViewHolder> {
    public ProductAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView, descriptionView, priceView;
        private final ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.textViewProductName);
            descriptionView = itemView.findViewById(R.id.textViewProductDescription);
            priceView = itemView.findViewById(R.id.textViewProductPrice);
            imageView = itemView.findViewById(R.id.imageViewProduct);
        }

        public void bind(Product product) {
            nameView.setText(product.getName());
            descriptionView.setText(product.getDescription());
            priceView.setText(String.format("€ %.2f", product.getPrice()));

            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);
        }
    }
}

