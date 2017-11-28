package com.jaspersoft.jasperserver.api.engine.common.virtualdatasourcequery.teiid;

import groovy.util.ObjectGraphBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.teiid.logging.Logger;
import java.util.HashMap;
import java.util.Map;


public class TeiidLogger implements Logger {

        private Map<String, Integer> contextMap = new HashMap<String, Integer>();
    	private int defaultLevel = 0;
        private static final Log log = LogFactory.getLog(TeiidEmbeddedServer.class);

        public TeiidLogger() {};

        public void log(int level, String context, Object msg) {
            log.debug(context);
            if (msg != null) log.debug(msg.toString());
        //    System.out.println(msg.toString());
        }

		public void log(int level, String context, Throwable t, Object msg) {
            log.debug(context);
            log.debug((msg != null? msg.toString() : null), t);
        //    System.out.println(msg.toString());
		}

        public void log(int i, java.lang.String s, java.lang.Object... objects) {
            log.debug(s);
            if (objects != null) {
                for (Object obj : objects) log.debug(obj.getClass().toString() +  ":" + obj.toString());
            }
        }

        public void log(int i, java.lang.String s, java.lang.Throwable throwable, java.lang.Object... objects) {
            log.debug(s);
            if (objects != null) {
                for (Object obj : objects) log.debug(obj.getClass().toString() +  ":" + obj.toString(), throwable);
            }
        }

        public void shutdown() {};

    	public int getLogLevel(String context) {
    		Integer level = this.contextMap.get(context);
    		if (level != null) return level;
    		return defaultLevel;
    	}

    	public void setLogLevel(String context, int logLevel) {
    		this.contextMap.put(context, logLevel);
    	}

    	public boolean isEnabled(String context, int msgLevel) {
    		int level = getLogLevel(context);
    		return level >= msgLevel;
    	}
        public void putMdc(java.lang.String s, java.lang.String s1) {};
        public void removeMdc(java.lang.String s) {};
    }