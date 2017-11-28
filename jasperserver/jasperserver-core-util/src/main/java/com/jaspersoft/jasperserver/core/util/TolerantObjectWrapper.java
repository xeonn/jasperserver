package com.jaspersoft.jasperserver.core.util;

import org.apache.commons.io.output.NullOutputStream;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Wraps the objects that is stored in the session.
 * if the object is serializable then writes the object otherwise write MissingObject to the stream
 *
 * Created by nthapa on 7/18/13.
 */
public class TolerantObjectWrapper implements Serializable {

	protected final Logger log = Logger.getLogger(getClass());

    protected Object obj;

    public TolerantObjectWrapper(Object obj)
    {
        this.obj = obj;
    }
    public Object getObject()
    {
        return obj;
    }

    public void setObject(Object obj)
    {
        this.obj = obj;
    }

    @Override
    public String toString()
    {
       return obj.getClass().toString();

    }

    /**
     * tests whether an object is serializable
     *
     * return boolean representing if it is serializable
     *
     * */
    private boolean testIsSerializable(Object obj)  throws IOException {

        NullOutputStream  nos= new NullOutputStream();
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(nos);
            oos.writeObject(obj);
            oos.close();
            nos.close();

        } catch (Exception err) {
           return  false;
        }
        return true;
    }


    private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException
    {

         obj = stream.readObject();
    }

     /*
     * Writes Object to stream if it is serializable else writes MissingObject to the Stream
     *
     * @param stream ObjectOutputStream
      */
    private void writeObject(ObjectOutputStream stream) throws ClassNotFoundException, IOException
    {
        if(testIsSerializable(obj))
        {
            stream.writeObject(obj);
        }
        else
        {
        	if (log.isDebugEnabled())
        		log.debug("Non serialized object on session "+obj.getClass());
            stream.writeObject(new MissingObject("MissingValue"));
        }

    }
}
