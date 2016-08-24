package Music;

import Music.DiatonicChords.DiatonicChord;
import Music.Collections.CustomDiatonicCollection;
import Music.Pitches.Pitch;

/**
 * Created by Nick on 2016-08-21.
 */
public class MusicDriver {
    public static void main(String[] args) {
        DiatonicChord diatonicChord = new DiatonicChord.Builder("C4")
                .third("Eb4")
                .fifth("Gb4")
                .seventh("Bb4")
                .ninth("D5")
                .thirteenth("A5")
                .inversion("first")
                .build();

        System.out.println(diatonicChord);
        System.out.println();
        System.out.printf("This chord is of type: %s%n", diatonicChord.getChordType());
        System.out.printf("Its underlying chord quality is: %s%n", diatonicChord.getQuality());
        System.out.printf("Its inversion is: %s%n", diatonicChord.getInversion());
        System.out.println();

        for (Pitch pitch : diatonicChord.getChordMembers()) {
            if (pitch != null) {
                System.out.printf("%s is %s%n", pitch, pitch.getPitchFrequency());
            }
        }

        System.out.println("\nA custom diatonic collection:");
        CustomDiatonicCollection customDiatonicCollection = new CustomDiatonicCollection("Cx", "D", "Ebb", "F", "G", "Ab", "Bb");
        System.out.println(customDiatonicCollection);

        /*  Console output:

            ( C4 Eb4 Gb4 Bb4 D5 A5 )

            This chord is of type: THIRTEENTH
            Its underlying chord quality is: DIMINISHED
            Its inversion is: FIRST

            C4 is 261.626 Hz
            Eb4 is 311.127 Hz
            Gb4 is 369.994 Hz
            Bb4 is 466.164 Hz
            D5 is 587.330 Hz
            A5 is 880.000 Hz

            A custom diatonic collection:
            ( Cx D Ebb F G Ab Bb )

         */
    }
}
