package com.jaspersoft.jasperserver.dto.adhoc.query.el.range;

import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpression;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientExpressions;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ClientLiteral;
import com.jaspersoft.jasperserver.dto.adhoc.query.el.ast.ClientELVisitor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @version $Id: ClientRange.java 64791 2016-10-12 15:08:37Z ykovalch $
 */
@XmlRootElement(name = ClientRange.RANGE_ID)
@XmlType(propOrder = {"start", "end"})
public class ClientRange implements ClientExpression<ClientRange> {
    public static final String RANGE_ID = "range";

    private ClientRangeBoundary start;
    private ClientRangeBoundary end;
    protected Boolean paren = null;

    public ClientRange() {
    }

    public ClientRange(ClientRangeBoundary start, ClientRangeBoundary end) {
        this.start = new ClientRangeBoundary(start);
        this.end = new ClientRangeBoundary(end);
    }

    public ClientRange(ClientLiteral start, ClientLiteral end) {
        this(new ClientRangeBoundary(start), new ClientRangeBoundary(end));
    }

    public ClientRange(ClientExpression start, ClientExpression end) {
        this(new ClientRangeBoundary(start), new ClientRangeBoundary(end));
    }

    public ClientRange(ClientRange range) {
        this(new ClientRangeBoundary(range.getStart()), new ClientRangeBoundary(range.getEnd()));
    }

    public ClientRangeBoundary getEnd() {
        return end;
    }

    public ClientRange setEnd(ClientRangeBoundary end) {
        this.end = end;
        return this;
    }

    public ClientRangeBoundary getStart() {
        return start;
    }

    public ClientRange setStart(ClientRangeBoundary start) {
        this.start = start;
        return this;
    }

    @Override
    public void accept(ClientELVisitor visitor) {
        if (this.start != null) {
            this.start.accept(visitor);
        }
        if (this.end != null) {
            this.end.accept(visitor);
        }
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientRange)) return false;

        ClientRange that = (ClientRange) o;

        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        return paren != null ? paren.equals(that.paren) : that.paren == null;

    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (paren != null ? paren.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String startString;
        String endString;
        if (getStart() != null && getStart().getBoundary() != null) {
            startString = getStart().getBoundary().toString();
        } else {
            startString = ClientExpressions.MISSING_REPRESENTATION;
        }
        if (getEnd() != null && getEnd().getBoundary() != null) {
            endString = getEnd().getBoundary().toString();
        } else {
            endString = ClientExpressions.MISSING_REPRESENTATION;
        }
        return "(" + startString + ":" + endString + ")";
    }

    public Boolean isParen() {
        return (paren == null) ? null : paren;
    }

    @Override
    public Boolean hasParen() {
        return isParen() != null && paren;
    }

    @Override
    public ClientRange deepClone() {
        return new ClientRange(this);
    }
}
