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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.ArrayList;

import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent;
import mihajlo.asc.hr.capio.Adapters.Contents.RealEstateContent.RealEstateItem;
import mihajlo.asc.hr.capio.Fragments.MapFragment;
import mihajlo.asc.hr.capio.Fragments.ProfileFragment;
import mihajlo.asc.hr.capio.Fragments.RealEstateFragment;
import mihajlo.asc.hr.capio.R;
import mihajlo.asc.hr.capio.Slider.activities.SampleActivityBase;
import mihajlo.asc.hr.capio.Models.User;
import mihajlo.asc.hr.capio.Slider.view.SlidingTabLayout;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment * } which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase implements RealEstateFragment.OnListFragmentInteractionListener {

    public static final String TAG = "MainActivity";

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;
    public User korisnik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);

        Bundle inBundle = getIntent().getExtras();
        String userId = inBundle.get("userId").toString();
        String name = inBundle.get("name").toString();
        String surname = inBundle.get("surname").toString();
        String imageUrl = inBundle.get("imageUrl").toString();
        String birthday = inBundle.get("birthday").toString();
        String email = inBundle.get("email").toString();
        String gender = inBundle.get("gender").toString();

        korisnik = new User(userId, name, surname, imageUrl, birthday, "|", "|", email, gender);

        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.custom_tab, 0);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);


        // TODO napraviti User klasu da bude bolji kod


    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        Intent login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }


    @Override
    public void onListFragmentInteraction(RealEstateItem item) {
        //TODO implementirat
    }

    /**
     * The {@link PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    private class SamplePagerAdapter extends FragmentPagerAdapter {

        private int[] imageResId = {
                R.drawable.ic_home,
                R.drawable.ic_map,
                R.drawable.ic_profile
        };

        private ArrayList<Fragment> fragments = new ArrayList<Fragment>();



        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
            Bundle bundleUser = new Bundle();
            //korisnik = new User("1","firstName","lastName","http://kingofwallpapers.com/picture/picture-008.jpg","birthday","0911234567","Kneza Mihajla 3","email","gender");

            bundleUser.putString("Korisnik",korisnik.toString());
            ProfileFragment tmpProfileFragment = new ProfileFragment();
            tmpProfileFragment.setArguments(bundleUser);
            fragments.add(new RealEstateFragment()); // TODO umjesto ovoga treba dodati new RealEstateFragment()
            fragments.add(new MapFragment());
            fragments.add(tmpProfileFragment); //
        }

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 3;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = ResourcesCompat.getDrawable(getResources(), imageResId[position], null);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(" ");
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

    }

}
