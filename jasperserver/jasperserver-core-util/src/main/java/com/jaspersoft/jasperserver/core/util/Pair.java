package com.jaspersoft.jasperserver.core.util;

import java.io.Serializable;

public class Pair implements Serializable
{
	
	private static final long serialVersionUID = 6235709123474415196L;
	private final Object o1;
	private final Object o2;
	private final int hash;

	
	/**
	 * Create a pair instance.
	 * 
	 * @param o1 the first member of the pair
	 * @param o2 the second member of the pair
	 */
	public Pair(Object o1, Object o2)
	{
		this.o1 = o1;
		this.o2 = o2;
		this.hash = computeHash();
	}
	
	public Object getFirst() {
		return o1;
	}
	
	public Object getSecond() {
		return o2;
	}

	private int computeHash()
	{
		int hashCode = o1 == null ? 0 : o1.hashCode();
		hashCode *= 31;
		hashCode += o2 == null ? 0 : o2.hashCode();
		return hashCode;
	}

	public boolean equals(Object o)
	{
		if (o == this)
		{
			return true;
		}
		
		if (o == null || !(o instanceof Pair))
		{
			return false;
		}
		
		Pair p = (Pair) o;
		
		return (p.o1 == null ? o1 == null : (o1 != null && p.o1.equals(o1))) &&
			(p.o2 == null ? o2 == null : (o2 != null && p.o2.equals(o2)));
	}

	public int hashCode()
	{
		return hash;
	}
	
	public String toString()
	{
		return "(" + String.valueOf(o1) + ", " + String.valueOf(o2) + ")";
	}

}