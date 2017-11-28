package com.jaspersoft.jasperserver.dto.matchers;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientField;
import com.jaspersoft.jasperserver.dto.adhoc.query.field.ClientQueryGroup;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.google.common.base.Objects.equal;

/**
 * Created by schubar on 2/6/16.
 */
public class IsClientQueryGroup extends TypeSafeMatcher<ClientField> {

    private String id;
    private String type;
    private boolean measure;
    private String format;
    private String fieldName;
    private String categorizer;

    public IsClientQueryGroup(String id, String type, boolean measure, String fieldName, String format, String categorizer) {
        this.id = id;
        this.type = type;
        this.fieldName = fieldName;
        this.measure = measure;
        this.format = format;
        this.categorizer = categorizer;
    }

    @Override
    protected boolean matchesSafely(ClientField clientField) {
        if (clientField instanceof ClientQueryGroup){
            ClientQueryGroup actualGroup = (ClientQueryGroup) clientField;

            return
                    equal(actualGroup.getId(), id)
                            && equal(actualGroup.getType(), type)
                            && equal(actualGroup.getFieldName(), fieldName)
                            && equal(actualGroup.getCategorizer(), categorizer);

        } else {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendValue(new ClientQueryGroup().setId(id).setFieldName(fieldName).setCategorizer(categorizer));
    }

    @Factory
    public static Matcher isClientQueryGroup(String id, String type, boolean measure, String fieldName, String format, String categorizer) {
        return new IsClientQueryGroup(id, type, measure, fieldName, format, categorizer);
    }

    @Factory
    public static Matcher isClientQueryGroup(String id) {
        return new IsClientQueryGroup(id, null, false, null, null, null);
    }
    @Factory
    public static Matcher isClientQueryGroup(String id, String fieldName) {
        return new IsClientQueryGroup(id, null, false, fieldName, null, null);
    }
    @Factory
    public static Matcher isClientQueryGroup(String id, String fieldName, String format) {
        return new IsClientQueryGroup(id, null, false, fieldName, format, null);
    }
    @Factory
    public static Matcher isClientQueryGroup(String id, boolean measure) {
        return new IsClientQueryGroup(id, null, measure, null, null, null);
    }
    @Factory
    public static Matcher isClientQueryGroup(String id, boolean measure, String fieldName) {
        return new IsClientQueryGroup(id, null, measure, fieldName, null, null);
    }
    @Factory
    public static Matcher isClientQueryGroup(String id, String type, boolean measure) {
        return new IsClientQueryGroup(id, type, measure, null, null, null);
    }
    @Factory
    public static Matcher isClientQueryGroup(String id, String type, boolean measure, String fieldName) {
        return new IsClientQueryGroup(id, type, measure, fieldName, null, null);
    }
}
