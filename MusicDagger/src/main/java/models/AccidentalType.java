package models;

public enum AccidentalType {

    FLAT, NATURAL, SHARP;

    public static String toString(AccidentalType accidentalType) {
        switch (accidentalType) {
            case FLAT: return "b";
            case NATURAL: return "";
            case SHARP: return "#";
            default: throw new IllegalArgumentException("Unknown AccidentalType");
        }
    }
}
