package com.jaspersoft.jasperserver.core.util;

import java.io.Serializable;

/**
 * Created by nthapa on 7/23/13.
 */
public class MissingObject implements Serializable {

    private String status;
    public MissingObject(String s)
    {
        status=s;
    }
}
