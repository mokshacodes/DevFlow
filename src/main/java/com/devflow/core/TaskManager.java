package com.devflow.core;

import com.devflow.model.Project;
import com.devflow.model.Task;

import java.util.*;

// Singleton — central authority for projects & tasks
public class TaskManager {
    private static TaskManager INSTANCE;

    private final Map<String, Project> projects = new HashMap<>();

    private TaskManager() {
// default project
        projects.put("general", new Project("general"));
    }

    public static synchronized TaskManager getInstance() {
        if (INSTANCE == null) INSTANCE = new TaskManager();
        return INSTANCE;
    }

    public void clear() { projects.clear(); projects.put("general", new Project("general")); }

    public Project getOrCreateProject(String name) {
        return projects.computeIfAbsent(name, Project::new);
    }

    public Collection<Project> allProjects() { return projects.values(); }

    public Optional<Task> findById(String id) {
        for (Project p : projects.values()) {
            for (Task t : p.getTasks()) {
                if (t.getId().equals(id)) return Optional.of(t);
            }
        }
        return Optional.empty();
    }
}