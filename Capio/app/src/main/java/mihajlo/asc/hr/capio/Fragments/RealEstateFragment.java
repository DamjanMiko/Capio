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

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RealEstateFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private boolean firstTime;
    private int priceFilter;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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

        android.util.Log.e("CIJENA", String.valueOf(priceFilter));

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if (firstTime) {

//                new AllLikeUnitsByUserIdTask(new AllLikeUnitsByUserIdTask.AsynResponse() {
//
//                }).execute();

                new AllUnitsTask(new AllUnitsTask.AsynResponse() {
                    @Override
                    public void processFinish(List<Unit> output) {
                        RealEstateContent.clearAll();
                        RealEstateContent.addItems(output, priceFilter);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(RealEstateItem item);
    }
}
