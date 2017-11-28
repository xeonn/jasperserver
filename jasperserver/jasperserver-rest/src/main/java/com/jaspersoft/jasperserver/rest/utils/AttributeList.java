package com.jaspersoft.jasperserver.rest.utils;


import com.jaspersoft.jasperserver.api.metadata.user.domain.client.ProfileAttributeImpl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: carbiv
 * Date: 10/8/11
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="ProfileAttributes")
public class AttributeList<ProfileAttributeImpl>{
    protected List<ProfileAttributeImpl> list;

    public AttributeList(){}

    public AttributeList(List<ProfileAttributeImpl> list){
        this.list=list;
    }

    @XmlElement(name="ProfileAttribute")
    public List<ProfileAttributeImpl> getList(){
        return list;
    }

    public void setHostedServices(List<ProfileAttributeImpl> attributes)
    {
        list = attributes;
    }
}

