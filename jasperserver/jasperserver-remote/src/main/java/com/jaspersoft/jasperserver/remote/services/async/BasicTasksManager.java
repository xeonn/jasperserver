/*
* Copyright (C) 2005 - 2012 Jaspersoft Corporation. All rights reserved.
* http://www.jaspersoft.com.
* Licensed under commercial Jaspersoft Subscription License Agreement.
*/

package com.jaspersoft.jasperserver.remote.services.async;

import com.jaspersoft.jasperserver.remote.exception.NoSuchTaskException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*
*  @author inesterenko
*/

@Component
public class BasicTasksManager implements TasksManager {

    protected Map<String, Task> tasks = new ConcurrentHashMap<String, Task>();

    public BasicTasksManager(Map<String, Task> tasks) {
        this.tasks = new ConcurrentHashMap<String, Task>(tasks);
    }

    public BasicTasksManager() {
        super();
    }

    protected  String generateUniqueId(){
        String uuid = UUID.randomUUID().toString();
        if (tasks.containsKey(uuid)) {
            uuid = generateUniqueId();
        }
        return uuid;
    }

    @Override
    public StateDto startTask(Task task) {
        StateDto stateDto = task.getState();
        stateDto.setPhase(Task.INPROGRESS);
        String uuid = generateUniqueId();
        task.setUniqueId(uuid);
        stateDto.setId(uuid);
        tasks.put(uuid, task);
        task.start();
        return stateDto;
    }

    @Override
    public Set<String> getTaskIds() {
        return Collections.unmodifiableSet(tasks.keySet());
    }

    @Override
    public Task getTask(String taskId) throws NoSuchTaskException {
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        } else {
            throw new NoSuchTaskException(taskId);
        }
    }

    @Override
    public void finishTask(String taskId) throws NoSuchTaskException {
        getTask(taskId).stop();
        tasks.remove(taskId);
    }

    public StateDto getTaskState(String taskId) throws NoSuchTaskException {
        return getTask(taskId).getState();
    }
}
