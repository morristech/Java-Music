package com.nihk.github.musictheoryromannumeralanalysisquiz;

import java.util.ArrayList;
import java.util.List;

public class WrongAnswerGenerator {
    protected List<String> wrongAnswerPoolSimpleChords, wrongAnswerPoolComplexChords;
    protected ChordGenerator cgWAG;
    protected String[] modalMixtureChords;
    protected String dim, halfDim;
    public WrongAnswerGenerator() {
        wrongAnswerPoolSimpleChords = new ArrayList<String>();
        wrongAnswerPoolComplexChords = new ArrayList<String>();
        //only modally mixed chords that have alterations to the RN (i.e. adds an accidental to the RN)
        modalMixtureChords = new String[] {"bII", "bIII", "bVI", "bVII"};
        dim = "\u00B0";
        halfDim = "\u00F8";
        cgWAG = new ChordGenerator();
        makeWrongAnswerPool();
    }
    public void makeWrongAnswerPool() {
        //simple chords (i.e. no added accidentals, just chords in key)
        for (String majorRN: cgWAG.romanNumeralsMajorArray) {
            for (String triadInversion: cgWAG.triadicInversions) {
                wrongAnswerPoolSimpleChords.add(majorRN.concat(triadInversion));
            }
            for (String seventhInversion: cgWAG.seventhInversions) {
                if (majorRN.equals(cgWAG.romanNumeralsMajorArray[6])) {
                    majorRN = majorRN.replace(dim, halfDim);
                }
                wrongAnswerPoolSimpleChords.add(majorRN.concat(seventhInversion));
            }
        }
        for (String minorRN: cgWAG.romanNumeralsMinorArray) {
            for (String triadInversion: cgWAG.triadicInversions) {
                wrongAnswerPoolSimpleChords.add(minorRN.concat(triadInversion));
            }
            for (String seventhInversion: cgWAG.seventhInversions) {
                if (minorRN.equals(cgWAG.romanNumeralsMinorArray[1])) {
                    minorRN = minorRN.replace(dim, halfDim);
                }
                wrongAnswerPoolSimpleChords.add(minorRN.concat(seventhInversion));
            }
        }

        //complex chords (i.e. modal mixture, applied chords, et al)

        //Applied chords. always V and always 7th chords
        for (String inversion: cgWAG.seventhInversions) {
            for (String applied: cgWAG.appliedsMinor) {
                wrongAnswerPoolComplexChords.add(cgWAG.romanNumeralsMajorArray[4].concat(inversion).concat(applied));
            }
            for (String applied: cgWAG.appliedsMajor) {
                wrongAnswerPoolComplexChords.add(cgWAG.romanNumeralsMajorArray[4].concat(inversion).concat(applied));
            }
        }

        //modally mixed chords
        for (String modallyMixedChord: modalMixtureChords) {
            for (String triadInversion: cgWAG.triadicInversions) {
                wrongAnswerPoolComplexChords.add(modallyMixedChord.concat(triadInversion));
            }
            for (String seventhInversion: cgWAG.seventhInversions) {
                wrongAnswerPoolComplexChords.add(modallyMixedChord.concat(seventhInversion));
            }
        }

        //add later their inversions?
        /*
        wrongAnswerPoolComplexChords.add("Ger6/5");
        wrongAnswerPoolComplexChords.add("It6");
        wrongAnswerPoolComplexChords.add("Fr4/3");
        */

    }
    //1/5 chance of a complex chord; I want to emphasize simple chords
    public String getRandomWrongAnswer() {
        if (Math.random() > 0.2) {
            return wrongAnswerPoolSimpleChords.get((int)(Math.random() * wrongAnswerPoolSimpleChords.size()));
        } else {
            return wrongAnswerPoolComplexChords.get((int)(Math.random() * wrongAnswerPoolComplexChords.size()));
        }
    }
}
