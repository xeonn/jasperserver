package com.jaspersoft.jasperserver.search.mode;

/**
 * <p>Resolver for SearchMode settings.</p>
 *
 * @author Yuriy Plakosh
 * @version $Id$
 */
public interface SearchModeSettingsResolver {
    SearchModeSettings getSettings(SearchMode searchMode);
}
