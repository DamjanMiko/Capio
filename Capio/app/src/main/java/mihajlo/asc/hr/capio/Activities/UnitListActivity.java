package mihajlo.asc.hr.capio.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent;
import mihajlo.asc.hr.capio.Adapters.MyRealEstateRecyclerViewAdapter;
import mihajlo.asc.hr.capio.Adapters.UnitListAdapter;
import mihajlo.asc.hr.capio.Fragments.RealEstateFragment;
import mihajlo.asc.hr.capio.HttpRequests.AllUnitsTask;
import mihajlo.asc.hr.capio.HttpRequests.UserByIdTask;
import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableUnit;
import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.Models.UserObject;
import mihajlo.asc.hr.capio.R;

public class UnitListActivity extends AppCompatActivity {

    private int mColumnCount = 1;
    private boolean firstTime = true;
    private final int CONSTANT = 100000;
    private String userId;
    private TextView textInfo;
    private ParcelableUnit createdUnit;

    private String name;
    private String surname;
    private String imageUrl;
    private String birthday;
    private String email;
    private String gender;

    private RealEstateFragment.OnListFragmentInteractionListener mListener = new RealEstateFragment.OnListFragmentInteractionListener() {
        @Override
        public void onListFragmentInteraction(RealEstateContent.RealEstateItem item) {
            Intent intent = new Intent(UnitListActivity.this, RealEstateDetailActivity.class);
            intent.putExtra("createdItem", item);
            intent.putExtra("userId", userId);
            intent.putExtra("name",name);
            intent.putExtra("surname",surname);
            intent.putExtra("imageUrl",imageUrl);
            intent.putExtra("email", email);
            intent.putExtra("birthday", birthday);
            intent.putExtra("gender", gender);
            intent.putExtra("notLike", true);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle inBundle = getIntent().getExtras();
        userId = inBundle.get("userId").toString();
        name = inBundle.get("name").toString();
        surname = inBundle.get("surname").toString();
        imageUrl = inBundle.get("imageUrl").toString();
        birthday = inBundle.get("birthday").toString();
        email = inBundle.get("email").toString();
        gender = inBundle.get("gender").toString();

        textInfo = (TextView) findViewById(R.id.textInfo);
        createdUnit = (ParcelableUnit) getIntent().getExtras().get("createdUnit");

     //   firstTime = getArguments().getBoolean("firstTime");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, mColumnCount));
        }
        if (firstTime) {

            if (userId != null && !userId.isEmpty()) {
                new UserByIdTask(userId, new UserByIdTask.AsynResponse() {

                    @Override
                    public void processFinish(UserObject output) {

                        if (output != null && output.getOwnedUnits() != null && !output.getOwnedUnits().isEmpty()) {
                            RealEstateContent.clearAll();
                            if (createdUnit != null) {
                                output.getOwnedUnits().add(new Unit(createdUnit));
                            }
                            RealEstateContent.addItems(output.getOwnedUnits(), CONSTANT, CONSTANT, CONSTANT, CONSTANT, null);
                            recyclerView.setAdapter(new UnitListAdapter(UnitListActivity.this, RealEstateContent.ITEMS, mListener));
                            firstTime = false;
                            textInfo.setVisibility(View.GONE);
                        } else if (createdUnit != null) {
                            RealEstateContent.clearAll();
                            List<Unit> units = new ArrayList<Unit>();
                            Unit unit = new Unit(createdUnit);
                            units.add(unit);
                            RealEstateContent.addItems(units, CONSTANT, CONSTANT, CONSTANT, CONSTANT, null);
                            recyclerView.setAdapter(new UnitListAdapter(UnitListActivity.this, RealEstateContent.ITEMS, mListener));
                            firstTime = false;
                            textInfo.setVisibility(View.GONE);
                        } else {
                            textInfo.setVisibility(View.VISIBLE);
                        }
                    }
                }).execute();
            } else {
                textInfo.setVisibility(View.VISIBLE);
            }

        } else {
            recyclerView.setAdapter(new UnitListAdapter(this, RealEstateContent.ITEMS, mListener));
        }

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RealEstateContent.RealEstateItem item);
    }

    public void addUnit(View view) {
        Intent intent = new Intent(UnitListActivity.this, CreateUnitActivity.class);
        intent.putExtra("createdUnit", createdUnit);
        intent.putExtra("userId", userId);
        intent.putExtra("name",name);
        intent.putExtra("surname",surname);
        intent.putExtra("imageUrl",imageUrl);
        intent.putExtra("email", email);
        intent.putExtra("birthday", birthday);
        intent.putExtra("gender", gender);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("createdUnit", createdUnit);
        intent.putExtra("userId", userId);
        intent.putExtra("name",name);
        intent.putExtra("surname",surname);
        intent.putExtra("imageUrl",imageUrl);
        intent.putExtra("email", email);
        intent.putExtra("birthday", birthday);
        intent.putExtra("gender", gender);
        startActivity(intent);
    }
}
