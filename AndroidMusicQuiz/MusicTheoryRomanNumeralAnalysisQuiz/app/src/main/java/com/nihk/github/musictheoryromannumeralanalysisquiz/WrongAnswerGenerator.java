package com.nihk.github.musictheoryromannumeralanalysisquiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WrongAnswerGenerator {
    protected List<String> wrongAnswerPoolSimpleChordsTriads, wrongAnswerPoolModalMixtureTriads,
            wrongAnswerPoolSimpleChordsSevenths, wrongAnswerPoolAppliedChordsSevenths;
    protected ChordGenerator cgWAG;
    protected String[] specialComplexChordsTriads, specialComplexChordsSevenths;
    protected String dim, halfDim;
    protected Random rand;
    public WrongAnswerGenerator() {
        dim = "\u00B0";
        halfDim = "\u00F8";
        wrongAnswerPoolSimpleChordsTriads = new ArrayList<String>();
        wrongAnswerPoolSimpleChordsSevenths = new ArrayList<String>();
        wrongAnswerPoolModalMixtureTriads = new ArrayList<String>();
        wrongAnswerPoolAppliedChordsSevenths = new ArrayList<String>();
        //only modally mixed chords that have alterations to the RN (i.e. adds an accidental to the RN)
        specialComplexChordsTriads = new String[] {"bII", "bIII", "bVI", "bVII"};
        specialComplexChordsSevenths = new String[] {"V+", "V-"}; //add more?
        cgWAG = new ChordGenerator();
        rand = new Random();
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
                wrongAnswerPoolAppliedChordsSevenths.add(cgWAG.romanNumeralsMajorArray[4].concat(inversion).concat(applied));
            }
            for (String applied: cgWAG.appliedsMajor) {
                wrongAnswerPoolAppliedChordsSevenths.add(cgWAG.romanNumeralsMajorArray[4].concat(inversion).concat(applied));
            }
        }

        //modally mixed chords
        for (String modallyMixedChord: specialComplexChordsTriads) {
            for (String triadInversion: cgWAG.triadicInversions) {
                wrongAnswerPoolModalMixtureTriads.add(modallyMixedChord.concat(triadInversion));
            }
        }
        for (String triadicInversion: cgWAG.triadicInversions) {
            wrongAnswerPoolModalMixtureTriads.add(cgWAG.romanNumeralsMajorArray[4].concat(triadicInversion));
        }
        for (String seventhInversion: cgWAG.seventhInversions) {
            wrongAnswerPoolModalMixtureTriads.add(cgWAG.romanNumeralsMajorArray[4].concat(seventhInversion));
        }
        for (String extendedDominant: specialComplexChordsSevenths) {
            for (String seventhInversion: cgWAG.seventhInversions) {
                wrongAnswerPoolAppliedChordsSevenths.add(extendedDominant.concat(seventhInversion));
            }
        }

        //augmented 6ths
        for (String aug: cgWAG.augmentedSixths) {
            wrongAnswerPoolAppliedChordsSevenths.add(aug);
        }
    }
    //rand.nextBoolean() because I want there to be some simple chords. otherwise i'll miss out on chords like "iv" in major which isn't technically a
    //non-simple chord. modal mixture array doesn't have those covert non-simple chords.
    //noAccidentalsAdded because if modalmixture or appliedchords booleans are true but its a chord that didn't receive any accidentals because there
    //was no opportunity to, it'll be just a regular chord. I want the wrong answers to then be simple wrong answers to make the right answer less obvious
    public String getRandomWrongAnswer(int triadOrSeventh, boolean isModalMixture, boolean isAppliedChords, boolean noAccidentalsAdded) {
        if (rand.nextBoolean() && !noAccidentalsAdded && (isModalMixture || isAppliedChords)) {
            if (triadOrSeventh == 3 && isModalMixture) {
                return wrongAnswerPoolModalMixtureTriads.get((int)(Math.random() * wrongAnswerPoolModalMixtureTriads.size()));
            } else {
                return wrongAnswerPoolAppliedChordsSevenths.get((int)(Math.random() * wrongAnswerPoolAppliedChordsSevenths.size()));
            }

        } else {
            if (triadOrSeventh == 3) {
                return wrongAnswerPoolSimpleChordsTriads.get((int)(Math.random() * wrongAnswerPoolSimpleChordsTriads.size()));
            } else {
                return wrongAnswerPoolSimpleChordsSevenths.get((int)(Math.random() * wrongAnswerPoolSimpleChordsSevenths.size()));
            }
        }
    }
}
