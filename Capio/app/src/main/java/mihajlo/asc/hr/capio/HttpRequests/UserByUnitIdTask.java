package mihajlo.asc.hr.capio.HttpRequests;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import mihajlo.asc.hr.capio.Models.UserObject;
import mihajlo.asc.hr.capio.Util.Settings;

/**
 * Created by Damjan on 5/9/2017.
 */

public class UserByUnitIdTask extends AsyncTask<Void, Void, UserObject> {


    private final String partOfUrl = "/owner";
    private String unitId;

    public interface AsynResponse {
        void processFinish(UserObject output);
    }

    UserByUnitIdTask.AsynResponse asynResponse = null;

    public UserByUnitIdTask(String unitId, UserByUnitIdTask.AsynResponse asynResponse) {
        this.unitId = unitId;
        this.asynResponse = asynResponse;
    }


    @Override
    protected UserObject doInBackground(Void... params) {
        try {
            String url = Settings.host + partOfUrl;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
            uriBuilder.appendQueryParameter("id", unitId);
            url = uriBuilder.build().toString();

            UserObject forNow = restTemplate.getForObject(url, UserObject.class);
            if (forNow == null) {
                return null;
            } else {
                return forNow;
            }
        } catch (Exception e) {
            Log.e("UserByUnitIdTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(UserObject result) {
        asynResponse.processFinish(result);
    }
}
