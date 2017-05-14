package mihajlo.asc.hr.capio.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import mihajlo.asc.hr.capio.HttpRequests.AllUnitsTask;
import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.R;
import mihajlo.asc.hr.capio.Util.ImageLoadTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;

    private static final LatLng SYDNEY = new LatLng(45.791502, 15.955470);

    private static final LatLng ADELAIDE = new LatLng(45.799, 15.977);

    private static final LatLng PERTH = new LatLng(45.7933603, 15.9996207);

    private static final LatLng ALICE_SPRINGS = new LatLng(45.80565, 15.96271279);

    private static final LatLng FERLoc = new LatLng(45.800702, 15.971045);

    private Marker mSydney;

    private Marker mBrisbane;

    private Marker mAdelaide;

    private Marker mMelbourne;

    private TextView apartmentDesc;

    private HashMap<Marker,Unit> mapOfMarkerUnit = new HashMap<>();

    private List<Unit> listOfAllUnits = new ArrayList<>();
    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately


        new AllUnitsTask(new AllUnitsTask.AsynResponse() {
            @Override
            public void processFinish(List<Unit> output) {
                listOfAllUnits = output;
                addMarkersToMap();
            }
        }).execute();


        apartmentPicture = (ImageView) rootView.findViewById(R.id.appPic);
        apartmentDesc = (TextView) rootView.findViewById(R.id.appDesc);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;
                //addMarkersToMap();

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
        googleMap.setOnMarkerClickListener(this);

        Marker mFER = googleMap.addMarker(new MarkerOptions()
                .position(FERLoc)
                .title("FER")
                .snippet("Fakultet elektrotehnike i računarstva")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fakultet))
        );

        for(Unit tmpUnit : listOfAllUnits){
            Geocoder geocoder = new Geocoder(this.getContext(), Locale.getDefault());
            mihajlo.asc.hr.capio.Models.Location tmpLocation = tmpUnit.getLocation();
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocationName(tmpLocation.getStreetName() +" "+ tmpLocation.getHouseNumber()+" " + tmpLocation.getCity() , 1);
                if(addresses.size()>0){
                    Address address = addresses.get(0);
                    double longitude = address.getLongitude();
                    double latitude = address.getLatitude();
                    Marker tmpMarker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude,longitude))
                            .title(String.valueOf(tmpUnit.getPrice())+"kn")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon))
                            .infoWindowAnchor(0.5f, 0.5f));
                    tmpMarker.showInfoWindow();
                    mapOfMarkerUnit.put(tmpMarker,tmpUnit);

                }

            } catch (IOException e) {


                e.printStackTrace();
            }


        }


        /*mBrisbane = googleMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("900€")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon))
                .infoWindowAnchor(0.5f, 0.5f));
        mBrisbane.showInfoWindow();
        mapOfMarkerUnit.put(mBrisbane,new String[]{"Great place for students","900€","http://cdn.homedit.com/wp-content/uploads/2015/09/Student-apartment-in-Hong-Kong-window-desks.jpg"});


        mSydney = googleMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("1200€")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon))
                .infoWindowAnchor(0.5f, 0.5f));
        mSydney.showInfoWindow();

        //Unit tmpUnit1 = new Unit(12304123,"Super cool apartment",1200,10000,1200,200,4,)
        mapOfMarkerUnit.put(mSydney,new String[]{"Super cool apartment","1200€","https://newyorkapartmentsdotorg.files.wordpress.com/2011/06/new-york-apartment1.jpg"});

        // Creates a draggable marker. Long press to drag.
        mMelbourne = googleMap.addMarker(new MarkerOptions()
                .position(ALICE_SPRINGS)
                .title("1500€")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon))
                .infoWindowAnchor(0.5f, 0.5f));
        mMelbourne.showInfoWindow();

        mapOfMarkerUnit.put(mMelbourne,new String[]{"Great apartment","1500€","https://res.cloudinary.com/apartmentlist/image/upload/t_fullsize/r0ymlbhl0u9chmnzcula.jpg"});
        /*mAdelaide = googleMap.addMarker(new MarkerOptions()
                .position(ADELAIDE)
                .title("Adelaide")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon))
                .snippet("Population: 1,213,000"));


*/

        /*mAdelaide = googleMap.addMarker(new MarkerOptions()
                .position(ADELAIDE)
                .title("My Spot")
                .snippet("This is my spot!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                */
    }


        private Marker lastMarker;
        private ImageView apartmentPicture;



        @Override
        public boolean onMarkerClick(final Marker marker) {
            marker.showInfoWindow();

            if(mapOfMarkerUnit.containsKey(marker)){

                apartmentDesc.setText(mapOfMarkerUnit.get(marker).getPrice()+"kn/mj. \n"+ mapOfMarkerUnit.get(marker).getRooms()+"-sobni stan. \n" +mapOfMarkerUnit.get(marker).getDescription());

                if(mapOfMarkerUnit.get(marker).getImages().size()>0) {

                    new ImageLoadTask(mapOfMarkerUnit.get(marker).getImages().get(0).getUrl(), apartmentPicture).execute();
                }else{
                    Log.e("no images","no images");
                    }
                apartmentPicture.setVisibility(View.VISIBLE);
                apartmentDesc.setVisibility(View.VISIBLE);
            }else{

                apartmentPicture.setVisibility(View.INVISIBLE);
                apartmentDesc.setVisibility(View.INVISIBLE);
            }

            if (marker.equals(lastMarker))
            {
                apartmentPicture.setVisibility(View.INVISIBLE);
                apartmentDesc.setVisibility(View.INVISIBLE);
                lastMarker = null;

            }else{
                lastMarker = marker;
            }
            return true;

    }


}
