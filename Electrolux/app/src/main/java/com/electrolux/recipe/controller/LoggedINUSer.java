package com.electrolux.recipe.controller;

import android.content.SharedPreferences;

/**
 * This class provides the user related information
 * Created by VINEET KUMAR on 10-10-2015.
 */
public class LoggedINUSer {

    /**
     * User Id of user which returned by the server
     */
    private final String LOGGEDIN_USERID = "LOGGEDINUSERID";
    /**
     * It is contant filed used as key for preferences
     */
    private final String USER_INITIAL_SETUP = "USERINITIALSETUP";


    /**
     * This method returns the currently logged user id. If user is not logged in then
     * it will return the blank string
     *
     * @return
     */
    public String getLoggedInUserID() {
        return RecipeController.getInstance(null).getPreferences().getString(LOGGEDIN_USERID, "");
    }

    /**
     * this method saves the user id in the application shared preference
     *
     * @param userID
     */
    public void saveUserID(String userID) {
        SharedPreferences.Editor editor = RecipeController.getInstance(null).getPreferences().edit();
        editor.putString(LOGGEDIN_USERID, userID);
        editor.commit();
    }

    /**
     * This method returns true if current user completed his/her initial setup of application
     * otherwise false
     *
     * @return
     */
    public boolean isInitialSetupDone() {
        return RecipeController.getInstance(null).getPreferences().getBoolean(USER_INITIAL_SETUP, false);
    }

    /**
     * this method saves the setup information
     *
     * @param initialSetupDone
     */
    public void setInitialSetupDone(boolean initialSetupDone) {
        SharedPreferences.Editor editor = RecipeController.getInstance(null).getPreferences().edit();
        editor.putBoolean(USER_INITIAL_SETUP, initialSetupDone);
        editor.commit();
    }
}