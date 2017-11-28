/*
* Copyright (C) 2005 - 2012 Jaspersoft Corporation. All rights reserved.
* http://www.jaspersoft.com.
* Licensed under commercial Jaspersoft Subscription License Agreement.
*/

package com.jaspersoft.jasperserver.remote.services.async;

import com.jaspersoft.jasperserver.remote.exception.NoSuchTaskException;

import java.util.Set;

/**
 *
 * Provides api for asynchronous processes.
 *
 */
public interface TasksManager {

    /**
     * Get task's uuids from storage
     *
     * @return List of ids
     */


    Set<String> getTaskIds();

    /**
     * Get task's by uuid from storage
     *
     *
     * @param taskId- contains some time consuming action
     * @throws com.jaspersoft.jasperserver.remote.exception.NoSuchTaskException
     * @return Task
     */


    Task getTask(String taskId) throws NoSuchTaskException;

    /**
     * Put task to storage and starts provided task
     *
     *
     * @param task- contains some time consuming action
     * @return uuid for task
     */

    StateDto startTask(Task task);

    /**
     * Finish selected task, does clean up of used resources
     *
     *
     * @param taskId - uuid of task
     * @throws com.jaspersoft.jasperserver.remote.exception.NoSuchTaskException
     */

    void finishTask(String taskId) throws NoSuchTaskException;

    /**
     * Returns state of task with provided id.
     *
     *
     * @param taskId - uuid of task
     * @return current state of task
     * @throws com.jaspersoft.jasperserver.remote.exception.NoSuchTaskException
     */
    StateDto getTaskState(String taskId) throws NoSuchTaskException;

}