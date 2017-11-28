package com.jaspersoft.jasperserver.dto.adhoc.dataset;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: ClientMultiAxesLevelReference.java 64626 2016-09-26 13:25:24Z vzavadsk $
 * @since 12.04.2016
 */
public class ClientMultiAxesLevelReference {

    private String name;
    private String dimension;
    private String hierarchy;
    private Boolean aggregation;

    public ClientMultiAxesLevelReference() {
    }

    public ClientMultiAxesLevelReference(ClientMultiAxesLevelReference reference) {
        this.name = reference.getName();
        this.dimension = reference.getDimension();
        this.hierarchy = reference.getHierarchy();
    }

    public String getName() {
        return name;
    }

    public ClientMultiAxesLevelReference setName(String name) {
        this.name = name;
        return this;
    }

    public String getDimension() {
        return dimension;
    }

    public ClientMultiAxesLevelReference setDimension(String dimension) {
        this.dimension = dimension;
        return this;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public ClientMultiAxesLevelReference setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
        return this;
    }

    public Boolean getAggregation() {
        return aggregation;
    }

    public ClientMultiAxesLevelReference setAggregation(Boolean aggregation) {
        this.aggregation = aggregation;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientMultiAxesLevelReference that = (ClientMultiAxesLevelReference) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (dimension != null ? !dimension.equals(that.dimension) : that.dimension != null) return false;
        if (aggregation != null ? !aggregation.equals(that.aggregation) : that.aggregation != null) return false;
        return !(hierarchy != null ? !hierarchy.equals(that.hierarchy) : that.hierarchy != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        result = 31 * result + (hierarchy != null ? hierarchy.hashCode() : 0);
        result = 31 * result + (aggregation != null ? aggregation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                ", name='" + name + '\'' +
                ", dimension='" + dimension + '\'' +
                ", hierarchy='" + hierarchy + '\'' +
                ", aggregation='" + aggregation + '\'' +
                '}';
    }
}
