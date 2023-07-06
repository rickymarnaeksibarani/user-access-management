package com.gmf.user_management.core.utils;

public class StringUtil {

    public static String transformToCamelCase(String origin) {
        String[] words = origin.split("\\s+|\\p{Punct}");

        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            words[i] = Character.toUpperCase(word.charAt(0)) + word.substring(1);
        }

        StringBuilder camelCaseBuilder = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            camelCaseBuilder.append(words[i]);
        }

        return camelCaseBuilder.toString();

    }

}
