package mihajlo.asc.hr.capio.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent.RealEstateItem;
import mihajlo.asc.hr.capio.Adapters.ImageAdapter;
import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableUnit;
import mihajlo.asc.hr.capio.R;

public class RealEstateDetailActivity extends AppCompatActivity {

    private ViewPager viewPagerImages;
    private RelativeLayout btnContact;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_real_estate_detail);

        btnContact = (RelativeLayout) findViewById(R.id.buttonContact);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        Intent intent = getIntent();
        RealEstateItem item = (RealEstateItem) intent.getParcelableExtra("item");

        ParcelableUnit unit = item.getUnit();
        unit.getImages();

        viewPagerImages = (ViewPager) findViewById(R.id.mvieww);
        viewPagerImages.setAdapter(new ImageAdapter(this));

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }
}
