package mihajlo.asc.hr.capio.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

    private TextView textCijena;
    private TextView textKvadratura;
    private TextView textRezije;
    private TextView textBrojSoba;
    private TextView textOpis;

    private TextView textKorisnikIme;
    private TextView textKorisnikEmail;
    private TextView textKorisnikTelefonskiBroj;


    private UserObject ownerOfUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_real_estate_detail);

        btnContact = (RelativeLayout) findViewById(R.id.buttonContact);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textCijena = (TextView) findViewById(R.id.textCijena);

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

        Intent intent = getIntent();
        RealEstateItem item = (RealEstateItem) intent.getParcelableExtra("item");

        ParcelableUnit unit = item.getUnit();

        setText(unit);
        viewPagerImages = (ViewPager) findViewById(R.id.mvieww);
        viewPagerImages.setAdapter(new ImageAdapter(this, unit.getImages()));

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                scrollView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                    }
//                });

                if (ownerOfUnit != null) {
                    dialContactPhone(ownerOfUnit.getContactInfo().getPhoneNumber());
                }

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
    }
}
