package it.ddcompendium.service.responses;

import androidx.annotation.NonNull;

public class Status {
    private Integer code;
    private String message;

    public Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Status(String message) {
        this.message = message;
    }

    public Status() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
