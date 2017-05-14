package mihajlo.asc.hr.capio.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import mihajlo.asc.hr.capio.Activities.FilterActivity;
import mihajlo.asc.hr.capio.Activities.LoginActivity;
import mihajlo.asc.hr.capio.Activities.MainActivity;
import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent;
import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent.RealEstateItem;
import mihajlo.asc.hr.capio.Adapters.MyRealEstateRecyclerViewAdapter;
import mihajlo.asc.hr.capio.HttpRequests.AllLikeUnitsByUserIdTask;
import mihajlo.asc.hr.capio.HttpRequests.AllUnitsTask;
import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.R;
import mihajlo.asc.hr.capio.Slider.logger.Log;

public class RealEstateFragment extends Fragment {

    private int mColumnCount = 1;
    private boolean firstTime;
    private int priceFilter;
    private int areaFilter;
    private int overheadsFilter;
    private int roomsFilter;

    private OnListFragmentInteractionListener mListener;

    public RealEstateFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_realestate_list, container, false);

        firstTime = getArguments().getBoolean("firstTime");
        priceFilter = getArguments().getInt("price");
        areaFilter = getArguments().getInt("area");
        overheadsFilter = getArguments().getInt("overheads");
        roomsFilter = getArguments().getInt("rooms");

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if (firstTime) {
                new AllUnitsTask(new AllUnitsTask.AsynResponse() {
                    @Override
                    public void processFinish(List<Unit> output) {
                        RealEstateContent.clearAll();
                        RealEstateContent.addItems(output, priceFilter, areaFilter, overheadsFilter, roomsFilter, null);
                        recyclerView.setAdapter(new MyRealEstateRecyclerViewAdapter(RealEstateContent.ITEMS, mListener));
                        firstTime = false;
                    }
                }).execute();
            } else {
                recyclerView.setAdapter(new MyRealEstateRecyclerViewAdapter(RealEstateContent.ITEMS, mListener));
            }

        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(RealEstateItem item);
    }
}