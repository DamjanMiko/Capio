/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package mihajlo.asc.hr.capio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import mihajlo.asc.hr.capio.Fragments.SlidingTabsBasicFragment;
import mihajlo.asc.hr.capio.R;
import mihajlo.asc.hr.capio.Slider.activities.SampleActivityBase;
import mihajlo.asc.hr.capio.Slider.logger.Log;
import mihajlo.asc.hr.capio.Slider.logger.LogFragment;
import mihajlo.asc.hr.capio.Slider.logger.LogWrapper;
import mihajlo.asc.hr.capio.Slider.logger.MessageOnlyLogFilter;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment * } which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

//        if(AccessToken.getCurrentAccessToken() == null) {
//            goToLoginActivity();
//        }
//
//        private void goToLoginActivity() {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
//
//        public void logout(View view) {
//            LoginManager.getInstance().logOut();
//            goToLoginActivity();
//        }
    }
}
