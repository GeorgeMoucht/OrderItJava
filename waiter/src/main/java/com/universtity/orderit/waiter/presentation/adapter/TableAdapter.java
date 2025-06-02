package com.universtity.orderit.waiter.presentation.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.universtity.orderit.waiter.R;
import com.universtity.orderit.waiter.domain.model.Table;

public class TableAdapter extends ListAdapter<Table, TableAdapter.TableViewHolder> {

    public interface OnTableClickListener {
        void onTableClick(Table table);
    }

    private final OnTableClickListener listener;

    public TableAdapter(OnTableClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Table> DIFF_CALLBACK = new DiffUtil.ItemCallback<Table>() {
        @Override
        public boolean areItemsTheSame(@NonNull Table oldItem, @NonNull Table newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Table oldItem, @NonNull Table newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        Table table = getItem(position);
        holder.bind(table);
        holder.itemView.setOnClickListener(v -> listener.onTableClick(table));
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        private final TextView tableName;
        private final TextView status;
        private final CardView card;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            tableName = itemView.findViewById(R.id.textViewTableName);
            status = itemView.findViewById(R.id.textViewStatus);
            card = (CardView) itemView;
        }

        public void bind(Table table) {
            tableName.setText(table.getName());
            status.setText(table.getStatus());

            String statusText = table.getStatus().toLowerCase();
            int bgColor;

            if (statusText.contains("ελεύθερο")) {
                bgColor = Color.parseColor("#E8F5E9"); // Light green
            } else if (statusText.contains("κρατημένο")) {
                bgColor = Color.parseColor("#FFF8E1"); // Light orange
            } else if (statusText.contains("κατειλημμένο")) {
                bgColor = Color.parseColor("#FFEBEE"); // Light red
            } else {
                bgColor = Color.parseColor("#ECEFF1"); // Neutral
            }

            card.setCardBackgroundColor(bgColor);
        }
    }
}