package uet.ppvan.mangareader.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    CONTINUE("Đang tiến hành"),
    ALL("Tất cả"),
    PAUSED("Tạm ngưng"),
    COMPLETED("Đã hoàn thành")
    ;

    private final String status;

    Status(String status) {
        this.status = status;
    }
    @JsonValue
    public String getStatus() {
        return status;
    }
}
