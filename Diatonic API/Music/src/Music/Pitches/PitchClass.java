package Music.Pitches;

/**
 * Created by Nick on 2016-08-24.
 */
public class PitchClass {
    public static final int NUM_PITCH_CLASSES = 12;
    private PitchLetter pitchLetter;
    private Accidental accidental;
    private int pitchClassNumber;

    public PitchLetter getPitchLetter() {
        return pitchLetter;
    }

    public Accidental getAccidental() {
        return accidental;
    }

    public int getPitchClassNumber() {
        return pitchClassNumber;
    }

    private PitchClass() {}

    public static PitchClass newInstance(PitchLetter pitchLetter, Accidental accidental) {
        PitchClass pc = new PitchClass();
        pc.pitchLetter = pitchLetter;
        pc.accidental = accidental;
        pc.pitchClassNumber = modTwelve(pc.calculatePitchLetterNumber() + pc.calculateAccidentalNumber());
        return pc;
    }

    public static PitchClass newInstance(String pitchClass) {
        return parsePitchClass(pitchClass);
    }

    private static PitchClass parsePitchClass(String input) {
        int inputLen = input.length();
        char pitchLetter = input.charAt(0);

        // If the last character was a digit then the octave was provided in the input, so ignore that here
        int lastAccidentalIndex = Character.isDigit(input.charAt(inputLen - 1)) ? inputLen - 1 : inputLen;
        String accidental = input.substring(1, lastAccidentalIndex);

        return PitchClass.newInstance(parsePitchLetter(pitchLetter), parseAccidental(accidental));
    }

    // TODO can this be reduced from 4 methods of switches to 2?
    private static PitchLetter parsePitchLetter(char pc) {
        Character character = Character.toUpperCase(pc);
        switch (character) {
            case 'C': return PitchLetter.C;
            case 'D': return PitchLetter.D;
            case 'E': return PitchLetter.E;
            case 'F': return PitchLetter.F;
            case 'G': return PitchLetter.G;
            case 'A': return PitchLetter.A;
            case 'B': return PitchLetter.B;
            default: throw new PitchClassParseException("Couldn't parse the PitchLetter");
        }
    }

    private static Accidental parseAccidental(String acc) {
        switch (acc.toLowerCase()) {
            case "bb": return Accidental.DOUBLE_FLAT;
            case "b": return Accidental.FLAT;
            case "": return Accidental.NATURAL;
            case "#": return Accidental.SHARP;
            case "x": return Accidental.DOUBLE_SHARP;
            default: throw new PitchClassParseException("Couldn't parse the Accidental");
        }
    }

    private int calculatePitchLetterNumber() {
        switch (pitchLetter) {
            case C: return 0;
            case D: return 2;
            case E: return 4;
            case F: return 5;
            case G: return 7;
            case A: return 9;
            case B: return 11;
            default: throw new RuntimeException("Error getting pitch letter number value");
        }
    }

    private int calculateAccidentalNumber() {
        switch (accidental) {
            case DOUBLE_FLAT: return -2;
            case FLAT: return -1;
            case NATURAL: return 0;
            case SHARP: return 1;
            case DOUBLE_SHARP: return 2;
            default: throw new RuntimeException("Error getting accidental number value");
        }
    }

    public static int modTwelve(int n) {
        return (n + 12) % 12;
    }

    private static class PitchClassParseException extends RuntimeException {
        public PitchClassParseException(String message) {
            super(message);
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s", pitchLetter, accidental.getAccidentalSymbol());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PitchClass that = (PitchClass) o;

        if (pitchClassNumber != that.pitchClassNumber) return false;
        if (pitchLetter != that.pitchLetter) return false;
        return accidental == that.accidental;

    }

    @Override
    public int hashCode() {
        int result = pitchLetter != null ? pitchLetter.hashCode() : 0;
        result = 31 * result + (accidental != null ? accidental.hashCode() : 0);
        result = 31 * result + pitchClassNumber;
        return result;
    }
}
