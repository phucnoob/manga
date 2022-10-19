package uet.ppvan.mangareader.mangas.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Genre {
    SIXTEEN_PLUS("16+"),
    EIGHTEEN_PLUS("18+"),
    ACTION("Action"),
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
    FULL_COLOR("Full màu"),
    GAME("Game"),
    GENDER_BENDER("Gender Bender"),
    HAREM("Harem"),
    HISTORICAL("Historical"),
    HORROR("Horror"),
    ISEKAI("Isekai"),
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
    private static final Map<String, Genre> reverseValuesCaseInsensitive;
    private static final String defaultErrorMessage;

    static {
        reverseValues = new HashMap<>();
        reverseValuesCaseInsensitive = new HashMap<>();
        Arrays.stream(values()).forEach(genre -> {
            reverseValues.put(genre.getValue(), genre);
            reverseValuesCaseInsensitive.put(genre.getValue().toLowerCase(), genre);
        });

        defaultErrorMessage = "'%s' is not valid genre.Must be one of: " + String.join(", ", reverseValues.keySet());
    }
    private final String value;

    Genre(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Genre fromString(String value) {
        if (!reverseValuesCaseInsensitive.containsKey(value.toLowerCase())) {
            throw new EnumParseException(String.format(defaultErrorMessage, value));
        }
        return reverseValuesCaseInsensitive.get(value.toLowerCase());
    }
}
