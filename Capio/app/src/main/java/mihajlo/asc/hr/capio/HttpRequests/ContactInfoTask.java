package mihajlo.asc.hr.capio.HttpRequests;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mihajlo.asc.hr.capio.Models.ContactInfo;
import mihajlo.asc.hr.capio.Util.Settings;

/**
 * Created by Damjan on 3/29/2017.
 */

public class ContactInfoTask extends AsyncTask<Void, Void, List<ContactInfo>> {


    private final String partOfUrl = "/contactinfo";

    public interface AsynResponse {
        void processFinish(List<ContactInfo> output);
    }

    AsynResponse asynResponse = null;

    public ContactInfoTask(AsynResponse asynResponse) {
        this.asynResponse = asynResponse;
    }


    @Override
    protected List<ContactInfo> doInBackground(Void... params) {
        try {
            String url = Settings.host + partOfUrl;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

//                String requestBody = "{\"xtag\":\"idDec\"}";
//            JsonSendObject requestBody = new JsonSendObject();
//            requestBody.setXtag(idDec);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<JsonSendObject> entity = new HttpEntity<>(requestBody, headers);

            ContactInfo[] forNow = restTemplate.getForObject(url, ContactInfo[].class);
            List<ContactInfo> searchList = new ArrayList<>();
            searchList = Arrays.asList(forNow);

//            ResponseEntity<ContactInfoList> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ContactInfo>>() {
//            });

            return searchList;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<ContactInfo> result) {
        if (result == null) {
            Log.i("zajeb", "null");
            return;
        }
        asynResponse.processFinish(result);
//
//        if (result.isSuccess()) {
//            txtSuccess.setTextColor(Color.BLUE);
//            txtSuccess.setText("Uspješno ste ušli u sustav!");
//        }  else {
//            txtSuccess.setTextColor(Color.RED);
//            txtSuccess.setText("Ne možete ući u sustav!");
//        }

    }

}
