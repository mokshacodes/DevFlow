package com.devflow.core;

import com.devflow.model.*;
import java.time.LocalDate;

// Factory Method — currently returns a basic Task, but easy to extend
public class TaskFactory {
    public Task create(String title, Priority priority, Label label, String project, LocalDate due) {
        return new Task(title, priority, label, project, due);
    }
}