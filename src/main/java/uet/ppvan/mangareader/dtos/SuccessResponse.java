package uet.ppvan.mangareader.dtos;

/**
 * Response format to make is easy to handle in client.
 * @param success
 * @param message
 * @param data
 */
public record SuccessResponse
    (
        Boolean success,
        String message,
        Object data
    ) {
    public static final Boolean SUCCESS = true;
    public static final Boolean FAILED = false;

}
