package com.firstclass.praceando.marketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.ProductsCallback;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Product;
import com.firstclass.praceando.fragments.HeaderFragment;

import java.util.ArrayList;
import java.util.List;

public class MarketplaceFragment extends Fragment {
    private RecyclerView recyclerView;
    private final List<Product> productList = new ArrayList<>();
    private final PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);


        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.headerFragmentLayout, new HeaderFragment());
        fragmentTransaction.commit();


        recyclerView = view.findViewById(R.id.recyclerViewProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        progressBar = view.findViewById(R.id.progressBar);

        addProductsInTheList();
        return view;
    }

    private void addProductsInTheList() {
        progressBar.setVisibility(View.VISIBLE);
        postgresqlAPI.getProducts(new ProductsCallback() {
            @Override
            public void onSuccess(List<Product> products) {
                Log.e("PRODUCTS", ""+products);
                ProductItemAdapter productItemAdapter = new ProductItemAdapter(products);
                recyclerView.setAdapter(productItemAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}