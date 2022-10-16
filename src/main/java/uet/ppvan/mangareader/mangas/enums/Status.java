package uet.ppvan.mangareader.mangas.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

        defaultErrorMessage = "Status must be one of: " + String.join(", ", reverseValues.keySet());
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
            throw new EnumParseException(defaultErrorMessage);
        }
        return reverseValues.get(value);
    }
}
