package com.jaspersoft.jasperserver.dto.executions;

import com.jaspersoft.jasperserver.dto.adhoc.query.ClientMultiLevelQuery;
import com.jaspersoft.jasperserver.dto.adhoc.query.MultiLevelQueryBuilder;
import com.jaspersoft.jasperserver.dto.adhoc.query.QueryExecutionRequestTest;
import com.jaspersoft.jasperserver.dto.adhoc.query.field.ClientQueryField;
import com.jaspersoft.jasperserver.dto.adhoc.query.field.ClientQueryGroup;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author Grant Bacon <gbacon@tibco.com>
 * @date 1/27/16 3:47PM
 */
public class QueryExecutionRequestSerializationToXMLTest extends QueryExecutionRequestTest {

    @Test
    public void ensureSelectZeroField() throws Exception {
        ClientMultiLevelQuery cq = MultiLevelQueryBuilder.select().build();

        assertThat(xml(request(cq)), is("<queryExecution>\n" +
                "    <query>\n" +
                "        <select>\n" +
                "            <fields/>\n" +
                "        </select>\n" +
                "    </query>\n" +
                "    <dataSourceUri>/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type</dataSourceUri>\n" +
                "</queryExecution>"));

    }

    @Test
    public void ensureSelectOneField() throws Exception {
        ClientMultiLevelQuery cq = MultiLevelQueryBuilder
                .select(new ClientQueryField().setFieldName("sales").setId("fieldName"))
                .build();


        assertThat(xml(request(cq)), is("<queryExecution>\n" +
                "    <query>\n" +
                "        <select>\n" +
                "            <fields>\n" +
                "                <field>\n" +
                "                    <field>sales</field>\n" +
                "                    <id>fieldName</id>\n" +
                "                </field>\n" +
                "            </fields>\n" +
                "        </select>\n" +
                "    </query>\n" +
                "    <dataSourceUri>/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type</dataSourceUri>\n" +
                "</queryExecution>"));

    }

    @Test
    public void ensureSelectTwoFields() throws Exception {
        ClientMultiLevelQuery cq = MultiLevelQueryBuilder
                .select(new ClientQueryField().setFieldName("sales").setId("fieldName"),
                        new ClientQueryField().setFieldName("city").setId("fieldName2"))
                .build();


        assertThat(xml(request(cq)), is("<queryExecution>\n" +
                "    <query>\n" +
                "        <select>\n" +
                "            <fields>\n" +
                "                <field>\n" +
                "                    <field>sales</field>\n" +
                "                    <id>fieldName</id>\n" +
                "                </field>\n" +
                "                <field>\n" +
                "                    <field>city</field>\n" +
                "                    <id>fieldName2</id>\n" +
                "                </field>\n" +
                "            </fields>\n" +
                "        </select>\n" +
                "    </query>\n" +
                "    <dataSourceUri>/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type</dataSourceUri>\n" +
                "</queryExecution>"));
    }

    @Test
    public void ensureSelectOneField_groupByCity() throws Exception {
        ClientMultiLevelQuery cq = MultiLevelQueryBuilder
                .select(new ClientQueryField().setFieldName("sales").setId("fieldName"))
                .groupBy(new ClientQueryGroup().setFieldName("city").setId("g1"))
                .build();


        assertThat(xml(request(cq)), is("<queryExecution>\n" +
                "    <query>\n" +
                "        <select>\n" +
                "            <fields>\n" +
                "                <field>\n" +
                "                    <field>sales</field>\n" +
                "                    <id>fieldName</id>\n" +
                "                </field>\n" +
                "            </fields>\n" +
                "        </select>\n" +
                "        <groupBy>\n" +
                "            <group>\n" +
                "                <field>city</field>\n" +
                "                <id>g1</id>\n" +
                "            </group>\n" +
                "        </groupBy>\n" +
                "    </query>\n" +
                "    <dataSourceUri>/public/Samples/Ad_Hoc_Views/04__Product_Results_by_Store_Type</dataSourceUri>\n" +
                "</queryExecution>"));

    }

}