package mihajlo.asc.hr.capio.Adapters;

/**
 * Created by Damjan on 5/14/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent;
import mihajlo.asc.hr.capio.Fragments.RealEstateFragment;
import mihajlo.asc.hr.capio.R;
import mihajlo.asc.hr.capio.Util.ImageLoadWithListenerTask;


/**
 * {@link RecyclerView.Adapter} that can display a {@link RealEstateContent.RealEstateItem} and makes a call to the
 * specified {@link RealEstateFragment.OnListFragmentInteractionListener}.
 */
public class UnitListAdapter extends RecyclerView.Adapter<UnitListAdapter.ViewHolder> {

    private final List<RealEstateContent.RealEstateItem> mValues;
    private final RealEstateFragment.OnListFragmentInteractionListener mListener;
    private Context context;

    public UnitListAdapter(Context context, List<RealEstateContent.RealEstateItem> items, RealEstateFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_realestate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(mValues.get(position).backgroundUrl));
            BitmapDrawable ob = new BitmapDrawable(bitmap);

            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.mLayout.setBackgroundDrawable(ob);
            } else {
                holder.mLayout.setBackground(ob);
            }
            holder.mImageLike.setVisibility(View.GONE);
        } catch (Exception e) {

        }


        holder.mPriceView.setText(mValues.get(position).price + " kn/mj");
        holder.mLocationView.setText(mValues.get(position).location);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final RelativeLayout mLayout;
        public final TextView mPriceView;
        public final TextView mLocationView;
        public final ImageView mImageLike;
        public RealEstateContent.RealEstateItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLayout = (RelativeLayout) view.findViewById(R.id.layout);
            mPriceView = (TextView) view.findViewById(R.id.textPrice);
            mLocationView = (TextView) view.findViewById(R.id.textLocation);
            mImageLike = (ImageView) view.findViewById(R.id.imageLike);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLocationView.getText() + "'";
        }
    }
}

