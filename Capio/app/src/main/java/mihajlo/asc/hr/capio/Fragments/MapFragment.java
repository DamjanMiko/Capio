package mihajlo.asc.hr.capio.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import mihajlo.asc.hr.capio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;

    private static final LatLng SYDNEY = new LatLng(45.8011209, 15.9708410);

    private static final LatLng ADELAIDE = new LatLng(45.799, 15.977);

    private static final LatLng PERTH = new LatLng(45.7933603, 15.9996207);

    private static final LatLng ALICE_SPRINGS = new LatLng(45.80565, 15.96271279);

    private Marker mSydney;

    private Marker mBrisbane;

    private Marker mAdelaide;

    private Marker mMelbourne;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;
                addMarkersToMap();

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    LatLng Zagreb = new LatLng(45.815399, 15.966568);
                    //googleMap.addMarker(new MarkerOptions().position(Zagreb).title("Babina Greda").snippet("Molimo dozvolite lokaciju aplikaciji ili zauvijek ostanite u Babinoj Gredi!"));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(Zagreb).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }else {
                    googleMap.setMyLocationEnabled(true);
                    GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {

                        boolean hasMapZoomedIn = false;

                        @Override
                        public void onMyLocationChange(Location location) {

                            if(hasMapZoomedIn || location==null) {
                                return;
                            }
                            else{
                                hasMapZoomedIn=true;
                                LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLoc).zoom(12).build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }

                        }
                    };
                    googleMap.setOnMyLocationChangeListener(myLocationChangeListener);


                }


            }
        });


        return rootView;
    }

    private void addMarkersToMap() {
        // Uses a colored icon.
        MarkerOptions marker = new MarkerOptions().position(PERTH).title("FER").snippet("$2000/mo").icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon));
        mBrisbane = googleMap.addMarker(marker);

        // Uses a custom icon with the info window popping out of the center of the icon.
        mSydney = googleMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("FER")
                .snippet("$420000/mo")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon))
                .infoWindowAnchor(0.5f, 0.5f));

        // Creates a draggable marker. Long press to drag.
        mMelbourne = googleMap.addMarker(new MarkerOptions()
                .position(ALICE_SPRINGS)
                .title("Melbourne")
                .snippet("Population: 4,137,400")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon))
                .draggable(true));

        mAdelaide = googleMap.addMarker(new MarkerOptions()
                .position(ADELAIDE)
                .title("Adelaide")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon))
                .snippet("Population: 1,213,000"));

    }


}
