package mihajlo.asc.hr.capio.HttpRequests;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.Models.User;
import mihajlo.asc.hr.capio.Models.UserObject;
import mihajlo.asc.hr.capio.Util.Settings;

/**
 * Created by Damjan on 5/6/2017.
 */

public class CreateUserTask extends AsyncTask<UserObject, Void, Boolean> {

    private final String partOfUrl = "/user";

    public interface AsynResponse {
        void processFinish(boolean output);
    }

    CreateUserTask.AsynResponse asynResponse = null;

    public CreateUserTask(CreateUserTask.AsynResponse asynResponse) {
        this.asynResponse = asynResponse;
    }

    @Override
    protected Boolean doInBackground(UserObject... users) {
        String url = Settings.host + partOfUrl;
        //set your headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

        for (UserObject user : users) {
            try {
                //set your entity to send
                HttpEntity entity = new HttpEntity(user, headers);

                // send it!
                ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.POST, entity
                        , String.class);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        asynResponse.processFinish(result);
    }
}
