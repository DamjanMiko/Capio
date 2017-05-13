package mihajlo.asc.hr.capio.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import mihajlo.asc.hr.capio.R;

public class FilterActivity extends AppCompatActivity {

    //user
    private String userId;
    private String name;
    private String surname;
    private String imageUrl;
    private String birthday;
    private String email;
    private String gender;

    // price filter
    private SeekBar priceSeekBar;
    private TextView priceTextView;
    private int price = 3750;
    private int priceStep = 100;

    // area filter
    private SeekBar areaSeekBar;
    private TextView areaTextView;
    private int area = 150;
    private int areaStep = 10;

    // rooms filter
    private Button lessRoomsButton;
    private TextView roomsValue;
    private Button moreRoomsButton;
    private int rooms = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_filter);

        setUser();
        initializeComponents();

        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = (progress / priceStep) * priceStep;
                price = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                priceTextView.setText(String.valueOf(price) + " kn");
            }
        });

        areaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = (progress / areaStep) * areaStep;
                area = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                areaTextView.setText(String.valueOf(area) + " m2");
            }
        });

        lessRoomsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rooms = Integer.parseInt(roomsValue.getText().toString());
                moreRoomsButton.setEnabled(true);
                if (rooms < 1) {
                    lessRoomsButton.setEnabled(false);
                } else {
                    rooms--;
                    roomsValue.setText(String.valueOf(rooms));
                }
            }
        });

        moreRoomsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfRooms = Integer.parseInt(roomsValue.getText().toString());
                lessRoomsButton.setEnabled(true);
                if (numberOfRooms > 9) {
                    moreRoomsButton.setEnabled(false);
                } else {
                    numberOfRooms++;
                    roomsValue.setText(String.valueOf(numberOfRooms));
                }
            }
        });
    }

    private void setUser() {
        Bundle inBundle = getIntent().getExtras();
        userId = inBundle.get("userId").toString();
        name = inBundle.get("name").toString();
        surname = inBundle.get("surname").toString();
        imageUrl = inBundle.get("imageUrl").toString();
        birthday = inBundle.get("birthday").toString();
        email = inBundle.get("email").toString();
        gender = inBundle.get("gender").toString();
    }

    private void initializeComponents() {
        priceSeekBar = (SeekBar) findViewById(R.id.priceFilter);
        priceTextView = (TextView) findViewById(R.id.priceValue);
        priceTextView.setText(String.valueOf(price));

        areaSeekBar = (SeekBar) findViewById(R.id.areaFilter);
        areaTextView = (TextView) findViewById(R.id.areaValue);
        areaTextView.setText(String.valueOf(area));

        lessRoomsButton = (Button) findViewById(R.id.lessRoomsButton);
        roomsValue = (TextView) findViewById(R.id.roomsValue);
        roomsValue.setText(String.valueOf(rooms));
        moreRoomsButton = (Button) findViewById(R.id.moreRoomsButton);
    };

    public void searchByFilters(View view) {
        Intent filterIntent = new Intent(FilterActivity.this, MainActivity.class);
        filterIntent.putExtra("userId", userId);
        filterIntent.putExtra("name",name);
        filterIntent.putExtra("surname",surname);
        filterIntent.putExtra("imageUrl",imageUrl);
        filterIntent.putExtra("email", email);
        filterIntent.putExtra("birthday", birthday);
        filterIntent.putExtra("gender", gender);
        filterIntent.putExtra("price", price);
        filterIntent.putExtra("area", area);
        filterIntent.putExtra("rooms", rooms);
        filterIntent.putExtra("firstTime", true);
        startActivity(filterIntent);
    }
}
