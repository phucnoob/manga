package uet.ppvan.mangareader.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Status {
    CONTINUE("Đang tiến hành"),
    ALL("Tất cả"),
    PAUSED("Tạm ngưng"),
    COMPLETED("Đã hoàn thành")
    ;

    private static final Map<String, Status> reverseValues;
    static {
        reverseValues = new HashMap<>();
        Arrays.stream(values()).forEach(status -> {
            reverseValues.put(status.getStatus(), status);
        });
    }
    private final String status;

    Status(String status) {
        this.status = status;
    }
    @JsonValue
    public String getStatus() {
        return status;
    }

    public static Optional<Status> fromString(String value) {
        return Optional.ofNullable(reverseValues.get(value));
    }
}
