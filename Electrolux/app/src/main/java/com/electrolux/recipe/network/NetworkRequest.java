package com.electrolux.recipe.network;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 * 
 * @author Vinay Kumar
 *
 */
@SuppressWarnings("deprecation")
public abstract class NetworkRequest {
	private Error error = null;
	private NetworkRequestListener networkRequestListener = null;
	private String progMsg = null;
	public NetworkRequest(NetworkRequestListener listener,String progressMsg)
	{
		networkRequestListener = listener;
		progMsg = progressMsg;
	}
	
	public void performRequest(String serverUrl) throws Exception
	{
		HttpRequestBase httpRequest = createRequest(serverUrl);
		
		/*HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
*/

		if(httpRequest != null){
			HttpClient client = new DefaultHttpClient();

			HttpResponse response = client.execute(httpRequest);
			if(response != null)
			{
				String responseJson = EntityUtils.toString(response.getEntity(), "UTF-8");
				if(response.getStatusLine().getStatusCode() == 200) 
				{
					if(ServerApi.isPrintConsole)
						System.out.println("Respone = "+responseJson);
					parseResponse(responseJson);
				}
				else
				{
					if(ServerApi.isPrintConsole)
						System.out.println("Respone Error= "+response.getStatusLine().getStatusCode());
					parseResponse("");
					error = new Error("Fail to get data");
				}
			}
			else
			{
				System.out.println("Respone Error= null response from server");
			}
		}
	}


	public abstract HttpRequestBase createRequest(String serverUrl);
	public abstract void parseResponse(String responseJson);
	public abstract BaseResponse getResponse();
	

	public void setError(String errorString)
	{
		error = new Error(errorString);
	}

	public Error getError(){ return error;}
	public String getProgressMsg(){ return progMsg;}
	public NetworkRequestListener getNetworkRequestListener(){return networkRequestListener;}

}
