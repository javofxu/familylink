package com.example.xmpic.support.config;

public interface JsonListener {
	String getSendMsg();

	boolean onParse(String json);
}
