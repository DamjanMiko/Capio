package mihajlo.asc.hr.capio.Activities;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mihajlo.asc.hr.capio.Adapters.Contents.ExpandableHeightGridView;
import mihajlo.asc.hr.capio.Adapters.SquareImageAdapter;
import mihajlo.asc.hr.capio.Models.Image;
import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableImage;
import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableLocation;
import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableUnit;
import mihajlo.asc.hr.capio.R;

public class CreateUnitActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private ArrayList<String> imagesEncodedList;
    private String imageEncoded;

    private GridView gridView;
    private SquareImageAdapter adapter;

    private TextView txtStreetName;
    private TextView txtHouseNumber;
    private TextView txtPostalCode;
    private TextView txtCity;

    private TextView txtDescription;
    private TextView txtPrice;
    private TextView txtArea;
    private TextView txtAreaM2;
    private TextView txtAvgOverheads;
    private TextView txtRooms;
    private List<Uri> uris= new ArrayList<Uri>();

    private ParcelableUnit cratedUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_unit);

        gridView = (ExpandableHeightGridView) findViewById(R.id.gridview);
        adapter = new SquareImageAdapter(this);
        gridView.setAdapter(adapter);

        cratedUnit = (ParcelableUnit) getIntent().getExtras().get("cratedUnit");

        txtStreetName = (TextView) findViewById(R.id.txtStreetName);
        txtHouseNumber = (TextView) findViewById(R.id.txtHouseNumber);
        txtPostalCode = (TextView) findViewById(R.id.txtPostalCode);
        txtCity = (TextView) findViewById(R.id.txtCity);

        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtArea = (TextView) findViewById(R.id.txtArea);
        txtAreaM2 = (TextView) findViewById(R.id.txtAreaM2);
        txtAvgOverheads = (TextView) findViewById(R.id.txtAvgOverheads);
        txtRooms = (TextView) findViewById(R.id.txtRooms);

        txtAreaM2.setText(Html.fromHtml("m" + "<sup>2</sup>"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();
                    adapter.addItem(mImageUri);
                    uris.add(mImageUri);

                  //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageEncoded);


                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            adapter.addItem(uri);
                            uris.add(uri);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "Niste odabrali niti jednu sliku",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Došlo je do pogreške", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_unit, menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_createUnit:
                createUnit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean createUnit() {
        String streetName = txtStreetName.getText().toString();
        String houseNumber = txtHouseNumber.getText().toString();
        String postalCode = txtPostalCode.getText().toString();
        String city = txtCity.getText().toString();

        String description = txtDescription.getText().toString();
        String price = txtPrice.getText().toString();
        String area = txtArea.getText().toString();
        String avgOverheads = txtAvgOverheads.getText().toString();
        String rooms = txtRooms.getText().toString();

        if (TextUtils.isEmpty(streetName) || TextUtils.isEmpty(houseNumber) || TextUtils.isEmpty(postalCode) ||
                TextUtils.isEmpty(city) || TextUtils.isEmpty(description) || TextUtils.isEmpty(price)
                || TextUtils.isEmpty(area) || TextUtils.isEmpty(avgOverheads) || TextUtils.isEmpty(rooms)) {
            Toast.makeText(this, "Nisu popunjena sva polja!", Toast.LENGTH_LONG).show();
        } else if (uris.isEmpty()) {
            Toast.makeText(this, "Trebate unijeti barem jednu sliku!", Toast.LENGTH_LONG).show();
        } else {
            List<ParcelableImage> images = new ArrayList<>();
            for (Uri uri : uris) {
                images.add(new ParcelableImage(10l, null, uri.toString()));
            }
            try {
                ParcelableLocation location = new ParcelableLocation(10l, streetName, Integer.parseInt(houseNumber),
                        Integer.parseInt(postalCode), "Hrvatska", city);
                cratedUnit = new ParcelableUnit(10l, description, Float.parseFloat(price),
                        Float.parseFloat(area), true, Integer.parseInt(avgOverheads), Integer.parseInt(rooms),
                        location, images);
                Intent intent = new Intent(CreateUnitActivity.this, UnitListActivity.class);
                intent.putExtra("createdUnit", cratedUnit);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Krivi podaci!", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }
}
