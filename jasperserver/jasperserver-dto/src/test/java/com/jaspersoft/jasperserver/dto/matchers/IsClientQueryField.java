package com.jaspersoft.jasperserver.dto.matchers;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientField;
import com.jaspersoft.jasperserver.dto.adhoc.query.field.ClientQueryField;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.google.common.base.Objects.equal;

/**
 * Created by schubar on 2/6/16.
 */
public class IsClientQueryField extends TypeSafeMatcher<ClientField> {

    private String id;
    private String type;
    private boolean measure;
    private String format;
    private String fieldName;

    public IsClientQueryField(String id, String type, boolean measure, String fieldName, String format) {
        this.id = id;
        this.type = type;
        this.fieldName = fieldName;
        this.measure = measure;
        this.format = format;
    }

    @Override
    protected boolean matchesSafely(ClientField clientField) {
        if (clientField instanceof ClientQueryField){
            ClientQueryField actualField = (ClientQueryField) clientField;

            return
                    equal(actualField.getId(), id)
                            && equal(actualField.getType(), type)
                            && actualField.isMeasure() == measure
                            && equal(actualField.getFieldName(), fieldName);

        } else {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendValue(new ClientQueryField().setId(id).setFieldName(fieldName));
    }

    @Factory
    public static Matcher isClientQueryField(String id, String type, boolean measure, String fieldName, String format) {
        return new IsClientQueryField(id, type, measure, fieldName, format);
    }

    @Factory
    public static Matcher isClientQueryField(String id) {
        return new IsClientQueryField(id, null, false, null, null);
    }
    @Factory
    public static Matcher isClientQueryField(String id, String fieldName) {
        return new IsClientQueryField(id, null, false, fieldName, null);
    }
    @Factory
    public static Matcher isClientQueryField(String id, String fieldName, String format) {
        return new IsClientQueryField(id, null, false, fieldName, format);
    }
    @Factory
    public static Matcher isClientQueryField(String id, boolean measure) {
        return new IsClientQueryField(id, null, measure, null, null);
    }
    @Factory
    public static Matcher isClientQueryField(String id, boolean measure, String fieldName) {
        return new IsClientQueryField(id, null, measure, fieldName, null);
    }
    @Factory
    public static Matcher isClientQueryField(String id, String type, boolean measure) {
        return new IsClientQueryField(id, type, measure, null, null);
    }
    @Factory
    public static Matcher isClientQueryField(String id, String type, boolean measure, String fieldName) {
        return new IsClientQueryField(id, type, measure, fieldName, null);
    }
}
