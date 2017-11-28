package com.jaspersoft.jasperserver.war.xmla.wrappers;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;

public class ServletOutputStreamWrapper extends ServletOutputStream {

	StringBuffer buffer = new StringBuffer();
	
	public ServletOutputStreamWrapper() {
		
	}

	@Override
	public void write(int c) throws IOException {
		buffer.append((char)c); 
	}

	public String getBytes() {
		return buffer.toString();
	}

	public void updateBytes(String s) {
		buffer = new StringBuffer(s);
	}
}
