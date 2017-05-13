package mihajlo.asc.hr.capio.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import mihajlo.asc.hr.capio.R;

public class FilterActivity extends AppCompatActivity {

    //korisnik
    private String userId;
    private String name;
    private String surname;
    private String imageUrl;
    private String birthday;
    private String email;
    private String gender;

    private SeekBar priceSeekBar;
    private TextView textView;
    private int price = 0;
    private int priceStep = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_filter);

        Bundle inBundle = getIntent().getExtras();
        userId = inBundle.get("userId").toString();
        name = inBundle.get("name").toString();
        surname = inBundle.get("surname").toString();
        imageUrl = inBundle.get("imageUrl").toString();
        birthday = inBundle.get("birthday").toString();
        email = inBundle.get("email").toString();
        gender = inBundle.get("gender").toString();


        priceSeekBar = (SeekBar) findViewById(R.id.priceFilter);
        textView = (TextView) findViewById(R.id.priceValue);
        textView.setText("0");

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
                textView.setText(String.valueOf(price) + " kn");
            }
        });
    }

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
        filterIntent.putExtra("firstTime", true);
        startActivity(filterIntent);
    }
}
