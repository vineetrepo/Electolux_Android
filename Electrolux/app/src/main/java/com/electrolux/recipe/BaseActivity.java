package com.electrolux.recipe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by VINAY KUMAR on 10-10-2015.
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected abstract void initUI();
}
