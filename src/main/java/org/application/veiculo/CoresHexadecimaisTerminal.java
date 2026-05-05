package org.application.veiculo;

class CoresHexadecimaisTerminal {
    public static final String RESET = "\033[0m";

    public static String corHexadecimal(int r, int g, int b) {
        return "\033[38;2;" + r + ";" + g + ";" + b + "m";
    }

    public static final String WHITE            = corHexadecimal(255, 250, 250); // 1
    public static final String LEMON_CHIFFON    = corHexadecimal(255, 250, 205); // 2
    public static final String ROYAL_BLUE       = corHexadecimal(65, 105, 225); // 3
    public static final String AQUA_MARINE      = corHexadecimal(127, 255, 212); // 4
    public static final String GOLD             = corHexadecimal(255, 215, 0); // 5
    public static final String FOREST_GREEN     = corHexadecimal(34, 139, 34); // 6
    public static final String SPRING_GREEN     = corHexadecimal(0, 255, 127); // 7
    public static final String CYAN             = corHexadecimal(0, 255, 255); // 8
    public static final String BEIGE            = corHexadecimal(245, 245, 220); // 9
    public static final String FIRE_BRICK       = corHexadecimal(178, 34, 34); // 10
    public static final String RED              = corHexadecimal(255, 0, 0); // 11
    public static final String DARK_VIOLET      = corHexadecimal(148, 0, 211); // 12
    public static final String DEEP_SKY_BLUE    = corHexadecimal(0, 191, 255); // 13
    public static final String DODGER_BLUE      = corHexadecimal(24, 116, 205); // 14
    public static final String SLATE_BLUE       = corHexadecimal(71, 60, 139); // 15
    public static final String TOMATO           = corHexadecimal(255, 99, 71); // 12
}
