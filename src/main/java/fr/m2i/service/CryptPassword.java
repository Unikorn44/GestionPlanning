package fr.m2i.service;

import java.nio.charset.StandardCharsets;

import org.springframework.util.DigestUtils;

public class CryptPassword {
	
	public String create(String password) {
		byte[] data = password.getBytes(StandardCharsets.UTF_8);
		String result = DigestUtils.md5DigestAsHex(data);
		
		return result;
	}

}
