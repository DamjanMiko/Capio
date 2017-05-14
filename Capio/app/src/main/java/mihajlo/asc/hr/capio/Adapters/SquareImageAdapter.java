package mihajlo.asc.hr.capio.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mihajlo.asc.hr.capio.R;

/**
 * Created by Damjan on 5/14/2017.
 */

public class SquareImageAdapter extends BaseAdapter {
    private final ArrayList<Uri> mItems = new ArrayList<>();

    private Context context;
    private final LayoutInflater mInflater;
    private int PICK_IMAGE_REQUEST = 1;

    public SquareImageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void addItem(Uri uri) {
        mItems.add(uri);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size() + 1;
    }

    @Override
    public Uri getItem(int i) {
        if (mItems.size() == i) {
            return null;
        }
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
        }

        picture = (ImageView) v.getTag(R.id.picture);

        Uri uri = getItem(i);
        if (uri == null) {
            picture.setImageResource(R.drawable.cross2);
            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickImages();
                }
            });
        } else {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                picture.setImageBitmap(bitmap);
            } catch (Exception e) {}
        }

        return v;
    }

    public void pickImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((AppCompatActivity) context).startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }
}
