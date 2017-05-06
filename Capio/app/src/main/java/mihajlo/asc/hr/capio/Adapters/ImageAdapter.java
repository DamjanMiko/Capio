package mihajlo.asc.hr.capio.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mihajlo.asc.hr.capio.R;

/**
 * Created by Damjan on 5/6/2017.
 */

public class ImageAdapter extends PagerAdapter {
    private Context context;
    private int[] GalImages = new int[] {
            R.drawable.apartman, R.drawable.background, R.drawable.house, R.drawable.white
    };

    public ImageAdapter(Context context) {
        this.context=context;
    }

    @Override
    public int getCount() {
        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.image_horizontal_margin);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(GalImages[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView, 0);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}