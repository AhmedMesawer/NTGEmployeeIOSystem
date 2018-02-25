package com.example.ilias.ntgemployeeiosystem.data.network;

/**
 * Created by ilias on 05/02/2018.
 */

public class APIError {

    private int code;
    private String message;

    public APIError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    String getMessage() {
        return message;
    }
}
