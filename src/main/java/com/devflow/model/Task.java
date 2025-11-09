package com.devflow.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Task {
    private final String id;
    private String title;
    private Priority priority;
    private Label label;
    private String projectName; // backref for convenience
    private String due; // ISO date string or ""
    private boolean completed;
    private String createdAt; // ISO date string

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;

    public Task(String title, Priority priority, Label label, String projectName, LocalDate due) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.priority = priority == null ? Priority.MEDIUM : priority;
        this.label = label == null ? Label.feature : label;
        this.projectName = projectName == null ? "general" : projectName;
        this.due = due == null ? "" : due.format(ISO);
        this.completed = false;
        this.createdAt = LocalDate.now().format(ISO);
    }

    // Gson no-args constructor support
    public Task() {
        this.id = UUID.randomUUID().toString();
        this.title = "";
        this.priority = Priority.MEDIUM;
        this.label = Label.feature;
        this.projectName = "general";
        this.due = "";
        this.completed = false;
        this.createdAt = LocalDate.now().format(ISO);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public Priority getPriority() { return priority; }
    public Label getLabel() { return label; }
    public String getProjectName() { return projectName; }
    public String getDue() { return due; }
    public boolean isCompleted() { return completed; }
    public String getCreatedAt() { return createdAt; }

    public void setTitle(String title) { this.title = title; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public void setLabel(Label label) { this.label = label; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public void setDue(LocalDate due) { this.due = due == null ? "" : due.format(ISO); }
    public void setCompleted(boolean completed) { this.completed = completed; }
}