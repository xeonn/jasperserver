package com.jaspersoft.jasperserver.war.xmla.wrappers;

import java.io.IOException;

import javax.servlet.ServletInputStream;

public class ServletInputStreamWrapper extends ServletInputStream {

	private byte[] data;
	private int idx = 0;

	ServletInputStreamWrapper(byte[] data) {
		if (data == null)
			data = new byte[0];
		this.data = data;
	}

	@Override
	public int read() throws IOException {
		if (idx == data.length)
			return -1;
		return data[idx++];
	}

}
