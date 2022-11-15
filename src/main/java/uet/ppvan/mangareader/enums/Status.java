package uet.ppvan.mangareader.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import uet.ppvan.mangareader.exceptions.EnumParseException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Status {
    CONTINUE("Đang tiến hành"),
    ALL("Tất cả"),
    PAUSED("Tạm ngưng"),
    COMPLETED("Đã hoàn thành")
    ;

    private static final Map<String, Status> reverseValues;
    private static final String defaultErrorMessage;
    static {
        reverseValues = new HashMap<>();
        Arrays.stream(values()).forEach(status -> {
            reverseValues.put(status.value(), status);
        });

        defaultErrorMessage = "%s is not a valid Status";
    }
    private final String status;

    Status(String status) {
        this.status = status;
    }
    @JsonValue
    public String value() {
        return status;
    }

    @JsonCreator
    public static Status fromString(String value) {
        if (!reverseValues.containsKey(value)) {
            throw new EnumParseException(String.format(defaultErrorMessage, value));
        }
        return reverseValues.get(value);
    }
}
