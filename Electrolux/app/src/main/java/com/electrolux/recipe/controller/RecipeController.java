package com.electrolux.recipe.controller;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.electrolux.recipe.R;
import com.electrolux.recipe.network.Network;
import com.electrolux.recipe.network.NetworkRequest;
import com.electrolux.recipe.network.ServerApi;

/**
 * @author Vineet kumar
 */
public class RecipeController {

    private volatile static RecipeController recipeControllerInstance = null;
    private Context mContext = null;
    private Network mNetwork = null;

    private ProgressDialog progressDialog = null;

    /**
     * instance variable of user class
     */
    private LoggedINUSer loggedINUSer = null;

    /**
     * This method returns always the single instance of the RecipeController
     * @param ctx
     * @return returns always the single instance of the RecipeController
     */

    public static RecipeController getInstance(Context ctx) {
        if (recipeControllerInstance == null) {
            synchronized (RecipeController.class) {
                if (recipeControllerInstance == null)
                    recipeControllerInstance = new RecipeController(ctx);
            }
        }
        return recipeControllerInstance;
    }

    private RecipeController(Context context) {
        mContext = context;
        mNetwork = new Network();
        mNetwork.init(ServerApi.BASE_URL, mContext);
        loggedINUSer = new LoggedINUSer();
    }


    /**
     * This method sends request to server
     *
     * @param networkRequest
     */
    public void sendRequestToServer(Context context, NetworkRequest networkRequest) {
        new NetworkTask(networkRequest.getProgressMsg(), context).execute(networkRequest);
    }


    private class NetworkTask extends AsyncTask<NetworkRequest, Integer, NetworkRequest> {
        private String msg = "Please wait...";


        public NetworkTask(String promsg, Context context) {

            msg = promsg;
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(msg);
            if (msg.length() > 0)
                progressDialog.show();
        }


        @Override
        protected NetworkRequest doInBackground(NetworkRequest... params) {
            try {
                mNetwork.sendRequestToServer(params[0]);

            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
            return params[0];
        }


        @Override
        protected void onPostExecute(NetworkRequest result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing())
            progressDialog.dismiss();
            try {
                if(result.getNetworkRequestListener() != null) {
                    if (result.getError() != null)
                        result.getNetworkRequestListener().requestFailedWithError(result.getError());
                    else
                        result.getNetworkRequestListener().requestSuccess(result.getResponse());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }


    public SharedPreferences getPreferences()
    {
        return  mContext.getSharedPreferences("com.electrolux.recipe", Context.MODE_PRIVATE);
    }


    /**
     * This method start a new activity with sliding Animation.
     * @param intent
     * @param callingActivity
     * @param needToFinishCallingActivity
     */
    public void startNewActivity(Intent intent, Activity callingActivity,boolean needToFinishCallingActivity)
    {
        callingActivity.startActivity(intent);
        callingActivity.overridePendingTransition(R.anim.slide_in_left_to_right, R.anim.slide_out_left_to_right);
        if(needToFinishCallingActivity)
            callingActivity.finish();
    }

    /**
     * This method returns the login user object
     * @return
     */
    public LoggedINUSer getLoginUSer()
    {
        return loggedINUSer;
    }


}
