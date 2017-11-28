/*
 * Copyright (C) 2005 - 2015 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * Licensed under commercial Jaspersoft Subscription License Agreement
 */
package com.jaspersoft.jasperserver.dto.adhoc.dataset;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by schubar on 12/3/13.
 * @version $Id: ClientMultiAxesDataset.java 62490 2016-04-12 20:31:10Z vspachyn $
 */
@XmlRootElement
public class ClientMultiAxesDataset {
    private List<ClientAxis> axes = new ArrayList<ClientAxis>();

    private List<String[]> data = new ArrayList<String[]>();
    private List<Integer> counts = new ArrayList<Integer>();

    public ClientMultiAxesDataset() {}

    // Constructor for the deep copy
    public ClientMultiAxesDataset(final ClientMultiAxesDataset dataset) {
        counts.addAll(dataset.getCounts());
        for (ClientAxis axis : dataset.getAxes()) {
            axes.add(new ClientAxis(axis));
        }
        for (String[] values : dataset.getData()) {
            data.add(Arrays.copyOf(values, values.length));
        }
    }

    @XmlElementWrapper(name = "axes")
    @XmlElement(name = "axis")
    public List<ClientAxis> getAxes() {
        return axes;
    }

    public void setAxes(List<ClientAxis> axes) {
        this.axes = axes;
    }

    @XmlElementWrapper(name = "data")
    @XmlElement(name = "column")
    public List<String[]> getData() {
        return data;
    }

    public ClientMultiAxesDataset setData(List<String[]> data) {
        this.data = data;
        return this;
    }

    public List<Integer> getCounts() {
        return counts;
    }

    public ClientMultiAxesDataset setCounts(List<Integer> counts) {
        this.counts = counts;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientMultiAxesDataset that = (ClientMultiAxesDataset) o;

        if (axes != null ? !axes.equals(that.axes) : that.axes != null) return false;
        if (data == that.data) return true;
        for (int i = 0; i < data.size(); i++) {
            if (!Arrays.equals(data.get(i), that.data.get(i))) {
                return false;
            }
        }
        return !(counts != null ? !counts.equals(that.counts) : that.counts != null);

    }

    @Override
    public int hashCode() {
        int result = axes != null ? axes.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (counts != null ? counts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientMultiAxesDataset{" +
                "axes=" + axes +
                ", data=" + data +
                ", counts=" + counts +
                '}';
    }
}
