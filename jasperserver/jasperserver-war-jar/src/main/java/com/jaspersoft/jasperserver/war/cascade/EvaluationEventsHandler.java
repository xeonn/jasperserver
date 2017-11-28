package com.jaspersoft.jasperserver.war.cascade;

import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlState;

import java.util.List;
import java.util.Map;

/**
 * Created by: chaim
 * Date: 4/4/12
 * Time: 2:10 PM
 */

public interface EvaluationEventsHandler {

    void beforeEvaluation(String uri, Map<String, String[]> parameters, User user);

    void afterEvaluation(String uri, Map<String, String[]> parameters, List<InputControlState> inputControlStates, User user);
}
