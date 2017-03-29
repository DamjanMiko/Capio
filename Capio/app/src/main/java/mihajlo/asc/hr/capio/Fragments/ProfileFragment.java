package mihajlo.asc.hr.capio.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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
    private EditText inputPh;
    private EditText inputLoc;
    private ImageView imgView;
    private User korisnik;
    private ScrollView mainScrollView;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String KorisnikString = getArguments().getString("Korisnik");

        korisnik = User.fromString(KorisnikString);
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        inputName  = (EditText) view.findViewById(R.id.editText);
        inputEmail  = (EditText) view.findViewById(R.id.editText7);
        inputPh  = (EditText) view.findViewById(R.id.editText6);
        inputLoc  = (EditText) view.findViewById(R.id.editText8);
        imgView = (ImageView) view.findViewById(R.id.imageView);
        mainScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        //setArguments(new Bundle());

        //

        new ImageLoadTask(korisnik.imgUrl, imgView).execute();

        inputName.setText(korisnik.firstName+" "+korisnik.lastName,TextView.BufferType.EDITABLE);

        if(!korisnik.email.equals("|")){
            inputEmail.setText(korisnik.email);
        }else{
            inputEmail.setText("");
        }


        if(!korisnik.phone.equals("|")){
            inputPh.setText(korisnik.phone);
        }else{
            inputPh.setText("");
        }


        if(!korisnik.address.equals("|")){
            inputLoc.setText(korisnik.address);
        }else{
            inputLoc.setText("");
        }


        Button saveButton = (Button) view.findViewById(R.id.button3);
        saveButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] usernameSplit = inputName.getText().toString().split(" ");
                korisnik.firstName = usernameSplit[0];
                korisnik.lastName = usernameSplit.length>1?usernameSplit[1]:"";
                korisnik.email = inputEmail.getText().toString();
                korisnik.phone = inputPh.getText().toString();
                korisnik.address = inputLoc.getText().toString();

                //TODO SAVE KORISNIK INTO DATABASE

                mainScrollView.fullScroll(ScrollView.FOCUS_UP);

            }
        });


        Button logoutButton = (Button) view.findViewById(R.id.button2);



        return view;
    }

    @Override
    public void onResume(){
        super.onResume();



    }
}
