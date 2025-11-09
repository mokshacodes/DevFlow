package com.devflow;

import com.devflow.core.TaskFactory;
import com.devflow.core.TaskManager;
import com.devflow.model.Label;
import com.devflow.model.Priority;
import com.devflow.model.Project;
import com.devflow.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    @AfterEach
    void reset() {
        TaskManager.getInstance().clear();
    }

    @Test
    void addTaskIntoProject() {
        TaskFactory f = new TaskFactory();
        TaskManager tm = TaskManager.getInstance();

        Task t = f.create("Implement auth", Priority.HIGH, Label.feature, "backend", LocalDate.now());
        tm.getOrCreateProject("backend").add(t);

        Project p = tm.getOrCreateProject("backend");
        assertEquals(1, p.getTasks().size());
        assertEquals("Implement auth", p.getTasks().get(0).getTitle());
        assertEquals(Label.feature, p.getTasks().get(0).getLabel());
    }
}
