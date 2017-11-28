package com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.collections.OrderedMap;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.engine.common.service.EngineService;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceReference;

public class EhcacheEngineService extends EngineBaseDecorator {

	final public static String IC_REFRESH_KEY = "com.jaspersoft.cascade.refreshIC";
	final public static String IC_CACHE_KEY = "com.jaspersoft.cascade.ICcacheKey";

	private Ehcache cache;

	public Ehcache getCache() {
		return cache;
	}

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	public void setEngineService(EngineService engine) {
		setDecoratedEngine(engine);
	}

	public EngineService getEngineService() {
		return getDecoratedEngine();
	}
		
    public OrderedMap executeQuery(ExecutionContext context,
		ResourceReference queryReference, String keyColumn, String[] resultColumns,
		ResourceReference defaultDataSourceReference,
		Map parameterValues, Map<String, Class<?>> parameterTypes, boolean formatValueColumns) 
    {
		String key = (String)parameterValues.get(IC_CACHE_KEY);
		Element e = null;
		OrderedMap value = null;
        boolean refresh = parameterValues!=null&&parameterValues.containsKey(IC_REFRESH_KEY);
		if (key!=null) {
			if (refresh) {
				cache.remove(key);
			} else {
				e = cache.get(key);
	    		if (e!=null) {
	    			value = (OrderedMap)(e.getValue());
	    			if (value!=null) return value;
	    		}	    	
			}
		}
		value=getDecoratedEngine().executeQuery(context, queryReference, keyColumn, resultColumns, defaultDataSourceReference, parameterValues, parameterTypes, formatValueColumns);
		if (key!=null&&value!=null) {
			e = new Element(key,value);
			cache.put(e);
		}
		return value;
    }
   
    public void clear() {
    	cache.removeAll();
    }
}
