package mihajlo.asc.hr.capio.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Damjan on 3/30/2017.
 */

public class ImageLoadWithListenerTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;

    public interface AsynResponse {
        void processFinish(Bitmap output);
    }

    ImageLoadWithListenerTask.AsynResponse asynResponse = null;

    public ImageLoadWithListenerTask(String url, ImageLoadWithListenerTask.AsynResponse asynResponse) {
        this.url = url;
        this.asynResponse = asynResponse;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        asynResponse.processFinish(result);
    }

}
