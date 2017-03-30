package mihajlo.asc.hr.capio.HttpRequests;

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
 * Created by Damjan on 3/29/2017.
 */

public class AllUnitsTask extends AsyncTask<Void, Void, List<Unit>> {


    private final String partOfUrl = "/units";

    public interface AsynResponse {
        void processFinish(List<Unit> output);
    }

    AllUnitsTask.AsynResponse asynResponse = null;

    public AllUnitsTask(AllUnitsTask.AsynResponse asynResponse) {
        this.asynResponse = asynResponse;
    }


    @Override
    protected List<Unit> doInBackground(Void... params) {
        try {
            String url = Settings.host + partOfUrl;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
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
