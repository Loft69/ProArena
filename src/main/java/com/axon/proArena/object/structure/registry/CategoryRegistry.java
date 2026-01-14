package com.axon.proArena.object.structure.registry;

import com.axon.proArena.object.structure.api.Category;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class CategoryRegistry {
    static Map<String, Category> CATEGORIES = new HashMap<>();

    public static void register(@NonNull Category category) {
        if (CATEGORIES.get(category.name()) != null) return;

        CATEGORIES.put(category.name(), category);
    }

    public static Category get(@NonNull String name) {
        return CATEGORIES.get(name);
    }

    public static Collection<Category> all() {
        return Collections.unmodifiableCollection(CATEGORIES.values());
    }
}
