package Music.DiatonicChords;

/**
 * Created by Nick on 2016-08-23.
 */
public enum ChordFactor {
    ROOT(0),
    THIRD(1),
    FIFTH(2),
    SEVENTH(3),
    NINTH(4),
    ELEVENTH(5),
    THIRTEENTH(6);

    private final int factorNumber;

    public int getFactorNumber() { return factorNumber; }

    ChordFactor(int factorNumber) {
        this.factorNumber = factorNumber;
    }
}
