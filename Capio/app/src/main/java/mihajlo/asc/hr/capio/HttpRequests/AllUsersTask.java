package mihajlo.asc.hr.capio.HttpRequests;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mihajlo.asc.hr.capio.Models.ContactInfo;
import mihajlo.asc.hr.capio.Models.UserObject;
import mihajlo.asc.hr.capio.Util.Settings;

/**
 * Created by Damjan on 3/29/2017.
 */

public class AllUsersTask extends AsyncTask<Void, Void, List<UserObject>> {


    private final String partOfUrl = "/users";

    public interface AsynResponse {
        void processFinish(List<UserObject> output);
    }

    AllUsersTask.AsynResponse asynResponse = null;

    public AllUsersTask(AllUsersTask.AsynResponse asynResponse) {
        this.asynResponse = asynResponse;
    }


    @Override
    protected List<UserObject> doInBackground(Void... params) {
        try {
            String url = Settings.host + partOfUrl;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            UserObject[] forNow = restTemplate.getForObject(url, UserObject[].class);
            List<UserObject> searchList = new ArrayList<>();
            searchList = Arrays.asList(forNow);

            return searchList;
        } catch (Exception e) {
            Log.e("AllUsersTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<UserObject> result) {
        asynResponse.processFinish(result);
    }

}