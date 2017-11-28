package com.jaspersoft.jasperserver.api.engine.replication;

import net.sf.ehcache.distribution.jms.JMSUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jndi.ActiveMQInitialContextFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.naming.Context;
import javax.naming.NamingException;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class JRSActiveMQInitialContextFactory extends ActiveMQInitialContextFactory {
    private static final Logger logger = LogManager.getLogger(JRSActiveMQInitialContextFactory.class);
    static final String CACHE_REPLICATION_SETTINGS_XML_FILE = "cache_replication_settings.xml";
    private static final String ACTIVE_MQ_TRUSTED_PACKAGES_PROP = "activeMQTrustedPackages";

    private static List<String> trustedPkgList;
    private static ApplicationContext cacheReplicationCtxt;

    @Override
    @SuppressWarnings("unchecked")
    public Context getInitialContext(Hashtable environment) throws NamingException {
        try {
            Map<String, Object> data = new ConcurrentHashMap<String, Object>();

            if (cacheReplicationCtxt == null) {
                cacheReplicationCtxt = new ClassPathXmlApplicationContext(CACHE_REPLICATION_SETTINGS_XML_FILE);
                trustedPkgList = (List<String>) cacheReplicationCtxt.getBean(ACTIVE_MQ_TRUSTED_PACKAGES_PROP);

                if (logger.isDebugEnabled()) {
                    logger.error("ActiveMQ trusted packages: ");
                    for (String pkg : trustedPkgList)
                        logger.debug(pkg);
                }
            }

            String replicationTopicConnectionFactoryBindingName = (String) environment.get(JMSUtil.TOPIC_CONNECTION_FACTORY_BINDING_NAME);

            if (replicationTopicConnectionFactoryBindingName != null) {
                try {
                    ActiveMQConnectionFactory connectionFactory = createConnectionFactory(environment);
                    connectionFactory.setTrustedPackages(trustedPkgList);
                    data.put(replicationTopicConnectionFactoryBindingName, connectionFactory);
                } catch (URISyntaxException e) {
                    throw new NamingException("Error initialisating TopicConnectionFactory with message " + e.getMessage());
                }
            }

            String getQueueConnectionfactoryBindingName = (String) environment.get(JMSUtil.GET_QUEUE_CONNECTION_FACTORY_BINDING_NAME);

            if (getQueueConnectionfactoryBindingName != null) {
                try {
                    ActiveMQConnectionFactory connectionFactory = createConnectionFactory(environment);
                    connectionFactory.setTrustedPackages(trustedPkgList);
                    data.put(getQueueConnectionfactoryBindingName, connectionFactory);
                } catch (URISyntaxException e) {
                    throw new NamingException("Error initialisating TopicConnectionFactory with message " + e.getMessage());
                }
            }

            String replicationTopicBindingName = (String) environment.get(JMSUtil.REPLICATION_TOPIC_BINDING_NAME);
            if (replicationTopicBindingName != null) {
                data.put(replicationTopicBindingName, createTopic(replicationTopicBindingName));
            }


            String getQueueBindingName = (String) environment.get(JMSUtil.GET_QUEUE_BINDING_NAME);
            if (getQueueBindingName != null) {
                data.put(getQueueBindingName, createQueue(getQueueBindingName));
            }

            return createContext(environment, data);
        } catch (Throwable e) {
            logger.error("Failed to initialize ActiveMQ context", e);
            throw new RuntimeException(e);
        }
    }
}
