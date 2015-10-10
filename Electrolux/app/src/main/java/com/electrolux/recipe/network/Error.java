package com.electrolux.recipe.network;

public class Error {

	private String errorName = "NO Error";
	
	public Error(String name)
	{
		errorName = name;
	}
	
	@Override
	public String toString()
	{
		return errorName;
	}
}
