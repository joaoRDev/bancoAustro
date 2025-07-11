package com.banco.austro.service;

public class ServiceResult<T> {
    private final boolean success;
    private final T data;
    private final String errorMessage;

    private ServiceResult(boolean success, T data, String errorMessage) {
        this.success = success;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> ServiceResult<T> success(T data) {
        return new ServiceResult<>(true, data, null);
    }

    public static <T> ServiceResult<T> failure(String errorMessage) {
        return new ServiceResult<>(false, null, errorMessage);
    }

    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getErrorMessage() { return errorMessage; }
}