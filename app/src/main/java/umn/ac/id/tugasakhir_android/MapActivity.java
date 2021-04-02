package umn.ac.id.tugasakhir_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Inisialisasi Variabel
    GoogleMap gMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Mendapatkan fragment di xml
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        // Assign variabel
        gMap = googleMap;

        // Tambahkan marker
        LatLng foodxpress = new LatLng(-1.5974365338998333, 103.62448445156222);
        gMap.addMarker(new MarkerOptions().position(foodxpress).title("Lokasi Restoran"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(foodxpress));
    }


}