package mihajlo.asc.hr.capio.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent.RealEstateItem;
import mihajlo.asc.hr.capio.R;

public class RealEstateDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_real_estate_detail);

        Intent intent = getIntent();
        RealEstateItem item = (RealEstateItem) intent.getParcelableExtra("item");
        int ii = 10;
    }
}
