/*
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
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
*/
package com.jaspersoft.jasperserver.dto.adhoc;

import com.jaspersoft.jasperserver.dto.adhoc.dataset.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * @author Vasyl Spachynskyi
 * @version $Id: ClientMultiAxesDatasetTest.java 64626 2016-09-26 13:25:24Z vzavadsk $
 * @since 26.01.2016
 */
public class ClientMultiAxesDatasetTest {

    @Test
    public void copyConstructor_shouldDoDeepDatasetCopy_Success() {
        ClientMultiAxesDataset expected = createMultiAxesDataset();

        ClientMultiAxesDataset actual = new ClientMultiAxesDataset(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void copyConstructor_afterCopyChangedSourceObject_CopiedAndSourceObjectAreNotEquals() {
        ClientMultiAxesDataset expected = createMultiAxesDataset();

        ClientMultiAxesDataset actual = new ClientMultiAxesDataset(expected);
        assertEquals(expected, actual);

        expected.getAxes().get(0).getAxisNode().getChildren().get(0).setMemberIdx(100500);
        assertThat(expected, is(not(actual)));
    }

    private ClientMultiAxesDataset createMultiAxesDataset() {
        ClientAxisNode usaNode = new ClientAxisNode();
        usaNode.setAll(false)
                .setAll(false)
                .setMemberIdx(0)
                .setChildren(asList(new ClientAxisNode()
                        .setAll(false)
                        .setDataIdx(0)
                        .setAll(true)
                        .setMemberIdx(0)));

        ClientAxisNode canadaNode = new ClientAxisNode();
        usaNode.setAll(false)
                .setAll(true)
                .setMemberIdx(1)
                .setChildren(asList(new ClientAxisNode()
                        .setAll(false)
                        .setDataIdx(1)
                        .setAll(true)
                        .setMemberIdx(1)));

        ClientAxisNode rows = new ClientAxisNode()
                .setAll(true)
                .setChildren(asList(usaNode, canadaNode));

        ClientAxisNode columns = new ClientAxisNode()
                .setAll(true)
                .setChildren(asList(new ClientAxisNode()
                        .setAll(true)
                        .setDataIdx(0)));

        ClientMultiAxesDataset expected = new ClientMultiAxesDataset();
        expected.setCounts(asList(2, 2));
        expected.setData(asList(new String[]{"42", "100,500"}, new String[]{"3", "7"}));
        List<ClientGroupAxis> datasets = new ArrayList<ClientGroupAxis>();

        datasets.add(new ClientGroupAxis().setLevel(asList(new String[]{"USA", "Canada"}, new String[]{})));

        ClientAxis columnsAxis = new ClientAxis();
        columnsAxis.setAxisNode(columns);
        columnsAxis.setLevels(Collections.singletonList(
                new ClientMultiAxesGroupLevel()
                        .setReferenceObject(
                                new ClientMultiAxesLevelReference()
                                        .setName("country"))
                        .setMembers(asList("USA", "Canada"))
        ));
        expected.getAxes().add(columnsAxis);

        ClientAxis rowAxis = new ClientAxis();
        rowAxis.setAxisNode(rows);
        rowAxis.setLevels(Collections.singletonList(
                new ClientMultiAxesGroupLevel()
                        .setReferenceObject(
                                new ClientMultiAxesLevelReference())
                        .setMembers(new ArrayList<String>())
        ));

        expected.getAxes().add(rowAxis);

        return expected;
    }
}