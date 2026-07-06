package br.com.migrate.invoicy.examples.infrastructure.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class MapPath {

    private MapPath() {
    }

    public static Optional<Object> getFirst(Object root, String path) {
        List<Object> values = getValues(root, path);
        if (values.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(values.getFirst());
    }

    public static List<Object> getValues(Object root, String path) {
        if (root == null || path == null || path.isBlank()) {
            return List.of();
        }
        String[] tokens = path.split("\\.");
        List<Object> current = List.of(root);
        for (String token : tokens) {
            List<Object> next = new ArrayList<>();
            for (Object value : current) {
                next.addAll(resolveToken(value, token));
            }
            current = next;
            if (current.isEmpty()) {
                return List.of();
            }
        }
        return current;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> resolveToken(Object value, String token) {
        if (value == null) {
            return List.of();
        }

        if (token.endsWith("[*]")) {
            String key = token.substring(0, token.length() - 3);
            Object listCandidate = value instanceof Map<?, ?> map ? map.get(key) : null;
            if (listCandidate instanceof List<?> list) {
                return new ArrayList<>((List<Object>) list);
            }
            return List.of();
        }

        if (token.matches(".+\\[\\d+\\]")) {
            int bracket = token.indexOf('[');
            String key = token.substring(0, bracket);
            int index = Integer.parseInt(token.substring(bracket + 1, token.length() - 1));
            Object listCandidate = value instanceof Map<?, ?> map ? map.get(key) : null;
            if (listCandidate instanceof List<?> list && index >= 0 && index < list.size()) {
                return List.of(list.get(index));
            }
            return List.of();
        }

        if (value instanceof Map<?, ?> map) {
            if (map.containsKey(token)) {
                    List<Object> result = new ArrayList<>();
                    result.add(map.get(token));
                    return result;
                }
                return List.of();
        }

        return List.of();
    }

    @SuppressWarnings("unchecked")
    public static void set(Map<String, Object> root, String path, Object value) {
        String[] tokens = path.split("\\.");
        Map<String, Object> current = root;
        for (int i = 0; i < tokens.length - 1; i++) {
            String token = tokens[i];
            Object child = current.get(token);
            if (!(child instanceof Map<?, ?>)) {
                child = new LinkedHashMap<String, Object>();
                current.put(token, child);
            }
            current = (Map<String, Object>) child;
        }
        current.put(tokens[tokens.length - 1], value);
    }
}
