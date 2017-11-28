package com.jaspersoft.jasperserver.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * This class is used to override HttpServeletRequest so that it can return TolerantHttpSession instead of HttpSession
 *
 * Created by nthapa on 7/12/13.
 */
public class TolerantRequest extends HttpServletRequestWrapper {

    public TolerantRequest(HttpServletRequest obj)
    {
        super(obj);
    }

    @Override
    public HttpSession getSession()
    {
       return getSession(true);

    }

    /*
    * Overrides the getSession method of HttpServletRequestWrapper
    *
    *return null if HttpSession is null else returns TolerantHttpSession
    *
     */
    @Override
    public HttpSession getSession(boolean create)
    {
        HttpSession session = super.getSession(create);
        if (session != null) {
            return new TolerantHttpSession(session);
        } else {
            return session;
        }

    }
}
