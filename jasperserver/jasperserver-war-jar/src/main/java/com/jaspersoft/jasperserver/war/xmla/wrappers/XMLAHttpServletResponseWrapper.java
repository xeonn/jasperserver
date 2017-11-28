package com.jaspersoft.jasperserver.war.xmla.wrappers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class XMLAHttpServletResponseWrapper extends HttpServletResponseWrapper {

	ServletOutputStreamWrapper outputStream = new ServletOutputStreamWrapper();

	public XMLAHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return outputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(outputStream);
	}

	public String getBytes() {
		return outputStream.getBytes();
	}

	public void updateBytes(String s) {
		outputStream.updateBytes(s);
	}
}
