package com.universtity.orderit.waiter.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.universtity.orderit.waiter.R;
import com.universtity.orderit.waiter.presentation.adapter.TableAdapter;
import com.universtity.orderit.waiter.presentation.viewmodel.WaiterViewModel;

public class WaiterActivity extends AppCompatActivity {

    private WaiterViewModel viewModel;
    private TableAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout emptyStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);

        setupUI();
        setupViewModel();
    }

    private void setupUI() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTables);
        progressBar = findViewById(R.id.progressBar);
        emptyStateLayout = findViewById(R.id.emptyStateLayout);

        Button buttonRetry = findViewById(R.id.buttonRetry);
        buttonRetry.setOnClickListener(v -> {
            viewModel.fetchTables();
        });

        adapter = new TableAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(WaiterViewModel.class);

        viewModel.getTables().observe(this, tables -> {
            adapter.submitList(tables);
            emptyStateLayout.setVisibility(tables.isEmpty() ? View.VISIBLE : View.GONE);

            // Hide retry button if data is presented
            if (!tables.isEmpty()) {
                findViewById(R.id.buttonRetry).setVisibility(View.GONE);
            }
        });

        viewModel.getIsLoading().observe(this, loading ->
                progressBar.setVisibility(loading ? View.VISIBLE : View.GONE)
        );

        viewModel.getError().observe(this, error -> {
            if (error != null) {
                emptyStateLayout.setVisibility(View.VISIBLE);
                TextView emptyMessage = findViewById(R.id.textEmptyMessage);
                emptyMessage.setText(error);

                findViewById(R.id.buttonRetry).setVisibility(View.VISIBLE);
            }
        });

        viewModel.fetchTables();
    }
}
