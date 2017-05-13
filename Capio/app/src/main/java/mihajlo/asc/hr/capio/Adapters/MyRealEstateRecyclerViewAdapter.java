package mihajlo.asc.hr.capio.Adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent.RealEstateItem;
import mihajlo.asc.hr.capio.Fragments.RealEstateFragment.OnListFragmentInteractionListener;
import mihajlo.asc.hr.capio.R;
import mihajlo.asc.hr.capio.Util.ImageLoadWithListenerTask;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link RealEstateItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyRealEstateRecyclerViewAdapter extends RecyclerView.Adapter<MyRealEstateRecyclerViewAdapter.ViewHolder> {

    private final List<RealEstateItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyRealEstateRecyclerViewAdapter(List<RealEstateItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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

        new ImageLoadWithListenerTask(mValues.get(position).backgroundUrl,
                new ImageLoadWithListenerTask.AsynResponse() {
            @Override
            public void processFinish(Bitmap picture) {
                BitmapDrawable ob = new BitmapDrawable(picture);

                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.mLayout.setBackgroundDrawable(ob);
                } else {
                    holder.mLayout.setBackground(ob);
                }
                if (mValues.get(position).isLike()) {
                    holder.mImageLike.setImageResource(R.mipmap.ic_red_heart);
                } else {
                    holder.mImageLike.setImageResource(R.mipmap.ic_white_heart);
                }

            }
        }).execute();


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

        holder.mImageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mValues.get(position).isLike()) {
                    mValues.get(position).setLike(false);
                    holder.mImageLike.setImageResource(R.mipmap.ic_white_heart);
                } else {
                    mValues.get(position).setLike(true);
                    holder.mImageLike.setImageResource(R.mipmap.ic_red_heart);
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
        public RealEstateItem mItem;

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
