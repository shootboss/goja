/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.sdk.kit;

import java.security.MessageDigest;

public class EncryptionKit {
	
	public static final String[] algorithms={"MD5","SHA-1","SHA-256","SHA-384","SHA-512"};
	
	public static String md5Encrypt(String srcStr){
		String result = "";
		try {
			result = encrypt("MD5",srcStr);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return result;
	}
	
	public static String sha1Encrypt(String srcStr){
		String result = "";
		try {
			result = encrypt("SHA-1",srcStr);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return result;
	}
	
	public static String sha256Encrypt(String srcStr){
		String result = "";
		try {
			result = encrypt("SHA-256",srcStr);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return result;
	}
	
	public static String sha384Encrypt(String srcStr){
		String result = "";
		try {
			result = encrypt("SHA-384",srcStr);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return result;
	}
	
	public static String sha512Encrypt(String srcStr){
		String result = "";
		try {
			result = encrypt("SHA-512",srcStr);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return result;
	}
	
	public static String encrypt(String algorithms,String srcStr) throws Exception {
		String result = "";
		MessageDigest md = MessageDigest.getInstance(algorithms);
		byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
		for(byte b:bytes){
			String hex = Integer.toHexString(b&0xFF).toUpperCase();
			result += ((hex.length()==1)?"0":"")+hex;
		}
		return result;
	}
	
}





