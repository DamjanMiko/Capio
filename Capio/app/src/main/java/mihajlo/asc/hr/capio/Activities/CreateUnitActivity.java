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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_unit);

        gridView = (ExpandableHeightGridView) findViewById(R.id.gridview);
        adapter = new SquareImageAdapter(this);
        gridView.setAdapter(adapter);



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
//                    // Get the cursor
//                    Cursor cursor = getContentResolver().query(mImageUri,
//                            filePathColumn, null, null, null);
//                    // Move to first row
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    imageEncoded  = cursor.getString(columnIndex);
//                    cursor.close();

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

//                            // Get the cursor
//                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
//                            // Move to first row
//                            cursor.moveToFirst();
//
//                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                            imageEncoded  = cursor.getString(columnIndex);
//                            imagesEncodedList.add(imageEncoded);
//                            cursor.close();

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
                return createUnit();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean createUnit() {


        return true;
    }
}
