package com.devflow.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.devflow.model.Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Storage {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path file;

    public Storage(Path file) { this.file = file; }

    public void save(Collection<Project> projects) throws IOException {
        Map<String, Object> root = new HashMap<>();
        root.put("projects", projects);
        Files.writeString(file, GSON.toJson(root));
    }

    public List<Project> load() throws IOException {
        if (!Files.exists(file)) return new ArrayList<>();
        String json = Files.readString(file);
        Map<?,?> decoded = GSON.fromJson(json, Map.class);
// re-encode to typed list to leverage Gson mapping
        String projectsJson = GSON.toJson(decoded.get("projects"));
        Project[] arr = GSON.fromJson(projectsJson, Project[].class);
        return new ArrayList<>(Arrays.asList(arr));
    }
}