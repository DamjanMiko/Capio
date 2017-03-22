package mihajlo.asc.hr.capio.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mihajlo.asc.hr.capio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View view;
    EditText inputName;
    EditText inputEmail;
    EditText inputPh;
    EditText inputLoc;
    public ProfileFragment() {
        // Required empty public constructor
    }

    private class UserInfo{
        public String username;
        public String phone;
        public String address;
        public String email;

        public UserInfo(){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("MyApp","I am here");
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
         inputName  = (EditText) view.findViewById(R.id.editText);
         inputEmail  = (EditText) view.findViewById(R.id.editText7);
         inputPh  = (EditText) view.findViewById(R.id.editText6);
         inputLoc  = (EditText) view.findViewById(R.id.editText8);
        UserInfo theUser = new UserInfo();
        theUser.username = "Ivica Kicmanovic";
        theUser.phone = "+385 91 012 3456";
        theUser.address = "Unska 3, Zagreb";
        theUser.email = "ivica.kicmanovic@efzg.com";

        //


        inputName.setText(theUser.username,TextView.BufferType.EDITABLE);


        inputEmail.setText(theUser.email);


        inputPh.setText(theUser.phone);


        inputLoc.setText(theUser.address);

        Button saveButton = (Button) view.findViewById(R.id.button3);
        saveButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });


        Button logoutButton = (Button) view.findViewById(R.id.button2);
        logoutButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();



    }
}
