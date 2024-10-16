package com.firstclass.praceando.map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firstclass.praceando.R;
import com.firstclass.praceando.fragments.HeaderFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.firstclass.praceando.databinding.FragmentMapBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private FragmentMapBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        getLastLocation();

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.headerFragmentLayout, new HeaderFragment());
        fragmentTransaction.commit();

        return binding.getRoot();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapFragment.this);
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        int height = 130;
        int width = 130;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.img_bee);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap myLocationMarker = Bitmap.createScaledBitmap(b, width, height, false);

        LatLng pracaDoSamba = new LatLng(-23.4098528, -46.76213);
        LatLng nossaSenhora = new LatLng(-23.4894623, -46.6098262);
        LatLng pracaHerois = new LatLng(-23.5049739, -46.6306044);
        LatLng pracaMargarida = new LatLng(-23.4993033, -46.6267953);
        LatLng pracaVistaVerde = new LatLng(-23.4755539, -46.754478);
        LatLng pracaMorelis = new LatLng(-23.4784685, -46.6230578);
        LatLng userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        googleMap.addMarker(new MarkerOptions()
                .position(userLocation)
                .icon(BitmapDescriptorFactory.fromBitmap(myLocationMarker))
                .title("Você está aqui!"));

        googleMap.addMarker(
                new MarkerOptions()
                        .position(pracaDoSamba)
                        .icon(BitmapFromVector(
                                requireContext(),
                                R.drawable.ic_flower_filled))
                        .title("Praça do Samba"));

        googleMap.addMarker(
                new MarkerOptions()
                        .position(nossaSenhora)
                        .icon(BitmapFromVector(
                                requireContext(),
                                R.drawable.ic_flower_filled))
                        .title("Nossa senhora dos prazeres"));

        googleMap.addMarker(
                new MarkerOptions()
                        .position(pracaHerois)
                        .icon(BitmapFromVector(
                                requireContext(),
                                R.drawable.ic_flower_filled))
                        .title("Praça Heróis da FEB"));

        googleMap.addMarker(
                new MarkerOptions()
                        .position(pracaMargarida)
                        .icon(BitmapFromVector(
                                requireContext(),
                                R.drawable.ic_flower_filled))
                        .title("Praça Margarida A. Gimenez"));

        googleMap.addMarker(
                new MarkerOptions()
                        .position(pracaVistaVerde)
                        .icon(BitmapFromVector(
                                requireContext(),
                                R.drawable.ic_flower_filled))
                        .title("Praça vista verde"));

        googleMap.addMarker(
                new MarkerOptions()
                        .position(pracaMorelis)
                        .icon(BitmapFromVector(
                                requireContext(),
                                R.drawable.ic_flower_filled))
                        .title("Praça Novais Morelis"));
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == FINE_PERMISSION_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            } else {
                Toast.makeText(requireContext(), "Permissões de localização negada", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
