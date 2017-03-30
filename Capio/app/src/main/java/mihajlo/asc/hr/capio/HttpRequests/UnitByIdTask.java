package mihajlo.asc.hr.capio.HttpRequests;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.Util.Settings;

/**
 * Created by Damjan on 3/30/2017.
 */

public class UnitByIdTask extends AsyncTask<Void, Void, Unit> {


    private final String partOfUrl = "/units";
    private String id;

    public UnitByIdTask(String id) {
        this.id = id;
    }

    public interface AsynResponse {
        void processFinish(Unit output);
    }

    UnitByIdTask.AsynResponse asynResponse = null;

    public UnitByIdTask(UnitByIdTask.AsynResponse asynResponse) {
        this.asynResponse = asynResponse;
    }


    @Override
    protected Unit doInBackground(Void... params) {
        try {
            String url = Settings.host + partOfUrl;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
            uriBuilder.appendQueryParameter("id", id);
            url = uriBuilder.build().toString();

            Unit[] forNow = restTemplate.getForObject(url, Unit[].class);
            if (forNow == null) {
                return null;
            } else {
                return forNow[0];
            }
        } catch (Exception e) {
            Log.e("UnitByIdTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Unit result) {
        asynResponse.processFinish(result);
    }
}