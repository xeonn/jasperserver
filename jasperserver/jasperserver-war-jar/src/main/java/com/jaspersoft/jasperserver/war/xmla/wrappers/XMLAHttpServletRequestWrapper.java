package com.jaspersoft.jasperserver.war.xmla.wrappers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class XMLAHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private byte[] contentData;

	public XMLAHttpServletRequestWrapper(HttpServletRequest request,
                                         byte[] contentData) {
        super(request);
		setContentData(contentData);
	}

	public XMLAHttpServletRequestWrapper(HttpServletRequest request) {
		this(request, new byte[0]);
	}

	public void setContentData(byte[] contentData) {
		this.contentData=contentData;
	}


	/**
	 * This method is safe to use multiple times. Changing the returned array
	 * will not interfere with this class operation.
	 * 
	 * @return The cloned content data.
	 */
	public byte[] getContentData() {
		return contentData.clone();
	}
	
	/**
	 * This method is safe to call multiple times. Calling it will not interfere
	 * with getParameterXXX() or getReader(). Every time a new
	 * ServletInputStream is returned that reads data from the begining.
	 * 
	 * @return A new ServletInputStream.
	 */
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStreamWrapper(contentData);
	}

	/**
	 * This method is safe to call multiple times. Calling it will not interfere
	 * with getParameterXXX() or getInputStream(). Every time a new
	 * BufferedReader is returned that reads data from the begining.
	 * 
	 * @return A new BufferedReader with the wrapped request's character
	 *         encoding (or UTF-8 if null).
	 */
	public BufferedReader getReader() throws IOException {
		String enc = getRequest().getCharacterEncoding();
		if (enc == null)
			enc = "UTF-8";
		return new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(contentData), enc));
	}
}


