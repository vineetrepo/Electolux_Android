package com.electrolux.recipe.network;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class JsonParser {

	public abstract BaseResponse parseJson(String responseJson) throws JSONException;
	protected void parseBaseResponse(BaseResponse res, JSONObject json) throws JSONException
	{
		if(json.has("status"))
			res.setStatus(json.getInt("status"));
		if(json.has("message"))
			res.setMessage(json.getString("message"));
	}

}
