package com.jaspersoft.jasperserver.war.cascade;

import com.jaspersoft.jasperserver.api.metadata.user.domain.User;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.InputControlState;

import java.util.List;
import java.util.Map;

/**
 * User: chaim
 * Date: 4/4/12
 * Time: 2:09 PM
 *  This is a dummy class to handle input control evaluation.
 */
public class EmptyEvaluationEventsHandlerImpl implements EvaluationEventsHandler {
    @Override
    public void beforeEvaluation(String uri, Map<String, String[]> parameters, User user) {

    }

    @Override
    public void afterEvaluation(String uri, Map<String, String[]> parameters, List<InputControlState> inputControlStates, User user) {

    }
}
