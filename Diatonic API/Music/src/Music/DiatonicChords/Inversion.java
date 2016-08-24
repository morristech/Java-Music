package Music.DiatonicChords;

/**
 * Created by Nick on 2016-08-24.
 */
public class Inversion {
    private enum Voicing {
        // Despite extended chords like thirteenths existing (and thus permitting fourth, fifth inversions),
        // 99% of chords fall within these four categories
        ROOT_POSITION, FIRST, SECOND, THIRD
    }

    private Voicing voicing;

    public Voicing getVoicing() {
        return voicing;
    }

    private Inversion() {}

    public static Inversion newInstance(String inversionVoicing) {
        Inversion inversion = new Inversion();
        inversion.voicing = parseInversionVoicing(inversionVoicing);
        return inversion;
    }

    private static Voicing parseInversionVoicing(String inversionVoicing) {
        switch (inversionVoicing.toLowerCase()) {
            case "root position": return Voicing.ROOT_POSITION;
            case "first": return Voicing.FIRST;
            case "second": return Voicing.SECOND;
            case "third": return Voicing.THIRD;
            default: throw new RuntimeException("Unkniwn inversion voicing type");
        }
    }

    @Override
    public String toString() {
        return voicing.toString();
    }
}
