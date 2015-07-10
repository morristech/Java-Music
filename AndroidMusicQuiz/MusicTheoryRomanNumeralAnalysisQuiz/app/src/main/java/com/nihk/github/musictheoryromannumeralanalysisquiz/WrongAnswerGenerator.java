package com.nihk.github.musictheoryromannumeralanalysisquiz;

import java.util.ArrayList;
import java.util.List;

public class WrongAnswerGenerator {
    protected List<String> wrongAnswerPoolSimpleChordsTriads, wrongAnswerPoolComplexChordsTriads,
            wrongAnswerPoolSimpleChordsSevenths, wrongAnswerPoolComplexChordsSevenths;
    protected ChordGenerator cgWAG;
    protected String[] specialComplexChords;
    protected String dim, halfDim;
    public WrongAnswerGenerator() {
        wrongAnswerPoolSimpleChordsTriads = new ArrayList<String>();
        wrongAnswerPoolComplexChordsTriads = new ArrayList<String>();
        wrongAnswerPoolSimpleChordsSevenths = new ArrayList<String>();
        wrongAnswerPoolComplexChordsSevenths = new ArrayList<String>();
        //only modally mixed chords that have alterations to the RN (i.e. adds an accidental to the RN)
        specialComplexChords = new String[] {"bII", "bIII", "bVI", "bVII", "V+", "V-"};
        dim = "\u00B0";
        halfDim = "\u00F8";
        cgWAG = new ChordGenerator();
        makeWrongAnswerPool();
    }
    public void makeWrongAnswerPool() {
        //simple chords (i.e. no added accidentals, just chords in key)
        for (String majorRN: cgWAG.romanNumeralsMajorArray) {
            for (String triadInversion: cgWAG.triadicInversions) {
                wrongAnswerPoolSimpleChordsTriads.add(majorRN.concat(triadInversion));
            }
            for (String seventhInversion: cgWAG.seventhInversions) {
                if (majorRN.equals(cgWAG.romanNumeralsMajorArray[6])) {
                    majorRN = majorRN.replace(dim, halfDim);
                }
                wrongAnswerPoolSimpleChordsSevenths.add(majorRN.concat(seventhInversion));
            }
        }
        for (String minorRN: cgWAG.romanNumeralsMinorArray) {
            for (String triadInversion: cgWAG.triadicInversions) {
                wrongAnswerPoolSimpleChordsTriads.add(minorRN.concat(triadInversion));
            }
            for (String seventhInversion: cgWAG.seventhInversions) {
                if (minorRN.equals(cgWAG.romanNumeralsMinorArray[1])) {
                    minorRN = minorRN.replace(dim, halfDim);
                }
                wrongAnswerPoolSimpleChordsSevenths.add(minorRN.concat(seventhInversion));
            }
        }

        //complex chords (i.e. modal mixture, applied chords, et al)

        //Applied chords. always V and always 7th chords
        for (String inversion: cgWAG.seventhInversions) {
            for (String applied: cgWAG.appliedsMinor) {
                wrongAnswerPoolComplexChordsSevenths.add(cgWAG.romanNumeralsMajorArray[4].concat(inversion).concat(applied));
            }
            for (String applied: cgWAG.appliedsMajor) {
                wrongAnswerPoolComplexChordsSevenths.add(cgWAG.romanNumeralsMajorArray[4].concat(inversion).concat(applied));
            }
        }

        //modally mixed chords
        for (String modallyMixedChord: specialComplexChords) {
            for (String triadInversion: cgWAG.triadicInversions) {
                wrongAnswerPoolComplexChordsTriads.add(modallyMixedChord.concat(triadInversion));
            }
            for (String seventhInversion: cgWAG.seventhInversions) {
                wrongAnswerPoolComplexChordsSevenths.add(modallyMixedChord.concat(seventhInversion));
            }
        }

        //add later their inversions?
        /*
        wrongAnswerPoolComplexChordsTriads.add("Ger6/5");
        wrongAnswerPoolComplexChordsTriads.add("It6");
        wrongAnswerPoolComplexChordsTriads.add("Fr4/3");
        */

    }
    //1/5 chance of a complex chord; I want to emphasize simple chords. change this later to a boolean for the checkbox of applieds enabled
    public String getRandomWrongAnswer(int triadOrSeventh) {
        if (Math.random() > 0.2) {
            if (triadOrSeventh == 3) {
                return wrongAnswerPoolSimpleChordsTriads.get((int) (Math.random() * wrongAnswerPoolSimpleChordsTriads.size()));
            } else {
                return wrongAnswerPoolSimpleChordsSevenths.get((int) (Math.random() * wrongAnswerPoolSimpleChordsSevenths.size()));
            }
        } else {
            if (triadOrSeventh == 3) {
                return wrongAnswerPoolComplexChordsTriads.get((int) (Math.random() * wrongAnswerPoolComplexChordsTriads.size()));
            } else {
                return wrongAnswerPoolComplexChordsSevenths.get((int) (Math.random() * wrongAnswerPoolComplexChordsSevenths.size()));
            }
        }
    }
}
