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

import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.ProductsCallback;
import com.firstclass.praceando.R;
import com.firstclass.praceando.calendar.CalendarEventItemAdapter;
import com.firstclass.praceando.calendar.CalendarViewFragment;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.entities.Product;
import com.firstclass.praceando.fragments.HeaderFragment;

import java.util.ArrayList;
import java.util.List;

public class MarketplaceFragment extends Fragment {
    private RecyclerView recyclerView;
    private final List<Product> productList = new ArrayList<>();
    private final PostgresqlAPI postgresqlAPI = new PostgresqlAPI();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);


        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.headerFragmentLayout, new HeaderFragment());
        fragmentTransaction.commit();


        recyclerView = view.findViewById(R.id.recyclerViewProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        addProductsInTheList();
        return view;
    }

    private void addProductsInTheList() {
        postgresqlAPI.getProducts(new ProductsCallback() {
            @Override
            public void onSuccess(List<Product> products) {
                Log.e("PRODUCTS", ""+products);
                products.get(0).setImageUrl("https://firebasestorage.googleapis.com/v0/b/praceando-dbad6.appspot.com/o/avatars%2FAvatar1.png?alt=media&token=a9346a19-2340-4de7-b3ec-7c125562bff2");
                products.get(1).setImageUrl("https://cdn-icons-png.freepik.com/512/13748/13748379.png");
                products.get(2).setImageUrl("https://cdn-icons-png.freepik.com/512/13748/13748670.png");
                ProductItemAdapter productItemAdapter = new ProductItemAdapter(products);
                recyclerView.setAdapter(productItemAdapter);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}