package uet.ppvan.mangareader.dto;

public class ResponseFactory {
    public static ObjectResponse success(Object data) {
        return new ObjectResponse(true, "", data);
    }

    public static ObjectResponse success(String message, Object data) {
        return new ObjectResponse(true, message, data);
    }

    public static ObjectResponse failure(String message) {
        return new ObjectResponse(false, message, "");
    }

    public static ObjectResponse failure(String message, Object data) {
        return new ObjectResponse(false, message, data);
    }
}
