package com.firstclass.praceando.marketplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firstclass.praceando.R;
import com.firstclass.praceando.calendar.CalendarEventItemAdapter;
import com.firstclass.praceando.calendar.CalendarViewFragment;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.entities.Product;
import com.firstclass.praceando.fragments.HeaderFragment;

import java.util.ArrayList;
import java.util.List;

public class MarketplaceFragment extends Fragment {
    RecyclerView recyclerView;
    List<Product> productList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);


        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.headerFragmentLayout, new HeaderFragment());
        fragmentTransaction.commit();


        productList.add(new Product("avatar padrão feio", 1.99, "Avatar Futuristico", "https://cdn-icons-png.freepik.com/512/13748/13748555.png"));
        productList.add(new Product("avatar padrão feio", 1.99, "Avatar Futuristico", "https://cdn-icons-png.freepik.com/512/13748/13748436.png"));
        productList.add(new Product("avatar padrão feio", 1.99, "Avatar Futuristico", "https://cdn-icons-png.freepik.com/512/13748/13748379.png"));
        productList.add(new Product("avatar padrão feio", 1.99, "Avatar Futuristico", "https://cdn-icons-png.freepik.com/256/13748/13748527.png"));
        productList.add(new Product("avatar padrão feio", 1.99, "Avatar Futuristico", "https://cdn-icons-png.freepik.com/512/13748/13748670.png"));
        productList.add(new Product("avatar padrão feio", 1.99, "Avatar Futuristico", "https://cdn-icons-png.freepik.com/512/13748/13748614.png"));
        productList.add(new Product("avatar padrão feio", 1.99, "Avatar Futuristico", "https://cdn-icons-png.freepik.com/512/13748/13748570.png"));
        productList.add(new Product("avatar padrão feio", 1.99, "Avatar Futuristico", "https://cdn-icons-png.freepik.com/512/13748/13748687.png"));


        recyclerView = view.findViewById(R.id.recyclerViewProduct);
        ProductItemAdapter productItemAdapter = new ProductItemAdapter(productList);
        recyclerView.setAdapter(productItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
}