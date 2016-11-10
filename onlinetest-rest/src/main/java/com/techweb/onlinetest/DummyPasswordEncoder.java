package com.techweb.onlinetest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyPasswordEncoder {

	public static void main(String[] args) throws ClassNotFoundException {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
		System.out.println(encoder.encode("password"));

	}

}
