package mihajlo.asc.hr.capio.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import mihajlo.asc.hr.capio.HttpRequests.AllUnitsTask;
import mihajlo.asc.hr.capio.HttpRequests.AllUsersTask;
import mihajlo.asc.hr.capio.HttpRequests.ContactInfoTask;
import mihajlo.asc.hr.capio.HttpRequests.UnitByIdTask;
import mihajlo.asc.hr.capio.HttpRequests.UserByIdTask;
import mihajlo.asc.hr.capio.Models.ContactInfo;
import mihajlo.asc.hr.capio.Models.Unit;
import mihajlo.asc.hr.capio.Models.UserObject;
import mihajlo.asc.hr.capio.R;

public class ProbniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probni);


        new ContactInfoTask(new ContactInfoTask.AsynResponse() {
            @Override
            public void processFinish(List<ContactInfo> output) {
                // you can go here
            }
        }).execute();

        new AllUsersTask(new AllUsersTask.AsynResponse() {
            @Override
            public void processFinish(List<UserObject> output) {
                // you can go here
            }
        }).execute();

        new AllUnitsTask(new AllUnitsTask.AsynResponse() {
            @Override
            public void processFinish(List<Unit> output) {
                int i = 10;
                // you can go here
            }
        }).execute();

        new UserByIdTask(new UserByIdTask.AsynResponse() {
            @Override
            public void processFinish(UserObject output) {
                int i = 10;
                // you can go here
            }
        }).execute();

        new UnitByIdTask(new UnitByIdTask.AsynResponse() {
            @Override
            public void processFinish(Unit output) {
                int i = 10;
                // you can go here
            }
        }).execute();
    }
}
