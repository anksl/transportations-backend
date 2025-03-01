package com.transport.api.exception;

public final class NoSuchEnumException extends RuntimeException {

    private static final String MSG_ID =
        "Enum '%s' with id '%s' does not exist";
    private static final String MSG_NULL =
        "Enum '%s' cannot be null";

    private NoSuchEnumException(String message) {
        super(message);
    }

    public static NoSuchEnumException create(Class<? extends Enum<?>> type, Integer id) {
        var name = type.getSimpleName();
        return new NoSuchEnumException(String.format(MSG_ID, name, id));
    }

    public static NoSuchEnumException create(Class<? extends Enum<?>> type) {
        var name = type.getSimpleName();
        return new NoSuchEnumException(String.format(MSG_NULL, name));
    }
}