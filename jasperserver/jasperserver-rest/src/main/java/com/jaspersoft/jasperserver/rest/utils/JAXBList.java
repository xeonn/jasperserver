package com.jaspersoft.jasperserver.rest.utils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: carbiv
 * Date: 11/7/11
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="entityResource")
public class JAXBList<T>
{
    @XmlElement(name="Item")
    List<T> list=new ArrayList<T>();
    public JAXBList (){}
    public JAXBList (List<T> lst){
        list.addAll(lst);
    }

    public JAXBList (T[] arr){
        list = Arrays.asList(arr);
    }

    public void add(T element){
       list.add(element);
    }

    public int size(){
        return list.size();
    }

    public T get(int index){
        return list.get(index);
    }
}

