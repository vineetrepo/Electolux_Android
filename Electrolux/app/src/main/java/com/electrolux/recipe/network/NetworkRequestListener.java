/**
 * 
 */
package com.electrolux.recipe.network;

/**
 * @author Vinay Kumar
 *
 */
public interface NetworkRequestListener {

	 public void requestFailedWithError(Error error);
	 public void requestSuccess(BaseResponse baseResponse);
}
