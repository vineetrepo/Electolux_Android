package com.electrolux.recipe;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

/**
 * Created by VINEET KUMAR on 10-10-2015.
 */
public class LandingScreenActivity extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_landing_screen);
        initUI();
    }

    private void initUI() {
    }


    /**
     * This method adds the fragment on the stack with animation
     * @param fragment
     * @param addToStack
     */
    public void addFragment(Fragment fragment, boolean addToStack)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left_to_right, R.anim.slide_out_left_to_right, R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        if(addToStack)
            fragmentTransaction.addToBackStack(fragment.getClass().getName());

        fragmentTransaction.commit();
    }

    /**
     * This method is used to move one step back
     */
    public void backbyOneStep()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if( count > 1 )
            super.onBackPressed();
        else
        {
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        backbyOneStep();
    }

}
