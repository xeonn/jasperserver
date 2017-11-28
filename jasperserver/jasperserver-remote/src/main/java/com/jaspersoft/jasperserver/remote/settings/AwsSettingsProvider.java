/*
* Copyright (C) 2005 - 2013 Jaspersoft Corporation. All rights  reserved.
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
* along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
*/
package com.jaspersoft.jasperserver.remote.settings;

import com.jaspersoft.jasperserver.api.engine.jasperreports.util.AwsEc2MetadataClient;
import com.jaspersoft.jasperserver.api.engine.jasperreports.util.AwsProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p></p>
 *
 * @author yaroslav.kovalchyk
 * @version $Id: AwsSettingsProvider.java 41232 2014-01-31 14:42:48Z ykovalchyk $
 */
@Service
public class AwsSettingsProvider implements SettingsProvider {
    @Resource
    private List<String> awsRegions;
    @Resource
    private AwsEc2MetadataClient awsEc2MetadataClient;
    @Resource
    private AwsProperties awsProperties;
    @Override
    public Object getSettings() {
        final HashMap<String, Object> settings = new HashMap<String, Object>();
        settings.put("awsRegions", new ArrayList<String>(awsRegions));
        settings.put("isEc2Instance", awsEc2MetadataClient.isEc2Instance());
        settings.put("suppressEc2CredentialsWarnings", awsProperties.isSuppressEc2CredentialsWarnings());
        return settings;
    }
}
