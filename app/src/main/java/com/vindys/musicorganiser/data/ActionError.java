package com.vindys.musicorganiser.data;

import java.util.HashMap;
import java.util.List;

public class ActionError {
    private int code;
    private String message;
    private boolean error;
    private HashMap<String, List<String>> errors;

    public ActionError(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public HashMap<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, List<String>> errors) {
        this.errors = errors;
    }
}