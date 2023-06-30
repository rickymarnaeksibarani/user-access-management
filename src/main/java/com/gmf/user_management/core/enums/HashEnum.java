package com.gmf.user_management.core.enums;

public enum HashEnum {
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256");

    private final String hashName;

    HashEnum(String hashName) {
        this.hashName = hashName;
    }

    public String getDisplayName() {
        return hashName;
    }


}
