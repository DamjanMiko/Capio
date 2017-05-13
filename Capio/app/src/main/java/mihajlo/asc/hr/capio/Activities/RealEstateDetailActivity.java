package mihajlo.asc.hr.capio.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent.RealEstateItem;
import mihajlo.asc.hr.capio.Adapters.ImageAdapter;
import mihajlo.asc.hr.capio.HttpRequests.UserByUnitIdTask;
import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableLocation;
import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableUnit;
import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.Models.UserObject;
import mihajlo.asc.hr.capio.R;

public class RealEstateDetailActivity extends AppCompatActivity {

    private ViewPager viewPagerImages;
    private RelativeLayout btnContact;
    private ScrollView scrollView;

    private TextView textPrice;
    private TextView textAddress;
    private TextView textAddressCity;
    private ImageView imageLike;

    private TextView textCijena;
    private TextView textKvadratura;
    private TextView textRezije;
    private TextView textBrojSoba;
    private TextView textOpis;

    private TextView textKorisnikIme;
    private TextView textKorisnikEmail;
    private TextView textKorisnikTelefonskiBroj;

    private TextView textPicNumb;
    private ImageView btnBack;

    private UserObject ownerOfUnit;

    private Fragment mapView;
    private Fragment streetView;
    private boolean mapVisible = true;

    private static final String MARKER_POSITION_KEY = "MarkerPosition";

    // George St, Sydney
    private LatLng unitLocationCoordinate = new LatLng(0,0);

    private StreetViewPanorama mStreetViewPanorama;

    private Marker mMarker;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_real_estate_detail);

        btnContact = (RelativeLayout) findViewById(R.id.buttonContact);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textCijena = (TextView) findViewById(R.id.textCijena);
        imageLike = (ImageView) findViewById(R.id.imageLike);

        textPrice = (TextView) findViewById(R.id.textPrice);
        textAddress = (TextView) findViewById(R.id.textAddress);
        textAddressCity = (TextView) findViewById(R.id.textAddressCity);

        textKvadratura = (TextView) findViewById(R.id.textKvadratura);
        textRezije = (TextView) findViewById(R.id.textRezije);
        textOpis = (TextView) findViewById(R.id.textOpis);
        textBrojSoba = (TextView) findViewById(R.id.textBrojSoba);

        textKorisnikIme = (TextView) findViewById(R.id.textKorisnikIme);
        textKorisnikEmail = (TextView) findViewById(R.id.textKorisnikEmail);
        textKorisnikTelefonskiBroj = (TextView) findViewById(R.id.textKorisnikTelefonskiBroj);

        textPicNumb = (TextView) findViewById(R.id.textPicNumb);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        Intent intent = getIntent();
        final RealEstateItem item = (RealEstateItem) intent.getParcelableExtra("item");

        if (item.isLike()) {
            imageLike.setImageResource(R.mipmap.ic_black_red_heart);
        } else {
            imageLike.setImageResource(R.mipmap.ic_black_heart);
        }
        imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isLike()) {
                    item.setLike(false);
                    imageLike.setImageResource(R.mipmap.ic_black_heart);
                } else {
                    item.setLike(true);
                    imageLike.setImageResource(R.mipmap.ic_black_red_heart);
                }
            }
        });

        final ParcelableUnit unit = item.getUnit();

        setText(unit);
        setMap(savedInstanceState, unit.getLocation());
        viewPagerImages = (ViewPager) findViewById(R.id.mvieww);
        ImageAdapter adapter = new ImageAdapter(this, unit.getImages());
        viewPagerImages.setAdapter(adapter);
        viewPagerImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int picNumb = position + 1;
                textPicNumb.setText(picNumb + "/" + unit.getImages().size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ownerOfUnit != null) {
                    dialContactPhone(ownerOfUnit.getContactInfo().getPhoneNumber());
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private void setText(ParcelableUnit unit) {

        new UserByUnitIdTask(unit.getId().toString(), new UserByUnitIdTask.AsynResponse() {
            @Override
            public void processFinish(UserObject output) {
                ownerOfUnit = output;
                textKorisnikIme.setText("Ime i prezime: " + output.getFirstName() + " " + output.getLastName());
                textKorisnikTelefonskiBroj.setText("Telefonski broj: " + output.getContactInfo().getPhoneNumber());
                textKorisnikEmail.setText("Email: " + output.getContactInfo().getEmail());
            }
        }).execute();


        textPrice.setText((int) unit.getPrice() + " kn/mj");
        ParcelableLocation location = unit.getLocation();
        textAddress.setText(location.getStreetName() + " " + location.getHouseNumber());
        textAddressCity.setText(location.getPostalCode() + " " + location.getCity() +
                ", " + location.getCountry());

        textCijena.setText("Cijena: " + (int) unit.getPrice() + " kn/mj");
        textKvadratura.setText(Html.fromHtml("Kvadratura: " + unit.getArea() + " m" + "<sup>2</sup>"));
        textOpis.setText(unit.getDescription());
        textPicNumb.setText("1/" + unit.getImages().size());
        textRezije.setText("Prosječne režije: " + unit.getAvgOverheads() + " kn/mj");
        textBrojSoba.setText("Broj soba: " + unit.getRooms());
    }

    private void setMap(final Bundle savedInstanceState, ParcelableLocation location) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(location.getStreetName() + location.getHouseNumber() + location.getCity() + "", 1);
            if (addresses.size() == 0) {
                addresses = geocoder.getFromLocationName("Brozova ulica 44, Zagreb", 1);
            }
            Log.i("s", addresses.size() + "");
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                unitLocationCoordinate = new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            Log.i("e", e.getMessage());
        }

        streetView = getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        mapView = getFragmentManager().findFragmentById(R.id.streetviewpanorama);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
               // map.setOnMarkerDragListener(RealEstateDetailActivity.this);
                // Creates a draggable marker. Long press to drag.
                CameraUpdate center=
                        CameraUpdateFactory.newLatLng(unitLocationCoordinate);
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                map.moveCamera(center);
                map.animateCamera(zoom);
                map.getUiSettings().setScrollGesturesEnabled(false);
                map.getUiSettings().setZoomGesturesEnabled(false);
                mMarker = map.addMarker(new MarkerOptions()
                        .position(unitLocationCoordinate)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartmanmapicon))
                        .draggable(true));
                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        Intent intent = new Intent(RealEstateDetailActivity.this, MapActivity.class);
                        intent.putExtra("unitLocationCoordinate", unitLocationCoordinate);
                        startActivity(intent);
                    }
                });
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent intent = new Intent(RealEstateDetailActivity.this, MapActivity.class);
                        intent.putExtra("unitLocationCoordinate", unitLocationCoordinate);
                        startActivity(intent);
                    }
                });
            }
        });

    }

    public void setStreetView(View view) {
        Intent intent = new Intent(RealEstateDetailActivity.this, MapActivity.class);
        intent.putExtra("unitLocationCoordinate", unitLocationCoordinate);
        startActivity(intent);
    }
}
