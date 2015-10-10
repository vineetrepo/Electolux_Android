package com.electrolux.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.electrolux.recipe.controller.RecipeController;

/**
 * @author Vineet kumar
 */
public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        RecipeController.getInstance(getApplicationContext());
        initUI();
    }

    @Override
    protected void initUI() {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(RecipeController.getInstance(null).getLoginUSer().getLoggedInUserID().equals("")) {
                    Intent intent = new Intent(getApplicationContext(), LoginOptionActivity.class);
                    RecipeController.getInstance(getApplicationContext()).startNewActivity(intent, SplashScreenActivity.this, true);
                }
                else if(RecipeController.getInstance(null).getLoginUSer().isInitialSetupDone())
                {
                    Intent intent = new Intent(getApplicationContext(), LandingScreenActivity.class);
                    RecipeController.getInstance(getApplicationContext()).startNewActivity(intent, SplashScreenActivity.this, true);
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), InitialSetupScreenActivity.class);
                    RecipeController.getInstance(getApplicationContext()).startNewActivity(intent, SplashScreenActivity.this, true);
                }
            }
        }.sendEmptyMessageDelayed(0,2000);
    }
}
