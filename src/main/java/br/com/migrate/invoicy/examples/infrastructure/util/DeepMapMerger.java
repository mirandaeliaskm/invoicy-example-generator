package br.com.migrate.invoicy.examples.infrastructure.util;

import java.util.Map;

public final class DeepMapMerger {

    private DeepMapMerger() {
    }

    public static void applyDotNotationOverrides(Map<String, Object> target, Map<String, Object> overrides) {
        if (overrides == null || overrides.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : overrides.entrySet()) {
            MapPath.set(target, entry.getKey(), entry.getValue());
        }
    }
}
