package mihajlo.asc.hr.capio.HttpRequests;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import mihajlo.asc.hr.capio.Models.ContactInfo;
import mihajlo.asc.hr.capio.Models.ContactInfoList;

/**
 * Created by Damjan on 3/29/2017.
 */

public class ContactInfoTask extends AsyncTask<Void, Void, ContactInfoList> {

    private String host = "http://capio.herokuapp.com";
    private final String partOfUrl = "/contactinfo";

    @Override
    protected ContactInfoList doInBackground(Void... params) {
        try {
            String url = "http://" + host + partOfUrl;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

//                String requestBody = "{\"xtag\":\"idDec\"}";
//            JsonSendObject requestBody = new JsonSendObject();
//            requestBody.setXtag(idDec);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<JsonSendObject> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<ContactInfoList> response = restTemplate.exchange(url, HttpMethod.GET, null, ContactInfoList.class);

            return response.getBody();
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ContactInfoList result) {
        if (result == null) {
            Log.i("zajeb", "null");
            return;
        }
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
