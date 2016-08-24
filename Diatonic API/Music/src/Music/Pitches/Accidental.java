package Music.Pitches;

/**
 * Created by Nick on 2016-08-23.
 */
public enum Accidental {
    DOUBLE_FLAT("bb"),
    FLAT("b"),
    NATURAL(""),
    SHARP("#"),
    DOUBLE_SHARP("x");

    private final String accidentalSymbol;

    public String getAccidentalSymbol() { return accidentalSymbol; }

    Accidental(String accidentalSymbol) {
        this.accidentalSymbol = accidentalSymbol;
    }
}
