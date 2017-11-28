

package com.jaspersoft.jasperserver.rest.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;


/**
 *
 * 
 * Created 5/15/2013 @author nthapa
 * 
 */
public class DataSourceDTO implements DataSource{
// TODO UDI check

	private String contentType;
	private String name;
	private byte[] bInputStream;
	private byte[] bOutputStream;
	
	public void setContentType(String content_Type)
	{
		this.contentType=content_Type;
	}
	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return contentType;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		ByteArrayInputStream bstream= new ByteArrayInputStream(bInputStream);
		return (InputStream) bstream;
		
		//return null;
	}

	public void setiByteArray(InputStream br) throws IOException
	{
		br.read(bInputStream);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bostream= new ByteArrayOutputStream();
		return (OutputStream)bostream;
		
	}	

  
}
