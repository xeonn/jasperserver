package com.jaspersoft.jasperserver.search.mode.impl;

import com.jaspersoft.jasperserver.search.mode.SearchMode;
import com.jaspersoft.jasperserver.search.mode.SearchModeSettings;
import com.jaspersoft.jasperserver.search.mode.SearchModeSettingsResolver;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Implementation of {@link SearchModeSettingsResolver}.</p>
 *
 * @author Yuriy Plakosh
 * @version $Id$
 */
public class SearchModeSettingsResolverImpl implements SearchModeSettingsResolver, Serializable {
    private Map<SearchMode, SearchModeSettings> settingsMap;

    public void setSettingsMap(Map<SearchMode, SearchModeSettings> settingsMap) {
        this.settingsMap = settingsMap;
    }

    public SearchModeSettings getSettings(SearchMode searchMode) {
        return settingsMap.get(searchMode);
    }
}
