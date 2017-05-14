package mihajlo.asc.hr.capio.HttpRequests;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.Util.Settings;

/**
 * Created by Damjan on 5/13/2017.
 */

public class AllLikeUnitsByUserIdTask extends AsyncTask<Void, Void, List<Unit>> {


    private final String partOfUrl = "/likedunits";

    private String id;

    public interface AsynResponse {
        void processFinish(List<Unit> output);
    }

    AllLikeUnitsByUserIdTask.AsynResponse asynResponse = null;

    public AllLikeUnitsByUserIdTask(String id, AllLikeUnitsByUserIdTask.AsynResponse asynResponse) {
        this.id = id;
        this.asynResponse = asynResponse;
    }



    @Override
    protected List<Unit> doInBackground(Void... params) {
        try {
            String url = Settings.host + partOfUrl;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
            uriBuilder.appendQueryParameter("id", id);
            url = uriBuilder.build().toString();

            Unit[] forNow = restTemplate.getForObject(url, Unit[].class);
            List<Unit> searchList = new ArrayList<>();
            searchList = Arrays.asList(forNow);

            return searchList;
        } catch (Exception e) {
            Log.e("AllUnitsTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Unit> result) {
        asynResponse.processFinish(result);
    }
}
