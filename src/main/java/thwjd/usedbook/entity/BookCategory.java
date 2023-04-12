package thwjd.usedbook.entity;


import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BookCategory{
    NOVEL("소설", "novel"),
    HUMANITIES("인문", "humanities"),
    CARTOON("만화", "cartoon");

    private final String name;
    private final String lowerCase;
    private BookCategory(String name, String lowerCase) {
        this.name = name;
        this.lowerCase = lowerCase;
    }
    public String getName() { return name; }
    public String getLowerCase() { return lowerCase; }





//    private static final Map<String, BookCategory> byName =
//            Stream.of(values()).collect(Collectors.toMap(BookCategory::getName, Function.identity()));
//
//    public static BookCategory valueOfName(String name) {
//        return byName.get(name);
//    }
//
//    private static final Map<String, BookCategory> byLowerCase =
//            Stream.of(values()).collect(Collectors.toMap(BookCategory::getLowerCase, Function.identity()));
//
//    public static BookCategory valueOfLowerCase(String eng) {
//        return byLowerCase.get(eng);
//    }

}
