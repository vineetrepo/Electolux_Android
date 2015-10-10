package com.electrolux.recipe.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;


/**
 * This class provides API for Networking. It executes it's method in caller thread.
 *
 * @author Vinay Kumar
 */
public class Network {
	
    private String serverUrl = null;
    private Context context = null;
    private boolean initFlag = false;


    public static final String NETWORK_NOT_AVAILABLE = "Network not available";
    public static final String NETWORK_NOT_CONNECTED = "Network not connected";
    public static final String NETWORK_NOT_INITIALIZED = "Network is not initialized";
    public static final String CONNECTION_TIME_OUT = "Connection time out";
    public static final String INVALID_URL = "Url is invalid";
    public static final String SERVICE_NOT_AVAILABLE = "Server not found";

    public boolean init(String serverUrl, Context ctx) {
        if (serverUrl != null && serverUrl.length() > 0 && ctx != null) {
            this.serverUrl = serverUrl;
            this.context = ctx;
            initFlag = true;
        } else {
            initFlag = false;
        }

        return initFlag;
    }

    public void sendRequestToServer(NetworkRequest request) {
        if (!initFlag) {
            request.setError(NETWORK_NOT_INITIALIZED);
            return;
        }

        int networkState = checkNetworkState(context);
        if (networkState < 0) {
            request.setError(NETWORK_NOT_AVAILABLE);
            return;
        }

        if (networkState > 0) {
            request.setError(NETWORK_NOT_CONNECTED);
            return;
        }

        try {
            request.performRequest(serverUrl);
        }
        catch (SocketTimeoutException e) {
            request.setError(CONNECTION_TIME_OUT);
        }
        catch (MalformedURLException e) {
            request.setError(INVALID_URL);
        }
        /*catch (HttpStatusException e) {
            e.printStackTrace();
            request.setError("HttpStatusException");
        }
        catch (UnsupportedMimeTypeException e) {
            e.printStackTrace();
            request.setError("UnsupportedMimeTypeException");
        }*/
        catch (IOException e) {
            e.printStackTrace();
            request.setError(SERVICE_NOT_AVAILABLE);
        }
        catch (NullPointerException e) {
        	//e.printStackTrace();
            request.setError(SERVICE_NOT_AVAILABLE);
		}
        catch (Exception e) {
            e.printStackTrace();
            request.setError(SERVICE_NOT_AVAILABLE);
        }
        
       
    }

    /**
     * This helper method for checking network state on device
     *
     * @return status code
     */
    public int checkNetworkState(Context context) {
        int state = -1;
        ConnectivityManager connectvityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectvityMgr.getActiveNetworkInfo();
        
        if (networkInfo == null) {
        	state = -1;
        } else if (networkInfo.isConnected()) {
            state = 0;
        } else {
            state = 1;
        }

        return state;
    }
}
