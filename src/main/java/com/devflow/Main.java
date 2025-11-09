package com.devflow;

import com.devflow.core.TaskFactory;
import com.devflow.core.TaskManager;
import com.devflow.model.Label;
import com.devflow.model.Priority;
import com.devflow.model.Project;
import com.devflow.model.Task;
import com.devflow.persistence.Storage;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner IN = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println(
                "\n⚙️  DevFlow — track builds, bugs, and tasks efficiently\n" +
                        "Type 'help' for commands.  (projects: general)\n"
        );

        TaskManager tm = TaskManager.getInstance();
        TaskFactory factory = new TaskFactory();
        Storage storage = new Storage(Path.of("devflow.json"));

        while (true) {
            System.out.print("> ");
            String line = IN.nextLine().trim();
            if (line.isEmpty()) continue;

            String cmd = line.split("\\s+")[0].toLowerCase();

            try {
                if ("help".equals(cmd)) {
                    help();
                } else if ("add".equals(cmd)) {
                    add(factory, tm, line);
                } else if ("list".equals(cmd)) {
                    list(tm, line);
                } else if ("complete".equals(cmd)) {
                    complete(tm, line);
                } else if ("save".equals(cmd)) {
                    storage.save(tm.allProjects());
                } else if ("load".equals(cmd)) {
                    tm.clear();
                    for (Project p : storage.load()) {
                        tm.getOrCreateProject(p.getName()).getTasks().addAll(p.getTasks());
                    }
                } else if ("exit".equals(cmd) || "quit".equals(cmd)) {
                    System.out.println("bye");
                    return;
                } else {
                    System.out.println("Unknown command. Type 'help'.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void help() {
        System.out.println(
                "Commands:\n" +
                        "  add --project <name> --title <text> [--label bug|feature|chore] [--priority LOW|MEDIUM|HIGH] [--due YYYY-MM-DD]\n" +
                        "  list --all | --project <name> | --due-today\n" +
                        "  complete --id <taskId>\n" +
                        "  save | load | help | exit\n"
        );
    }

    private static void add(TaskFactory factory, TaskManager tm, String line) {
        Map<String, String> flags = parseFlags(line);

        String project = flags.containsKey("--project") ? flags.get("--project") : "general";
        String title   = flags.containsKey("--title")   ? flags.get("--title")   : "Untitled";
        String prStr   = flags.containsKey("--priority")? flags.get("--priority") : "MEDIUM";
        String lbStr   = flags.containsKey("--label")   ? flags.get("--label")   : "feature";
        Priority pr    = Priority.valueOf(prStr.toUpperCase());
        Label label    = Label.valueOf(lbStr);
        LocalDate due  = flags.containsKey("--due") ? LocalDate.parse(flags.get("--due")) : null;

        Task t = factory.create(title, pr, label, project, due);
        tm.getOrCreateProject(project).add(t);
        System.out.println("Added task " + t.getId() + " to project '" + project + "'.");
    }

    private static void list(TaskManager tm, String line) {
        Map<String, String> flags = parseFlags(line);

        // due today
        if (flags.containsKey("--due-today")) {
            String today = LocalDate.now().toString();
            for (Project p : tm.allProjects()) {
                System.out.println("\n[Project] " + p.getName());
                boolean any = false;
                for (Task t : p.getTasks()) {
                    if (today.equals(t.getDue()) && !t.isCompleted()) {
                        printTask(t);
                        any = true;
                    }
                }
                if (!any) System.out.println("  (no tasks)");
            }
            System.out.println();
            return;
        }

        // list all
        if (flags.containsKey("--all")) {
            for (Project p : tm.allProjects()) {
                printProject(p);
            }
            return;
        }

        // list by project (or general)
        String project = flags.containsKey("--project") ? flags.get("--project") : "general";
        printProject(tm.getOrCreateProject(project));
    }

    private static void complete(TaskManager tm, String line) {
        Map<String, String> flags = parseFlags(line);
        String id = flags.get("--id");
        if (id == null) {
            System.out.println("--id required");
            return;
        }

        // manual Optional handling to avoid Java 9+ API
        Task found = null;
        outer:
        for (Project p : tm.allProjects()) {
            for (Task t : p.getTasks()) {
                if (t.getId().equals(id)) { found = t; break outer; }
            }
        }

        if (found != null) {
            found.setCompleted(true);
            System.out.println("Completed.");
        } else {
            System.out.println("Task not found");
        }
    }

    private static void printProject(Project p) {
        System.out.println("\n[Project] " + p.getName());

        if (p.getTasks().isEmpty()) {
            System.out.println("  (no tasks)\n");
            return;
        }

        for (Task t : p.getTasks()) {
            printTask(t);
        }

        System.out.println();
    }

    private static void printTask(Task t) {
        String due = (t.getDue().isEmpty() ? "—" : t.getDue());
        System.out.printf("  - %s | %s | label=%s | prio=%s | due=%s | done=%s%n",
                t.getId(), t.getTitle(), t.getLabel(), t.getPriority(), due, t.isCompleted());
    }

    private static Map<String, String> parseFlags(String line) {
        Map<String, String> m = new HashMap<String, String>();
        String[] toks = line.split("\\s+");
        for (int i = 1; i < toks.length; i++) {
            String t = toks[i];
            if (t.startsWith("--")) {
                if (i + 1 < toks.length && !toks[i + 1].startsWith("--")) {
                    m.put(t, toks[i + 1]);
                    i++;
                } else {
                    m.put(t, "true");
                }
            }
        }
        return m;
    }
}
