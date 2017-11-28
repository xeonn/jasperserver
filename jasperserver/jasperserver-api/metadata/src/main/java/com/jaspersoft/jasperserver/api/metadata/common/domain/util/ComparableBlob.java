package com.jaspersoft.jasperserver.api.metadata.common.domain.util;

import java.sql.Blob;

import org.hibernate.lob.BlobImpl;
import org.hibernate.lob.SerializableBlob;

public class ComparableBlob extends SerializableBlob {
	private static final long serialVersionUID = -7928795808929208452L;
	private byte[] bytes;
	private transient Blob myBlob;
	
	public ComparableBlob(byte[] bytes) {
		super(new BlobImpl(bytes));
		this.bytes = bytes;
	}
	
	// superclass implements Serializable but doesn't actually work after serialization, so can't be cached.
	// we are saving the byte[] so we can create blob as needed
	public Blob getWrappedBlob() {
		if (myBlob == null) {
			myBlob = new BlobImpl(bytes);
		}
		return myBlob;
	}
	
	// same byte array? fine!
	public boolean equals(Object o) {
		return o instanceof ComparableBlob && ((ComparableBlob) o).bytes == bytes;
	}

	public byte[] getBytes() {
		return bytes;
	}


}