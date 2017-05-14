package mihajlo.asc.hr.capio.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import mihajlo.asc.hr.capio.Models.User;
import mihajlo.asc.hr.capio.R;
import mihajlo.asc.hr.capio.Util.ImageLoadTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View view;
    private EditText inputName;
    private EditText inputEmail;
    private EditText inputMobile;
    private EditText inputAddress;
    private ImageView imgView;
    private EditText inputCity;
    private User korisnik;
    private TextView save;
//    private ScrollView mainScrollView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String KorisnikString = getArguments().getString("Korisnik");

        korisnik = User.fromString(KorisnikString);
        view =  inflater.inflate(R.layout.profile, container, false);
        inputName = (EditText) view.findViewById(R.id.fullName);
        inputEmail = (EditText) view.findViewById(R.id.email);
        inputMobile  = (EditText) view.findViewById(R.id.mobile);
        inputAddress  = (EditText) view.findViewById(R.id.address);
        inputCity = (EditText) view.findViewById(R.id.city);
        imgView = (ImageView) view.findViewById(R.id.profile_img);
        save = (TextView) view.findViewById(R.id.save);
//        mainScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        //setArguments(new Bundle());

        //

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Korisnik spremljen",
                        Toast.LENGTH_SHORT).show();
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(save.getWindowToken(), 0);
            }
        });

        new ImageLoadTask(korisnik.imgUrl, imgView).execute();

        inputName.setText(korisnik.firstName+" "+korisnik.lastName);

        if(!korisnik.email.equals("|")){
            inputEmail.setText(korisnik.email);
        }else{
            inputEmail.setText("");
        }


        if(!korisnik.phone.equals("|")){
            inputMobile.setText(korisnik.phone);
        }else{
            inputMobile.setText("");
        }


        if(!korisnik.address.equals("|")){
            inputAddress.setText(korisnik.address);
        }else{
            inputAddress.setText("");
        }

        if(!korisnik.city.equals("|")){
            inputCity.setText(korisnik.city);
        }else{
            inputCity.setText("");
        }


//        Button saveButton = (Button) view.findViewById(R.id.button3);
//        saveButton.setOnClickListener( new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                String[] usernameSplit = inputName.getText().toString().split(" ");
//                korisnik.firstName = usernameSplit[0];
//                korisnik.lastName = usernameSplit.length>1?usernameSplit[1]:"";
//                korisnik.email = inputEmail.getText().toString();
//                korisnik.phone = inputPh.getText().toString();
//                korisnik.address = inputLoc.getText().toString();

                //HttpClient httpclient = HttpClientBuilder.create().build();
                //HttpPost httppost = new HttpGet("http://capio.herokuapp.com/users");

                //TODO SAVE KORISNIK INTO DATABASE
//                HttpClient httpclient = HttpClientBuilder.create().build();
//                HttpPost httppost = new HttpPost("http://capio.herokuapp.com/users");
//
//                // Request parameters and other properties.
//                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//                params.add(new BasicNameValuePair("firstName", "Ivan"));
//                params.add(new BasicNameValuePair("lastName", "Vlasic"));
//                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//
//                //Execute and get the response.
//                HttpResponse response = httpclient.execute(httppost);



                //System.out.println(response.getStatusLine().getStatusCode());

//                mainScrollView.fullScroll(ScrollView.FOCUS_UP);

//            }
//        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
