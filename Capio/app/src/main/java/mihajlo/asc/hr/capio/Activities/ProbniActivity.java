package mihajlo.asc.hr.capio.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mihajlo.asc.hr.capio.HttpRequests.AllUnitsTask;
import mihajlo.asc.hr.capio.HttpRequests.AllUsersTask;
import mihajlo.asc.hr.capio.HttpRequests.ContactInfoTask;
import mihajlo.asc.hr.capio.HttpRequests.CreateUnitTask;
import mihajlo.asc.hr.capio.HttpRequests.UnitByIdTask;
import mihajlo.asc.hr.capio.HttpRequests.UserByIdTask;
import mihajlo.asc.hr.capio.Models.ContactInfo;
import mihajlo.asc.hr.capio.Models.Image;
import mihajlo.asc.hr.capio.Models.Location;
import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.Models.UserObject;
import mihajlo.asc.hr.capio.R;

public class ProbniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probni);

        Image image = new Image(null, null,
                "https://www.google.hr/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwiKnfqu1ODTAhXSJ1AKHfTjCqsQjRwIBw&url=https%3A%2F%2Fwww.hrvatskaapartmani.hr%2Fapartmani-zadar.aspx&psig=AFQjCNEzfOIH3szSREVPPi8jWDmMN9Z5Mw&ust=1494345346680535");
        Location location = new Location(null, "Unska ulica", 3, 10000, "Hrvatska", "Zagreb");
        ArrayList<Image> images = new ArrayList<>();
        images.add(image);
        Unit unit = new Unit(null, "Ovo je opis stana.", 5000, 60, false, location, images);

        new CreateUnitTask(new CreateUnitTask.AsynResponse() {
            @Override
            public void processFinish(boolean output) {
                Log.i("Bravo", "Uspjesno spremanje apartmana");
            }
        }).execute(unit);

//        new ContactInfoTask(new ContactInfoTask.AsynResponse() {
//            @Override
//            public void processFinish(List<ContactInfo> output) {
//                // you can go here
//            }
//        }).execute();
//
//        new AllUsersTask(new AllUsersTask.AsynResponse() {
//            @Override
//            public void processFinish(List<UserObject> output) {
//                // you can go here
//            }
//        }).execute();
//
//        new AllUnitsTask(new AllUnitsTask.AsynResponse() {
//            @Override
//            public void processFinish(List<Unit> output) {
//                // you can go here
//            }
//        }).execute();
//
//        new UserByIdTask("1", new UserByIdTask.AsynResponse() {
//            @Override
//            public void processFinish(UserObject output) {
//                // you can go here
//            }
//        }).execute();
//
//        new UnitByIdTask("1", new UnitByIdTask.AsynResponse() {
//            @Override
//            public void processFinish(Unit output) {
//                // you can go here
//            }
//        }).execute();
    }
}
