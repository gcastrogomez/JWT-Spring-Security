package com.gcastro.entities;

import java.io.Serializable;

public class JwtUserResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4377715824824734694L;
	private final String jwttoken;
	
	public JwtUserResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	
	public String getJwttoken() {
		return this.jwttoken;
	}
	
	
	
	
}
