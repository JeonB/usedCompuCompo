package jeonb.usedcompu.entity;


public enum CompuCategory {
    GRAPHIC("그래픽카드", "graphic"),
    CPU("CPU", "cpu"),
    RAM("RAM", "ram");

    private final String name;
    private final String lowerCase;
    private CompuCategory(String name, String lowerCase) {
        this.name = name;
        this.lowerCase = lowerCase;
    }
    public String getName() { return name; }
    public String getLowerCase() { return lowerCase; }





//    private static final Map<String, CompuCategory> byName =
//            Stream.of(values()).collect(Collectors.toMap(CompuCategory::getName, Function.identity()));
//
//    public static CompuCategory valueOfName(String name) {
//        return byName.get(name);
//    }
//
//    private static final Map<String, CompuCategory> byLowerCase =
//            Stream.of(values()).collect(Collectors.toMap(CompuCategory::getLowerCase, Function.identity()));
//
//    public static CompuCategory valueOfLowerCase(String eng) {
//        return byLowerCase.get(eng);
//    }

}
