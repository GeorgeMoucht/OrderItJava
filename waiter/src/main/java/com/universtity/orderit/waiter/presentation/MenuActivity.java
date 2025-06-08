package com.universtity.orderit.waiter.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.universtity.orderit.waiter.R;
import com.universtity.orderit.waiter.presentation.adapter.CategoryAdapter;
import com.universtity.orderit.waiter.presentation.adapter.ProductAdapter;
import com.universtity.orderit.waiter.presentation.viewmodel.MenuViewModel;

public class MenuActivity extends AppCompatActivity {

    private MenuViewModel viewModel;
    private RecyclerView categoryRecyclerView;
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private ProgressBar progressBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        categoryRecyclerView = findViewById(R.id.recyclerViewCategories);
        productRecyclerView = findViewById(R.id.recyclerViewProducts);
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.searchView);

        setupRecyclerViews();
        setupViewModel();
        setupSearchView();
    }

    private void setupRecyclerViews() {
        productAdapter = new ProductAdapter();
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerView.setAdapter(productAdapter);

        categoryAdapter = new CategoryAdapter(category -> {
            viewModel.onCategoryClicked(category);
        });

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        viewModel.getIsLoading().observe(this, loading -> {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        });

        viewModel.getCategories().observe(this, categories -> {
            categoryAdapter.submitList(categories);
        });

        viewModel.getFilteredProducts().observe(this, products -> {
            productAdapter.submitList(products);
            productRecyclerView.setVisibility(products.isEmpty() ? View.GONE : View.VISIBLE);
        });

        viewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.fetchCategories();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.onSearchQueryChanged(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.onSearchQueryChanged(newText);
                return true;
            }
        });
    }
}
