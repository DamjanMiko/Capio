package mihajlo.asc.hr.capio.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableImage;
import mihajlo.asc.hr.capio.R;
import mihajlo.asc.hr.capio.Util.ImageLoadWithListenerTask;

/**
 * Created by Damjan on 5/6/2017.
 */

public class ImageAdapter extends PagerAdapter {
    private Context context;

    private List<Bitmap> allImages = new ArrayList<>();

    public ImageAdapter(Context context) {
        this.context=context;
    }

    public ImageAdapter(Context context, List<ParcelableImage> images) {
        this.context=context;
        for (ParcelableImage img : images) {
            addImageUrl(img.getUrl());
        }
    }

    public void addImageUrl(String url) {
        new ImageLoadWithListenerTask(url,
                new ImageLoadWithListenerTask.AsynResponse() {
                    @Override
                    public void processFinish(Bitmap picture) {
                        allImages.add(picture);
                        notifyDataSetChanged();
                    }
                }).execute();
    }

    @Override
    public int getCount() {
        return allImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.image_horizontal_margin);
        imageView.setPadding(0, 0, padding, 0);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(allImages.get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView, 0);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}