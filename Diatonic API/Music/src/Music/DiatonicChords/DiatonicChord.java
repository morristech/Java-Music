package Music.DiatonicChords;

import Music.Common.Quality;
import Music.Pitches.Pitch;
import Music.Pitches.PitchClass;

/**
 * Created by Nick on 2016-08-23.
 */
public class DiatonicChord {
    // Based on the number of diatonic chord factors (root to thirteenth)
    public static final int MAX_CHORD_MEMBERS = 7;
    private final Pitch[] chordMembers;
    private ChordType chordType;
    private Quality quality;
    // TODO make this inversion member do something useful
    private Inversion inversion;

    public Pitch[] getChordMembers() {
        return chordMembers;
    }

    public ChordType getChordType() {
        return chordType;
    }

    public Quality getQuality() {
        return quality;
    }

    public Inversion getInversion() {
        return inversion;
    }

    public Pitch getRoot() { return chordMembers[ChordFactor.ROOT.getFactorNumber()]; }
    public Pitch getThird() { return chordMembers[ChordFactor.THIRD.getFactorNumber()]; }
    public Pitch getFifth() { return chordMembers[ChordFactor.FIFTH.getFactorNumber()]; }
    public Pitch getSeventh() { return chordMembers[ChordFactor.SEVENTH.getFactorNumber()]; }
    public Pitch getNinth() { return chordMembers[ChordFactor.NINTH.getFactorNumber()]; }
    public Pitch getEleventh() { return chordMembers[ChordFactor.ELEVENTH.getFactorNumber()]; }
    public Pitch getThirteenth() { return chordMembers[ChordFactor.THIRTEENTH.getFactorNumber()]; }

    private DiatonicChord(Builder builder) {
        chordMembers = builder.chordMembers;
        chordType = ChordType.valueOf(builder.maxChordFactor);
        quality = calculateQuality();
        inversion = builder.inversion;
    }

    // TODO refactor and/or move this calculation to some other class?
    private Quality calculateQuality() {
        Quality quality = Quality.UNKNOWN;

        // Check interval from root to third
        if (getRoot() != null && getThird() != null) {
            int rootPcNumber = getRoot().getPitchClass().getPitchClassNumber();
            int thirdPcNumber = getThird().getPitchClass().getPitchClassNumber();
            int interval = PitchClass.modTwelve(thirdPcNumber - rootPcNumber);
            if (interval == 4)
                quality = Quality.MAJOR;
            else if (interval == 3)
                quality = Quality.MINOR;

            // Now check interval from third to fifth
            if (getFifth() != null) {
                int fifthPcNumber = getFifth().getPitchClass().getPitchClassNumber();
                interval = PitchClass.modTwelve(fifthPcNumber - thirdPcNumber);
                if (quality == Quality.MAJOR) {
                    if (interval == 4)
                        quality = Quality.AUGMENTED;
                    // If it's not augmented, the only other defined choice is major (interval of 3 from third to fifth)
                    else if (interval != 3)
                        quality = Quality.UNKNOWN;
                }
                else if (quality == Quality.MINOR) {
                    if (interval == 3)
                        quality = Quality.DIMINISHED;
                    // If it's not diminished, the only other defined choice is minor (interval of 4 from third to fifth)
                    else if (interval != 4)
                        quality = Quality.UNKNOWN;
                }
            }
        }
        return quality;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("( ");
        for (Pitch pitch : chordMembers) {
            if (pitch != null) {
                sb.append(String.format("%s ", pitch.toString()));
            }
        }
        sb.append(')');
        return sb.toString();
    }

    public static class Builder {
        private final Pitch[] chordMembers  = new Pitch[MAX_CHORD_MEMBERS];
        private Inversion inversion;
        private int maxChordFactor = -1;

        // TODO keep or remove the builder methods that don't parse raw Strings?
        public Builder(String tonic) { this(Pitch.newInstance(tonic)); }
        public Builder(Pitch tonic) { makeChordMember(ChordFactor.ROOT, tonic); }
        public Builder third(String third) { return third(Pitch.newInstance(third)); }
        public Builder third(Pitch third) { return makeChordMember(ChordFactor.THIRD, third); }
        public Builder fifth(String fifth) { return fifth(Pitch.newInstance(fifth)); }
        public Builder fifth(Pitch fifth) { return makeChordMember(ChordFactor.FIFTH, fifth); }
        public Builder seventh(String seventh) { return seventh(Pitch.newInstance(seventh)); }
        public Builder seventh(Pitch seventh) { return makeChordMember(ChordFactor.SEVENTH, seventh); }
        public Builder ninth(String ninth) { return ninth(Pitch.newInstance(ninth)); }
        public Builder ninth(Pitch ninth) { return makeChordMember(ChordFactor.NINTH, ninth); }
        public Builder eleventh(String eleventh) { return eleventh(Pitch.newInstance(eleventh)); }
        public Builder eleventh(Pitch eleventh) { return makeChordMember(ChordFactor.ELEVENTH, eleventh); }
        public Builder thirteenth(String thirteenth) { return thirteenth(Pitch.newInstance(thirteenth)); }
        public Builder thirteenth(Pitch thirteenth) { return makeChordMember(ChordFactor.THIRTEENTH, thirteenth); }
        public Builder inversion(String inversion) {
            this.inversion = Inversion.newInstance(inversion);
            return this;
        }

        private Builder makeChordMember(ChordFactor chordFactor, Pitch pitch) {
            int chordFactorNumber = chordFactor.getFactorNumber();
            chordMembers[chordFactorNumber] = pitch;
            if (chordFactorNumber > maxChordFactor) maxChordFactor = chordFactorNumber;
            return this;
        }
        public DiatonicChord build() {
            return new DiatonicChord(this);
        }
    }
}
