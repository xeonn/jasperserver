package com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.collections.OrderedMap;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.jaspersoft.jasperserver.api.common.domain.ExecutionContext;
import com.jaspersoft.jasperserver.api.engine.common.service.EngineService;
import com.jaspersoft.jasperserver.api.metadata.common.domain.ResourceReference;
import com.jaspersoft.jasperserver.api.metadata.common.service.impl.hibernate.persistent.RepoDataSource;

public class EhcacheEngineServiceListener implements PostUpdateEventListener,  BeanFactoryAware {

	BeanFactory factory = null;
	protected String ehcacheEngineServiceName;

	public String getEhcacheEngineServiceName() {
		return ehcacheEngineServiceName;
	}

	public void setEhcacheEngineServiceName(String name) {
		this.ehcacheEngineServiceName = name;
	}

	public EhcacheEngineService getEhcacheEngineService() {
		return (EhcacheEngineService)factory.getBean(ehcacheEngineServiceName);
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		Object e = event.getEntity();
		if (e instanceof RepoDataSource) {
			getEhcacheEngineService().clear();
		}
	}

	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		this.factory = factory;
	}

}
