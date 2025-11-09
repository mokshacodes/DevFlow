package com.devflow.model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private List<Task> tasks = new ArrayList<>();

    public Project(String name) { this.name = name; }
    public Project() { this.name = "general"; }

    public String getName() { return name; }
    public List<Task> getTasks() { return tasks; }
    public void add(Task t) { tasks.add(t); }
}