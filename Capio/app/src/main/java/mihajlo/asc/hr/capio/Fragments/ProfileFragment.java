package mihajlo.asc.hr.capio.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mihajlo.asc.hr.capio.Models.User;
import mihajlo.asc.hr.capio.R;
import mihajlo.asc.hr.capio.Util.ImageLoadTask;

public class ProfileFragment extends Fragment {

    private View view;
    private EditText inputName;
    private EditText inputEmail;
    private EditText inputMobile;
    private EditText inputAddress;
    private ImageView imgView;
    private EditText inputCity;
    private User user;
    private TextView save;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String KorisnikString = getArguments().getString("Korisnik");
        user = User.fromString(KorisnikString);
        view =  inflater.inflate(R.layout.profile, container, false);

        initializeData();

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Korisnik spremljen",
                        Toast.LENGTH_SHORT).show();
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(save.getWindowToken(), 0);
            }
        });

        new ImageLoadTask(user.imgUrl, imgView).execute();

        inputName.setText(user.firstName+" "+ user.lastName);

        if(!user.email.equals("|")){
            inputEmail.setText(user.email);
        }else{
            inputEmail.setText("");
        }


        if(!user.phone.equals("|")){
            inputMobile.setText(user.phone);
        }else{
            inputMobile.setText("");
        }


        if(!user.address.equals("|")){
            inputAddress.setText(user.address);
        }else{
            inputAddress.setText("");
        }

        if(!user.city.equals("|")){
            inputCity.setText(user.city);
        }else{
            inputCity.setText("");
        }

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void initializeData() {
        inputName = (EditText) view.findViewById(R.id.fullName);
        inputEmail = (EditText) view.findViewById(R.id.email);
        inputMobile  = (EditText) view.findViewById(R.id.mobile);
        inputAddress  = (EditText) view.findViewById(R.id.address);
        inputCity = (EditText) view.findViewById(R.id.city);
        imgView = (ImageView) view.findViewById(R.id.profile_img);
        save = (TextView) view.findViewById(R.id.save);
    };
}