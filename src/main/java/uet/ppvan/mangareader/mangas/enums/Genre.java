package uet.ppvan.mangareader.mangas.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.*;

public enum Genre {
    SIXTEEN_PLUS("16+"),
    EIGHTEEN_PLUS("18+"),
    ACTION("Action"),
    ANCIENT("Cổ Đại"),
    ADULT("Adult"),
    ADVENTURE("Adventure"),
    ANIME("Anime"),
    VIOLENCE("Bạo lực - Máu me"),
    COMEDY("Comedy"),
    COMIC("Comic"),
    DOUJINSHI("Doujinshi"),
    DRAMA("Drama"),
    ECCHI("Ecchi"),
    EVENT_BT("Event BT"),
    FANTASY("Fantasy"),
    FULL_COLOR("Full màu", "Truyện Màu"),
    GAME("Game"),
    GENDER_BENDER("Gender Bender"),
    HAREM("Harem"),
    HISTORICAL("Historical"),
    HORROR("Horror"),
    ISEKAI("Isekai", "Chuyển Sinh", "Xuyên Không"),
    JOSEI("Josei"),
    LIVE_ACTION("Live action"),
    MAGIC("Magic"),
    MANGA("Manga"),
    MANHUA("Manhua"),
    MANHWA("Manhwa"),
    MARTIAL_ARTS("Martial Arts"),
    MATURE("Mature"),
    MECHA("Mecha"),
    MYSTERY("Mystery"),
    COOKING("Nấu Ăn"),
    ROMANCES("Ngôn Tình"),
    NTR("NTR"),
    ONE_SHOT("One shot"),
    PSYCHOLOGICAL("Psychological"),
    ROMANCE("Romance"),
    SCHOOL_LIFE("School Life"),
    SCI_FI("Sci-fi"),
    SEINEN("Seinen"),
    SHOUJO("Shoujo"),
    SHOUJO_AI("Shoujo Ai"),
    SHOUNEN("Shounen"),
    SHOUNEN_AI("Shounen Ai"),
    SLICE_OF_LIFE("Slice of Life"),
    SMUT("Smut"),
    SOFT_YAOI("Soft Yaoi"),
    SOFT_YURI("Soft Yuri"),
    SPORTS("Sports"),
    SUPERNATURAL("Supernatural"),
    COMIC_MAGAZINE("Tạp chí truyện tranh"),
    TRAGEDY("Tragedy"),
    TRAP_CROSSDRESSING("Trap (Crossdressing)"),
    DETECTIVE("Trinh Thám"),
    SCAN_COMIC("Truyện scan"),
    FAIRY("Tu chân - tu tiên"),
    VIDEO_CLIP("Video Clip"),
    VNCOMIC("VnComic"),
    WEBTOON("Webtoon"),
    YURI("Yuri"),
    ;
    private static final Map<String, Genre> reverseValues;
    private static final String defaultErrorMessage;

    static {
        reverseValues = new HashMap<>();
        Arrays.stream(values()).forEach(genre -> {

            // Many name of genres but point to same enum.
            for (var value: genre.values) {
                reverseValues.put(value, genre);
                reverseValues.put(value.toLowerCase(), genre);
            }
        });

        defaultErrorMessage = "'%s' is not valid genre.Must be one of: " + String.join(", ", reverseValues.keySet());
    }
    private final ArrayList<String> values;

    Genre(String ...values) {
        this.values = new ArrayList<>(values.length);
        Collections.addAll(this.values, values);
    }
    @JsonValue
    public String getValue() {
        return values.get(0);
    }

    @JsonCreator
    public static Genre fromString(String value) {
        if (!reverseValues.containsKey(value.toLowerCase())) {
            throw new EnumParseException(String.format(defaultErrorMessage, value));
        }
        return reverseValues.get(value.toLowerCase());
    }
}
