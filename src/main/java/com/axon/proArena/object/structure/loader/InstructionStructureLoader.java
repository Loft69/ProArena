package com.axon.proArena.object.structure.loader;

import com.axon.proArena.object.structure.api.Category;
import com.axon.proArena.object.structure.impl.InstructionCategory;
import com.axon.proArena.object.structure.impl.StructureInstruction;
import com.axon.proArena.object.structure.FactoryType;
import com.axon.proArena.object.structure.api.Structure;
import com.axon.proArena.object.structure.registry.CategoryRegistry;
import com.axon.proArena.object.structure.registry.StructureRegistry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@NoArgsConstructor
public class InstructionStructureLoader {
    Gson GSON = new GsonBuilder().create();

    public void load(@NonNull Path root) {
        Map<String, Category> categoryMap = new HashMap<>();

        if (!Files.exists(root)) return;

        try {
            Files.walk(root, 2)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(path -> {
                        try {
                            String categoryName = resolveCategory(root, path);
                            String structureName = fileName(path);

                            StructureInstruction instruction = read(path);
                            if (instruction == null) return;

                            Category category = categoryMap.computeIfAbsent(categoryName, InstructionCategory::new);

                            Structure structure = StructureRegistry.create(FactoryType.INSTRUCTION, structureName, instruction);

                            category.add(structure);
                            Bukkit.getLogger().info("Success load structure: " + structureName + " in category: " + categoryName);
                        } catch (Exception e) {
                            Bukkit.getLogger().severe(
                                    "Failed to load structure: " + path
                            );
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Category category : categoryMap.values()) {
            CategoryRegistry.register(category);
        }


    }

    private StructureInstruction read(Path path) throws IOException {
        return GSON.fromJson(
                Files.readString(path, StandardCharsets.UTF_8),
                StructureInstruction.class
        );
    }

    private String resolveCategory(Path root, Path file) {
        Path parent = file.getParent();
        if (parent == null || parent.equals(root)) return "default";

        return parent.getFileName().toString();
    }

    private static String fileName(Path path) {
        String name = path.getFileName().toString();
        int dot = name.lastIndexOf('.');
        return dot == -1 ? name : name.substring(0, dot);
    }
}

