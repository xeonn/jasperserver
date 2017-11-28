package com.jaspersoft.jasperserver.jaxrs.poc.hypermedia.common;

/**
 * @author: Igor.Nesterenko
 * @version: ${Id}
 */
public enum Permissions{

    NOTHING(0),
    ADMINISTRATION(1),
    READ(2),
    WRITE(4),
    CREATE(8),
    DELETE(16),
    RUN(30);

    Integer mask;

    Permissions(Integer mask) {
        this.mask = mask;
    }

    public Integer mask(){
        return  mask;
    }

    public boolean equals(Integer that){
         return  this.mask().equals(that);
    }

}
