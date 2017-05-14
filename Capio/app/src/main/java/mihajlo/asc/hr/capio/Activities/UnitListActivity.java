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
import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.Models.UserObject;
import mihajlo.asc.hr.capio.R;

public class UnitListActivity extends AppCompatActivity {

    private int mColumnCount = 1;
    private boolean firstTime = true;
    private final int CONSTANT = 100000;
    private String userId;
    private TextView textInfo;

    private RealEstateFragment.OnListFragmentInteractionListener mListener = new RealEstateFragment.OnListFragmentInteractionListener() {
        @Override
        public void onListFragmentInteraction(RealEstateContent.RealEstateItem item) {
            Intent intent = new Intent(UnitListActivity.this, RealEstateDetailActivity.class);
            intent.putExtra("item", item);
            intent.putExtra("notLike", true);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = (String) getIntent().getExtras().get("userId");
        textInfo = (TextView) findViewById(R.id.textInfo);

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
                            RealEstateContent.addItems(output.getOwnedUnits(), CONSTANT, CONSTANT, CONSTANT, CONSTANT, null);
                            recyclerView.setAdapter(new UnitListAdapter(RealEstateContent.ITEMS, mListener));
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
            recyclerView.setAdapter(new UnitListAdapter(RealEstateContent.ITEMS, mListener));
        }

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RealEstateContent.RealEstateItem item);
    }

    public void addUnit(View view) {
        Intent intent = new Intent(UnitListActivity.this, CreateUnitActivity.class);
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

}
