<%--
    * Copyright (C) 2005 - 2015 TIBCO Software Inc. All rights reserved.
    * http://www.jaspersoft.com.
    *
    * Unless you have purchased  a commercial license agreement from Jaspersoft,
    * the following license terms  apply:
    *
    * This program is free software: you can redistribute it and/or  modify
    * it under the terms of the GNU Affero General Public License  as
    * published by the Free Software Foundation, either version 3 of  the
    * License, or (at your option) any later version.
    *
    * This program is distributed in the hope that it will be useful,
    * but WITHOUT ANY WARRANTY; without even the implied warranty of
    * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    * GNU Affero  General Public License for more details.
    *
    * You should have received a copy of the GNU Affero General Public  License
    * along with this program. If not, see <http://www.gnu.org/licenses/>.
--%>


<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<t:insertTemplate template="/WEB-INF/jsp/templates/page.jsp">
    <t:putAttribute name="pageTitle" value="Dialog Samples"/>
    <t:putAttribute name="bodyID" value="dialogs"/>
    <t:putAttribute name="pageClass" value="test"/>
    <t:putAttribute name="bodyClass" value="oneColumn jrs"/>
    <t:putAttribute name="headerContent" >

        <link rel="stylesheet" href="${pageContext.request.contextPath}/<spring:theme code="jasper-ui/jr-reset.css"/>" type="text/css" media="screen,print"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/<spring:theme code="jasper-ui/base.css"/>" type="text/css" media="screen,print"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/<spring:theme code="jasper-ui/utility.css"/>" type="text/css" media="screen,print"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/<spring:theme code="jasper-ui/icon.css"/>" type="text/css" media="screen,print"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/<spring:theme code="jasper-ui/button.css"/>" type="text/css" media="screen,print"/>


        <jsp:include page="/WEB-INF/jsp/modules/setScriptOptimizationProps.jsp"/>

        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bower_components/requirejs/require.js"></script>

    </t:putAttribute>
    <t:putAttribute name="bodyContent" >

        <t:insertTemplate template="/WEB-INF/jsp/templates/container.jsp">
            <t:putAttribute name="containerClass" value="column decorated primary"/>
            <t:putAttribute name="containerTitle" value="Collage from UI compnents"/>
            <t:putAttribute name="bodyClass" value="oneColumn"/>
            <t:putAttribute name="bodyContent" >

                <table style="margin: 1em">
                    <tbody>
                    <tr>
                        <td>
                            <button class="m-Button m-ButtonPrimary jrs">Small Primary</button>
                            <button class="m-Button m-ButtonDefault jrs" disabled="disabled">Small Default Disabled</button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="m-Icon filter jrs"></div>
                            <span>filter</span>
                        </td>
                        <td>
                            <div class="m-Icon grid jrs"></div>
                            <span>Show/Hide Grid</span>
                        </td>
                    </tr>
                    </tbody>
                </table>



            </t:putAttribute>
            <t:putAttribute name="footerContent">
                <!-- custom content here; remove this comment -->
            </t:putAttribute>
        </t:insertTemplate>

    </t:putAttribute>
</t:insertTemplate>