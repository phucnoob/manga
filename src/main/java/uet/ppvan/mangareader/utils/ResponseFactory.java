package uet.ppvan.mangareader.utils;

import uet.ppvan.mangareader.dtos.SuccessResponse;

public class ResponseFactory {
    public static SuccessResponse success(Object data) {
        return new SuccessResponse(true, "", data);
    }

    public static SuccessResponse success(String message, Object data) {
        return new SuccessResponse(true, message, data);
    }

    public static SuccessResponse failure(String message) {
        return new SuccessResponse(false, message, "");
    }

    public static SuccessResponse failure(String message, Object data) {
        return new SuccessResponse(false, message, data);
    }
}
