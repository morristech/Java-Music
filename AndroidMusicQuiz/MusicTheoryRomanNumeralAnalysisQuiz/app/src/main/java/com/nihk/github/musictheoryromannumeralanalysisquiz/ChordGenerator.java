package com.nihk.github.musictheoryromannumeralanalysisquiz;

/*
* TODO add applied chords and modal mixture...somehow!
* major: bVI, bVII, bIII, iv, iihalfdim7.  minor: IV (confused with V / [b]VII ?)
* note: the major modal mixture should probably only be for triads, except iihalfdim7..start with just triads and avoid iihalfdim7 for now
*/

public class ChordGenerator {
    protected String[] pitchClassesArray, accidentalsArray,
            modeArray, romanNumeralsMajorArray,
            romanNumeralsMinorArray, triadicInversions,
            seventhInversions, appliedsMajor, appliedsMinor, augmentedSixths;
    protected String[][][] keyNames;
    protected String[][][][] majorKeys, minorKeys;
    protected String[][][][][] chords; //chords[mode][accidental type][key][chord (and its size)][pitch class], chords[2][4][8][7][3 or 4]
    public static final int NUM_PITCH_CLASSES = 7, //the number of pitch classes in a diatonic collection
            THIRD_INTERVAL = 2, //though in music a third implies an interval of 3, it's actually 2
            START_C_MAJOR = 0, //to start looping through major keys built from pitchClassesArray starting on index 0
            START_A_MINOR = 5, //to start looping through minor keys built from pitchClassesArray starting on index 5
            ASC_FIFTHS = 4, //looping through pitchClassesArray by ascending fifths means a new key will be 4 indices higher
                            //than the previous. this also implies sharp accidentals will accumulate.
            ASC_FOURTHS = 3,//looping through pitchClassesArray by ascending fourths means a new key will be 3 indices higher
                            //than the previous. this also implies flat accidentals will accumulate. this is the same as
                            //descending fifths effectively.
            MAJOR_MODE = 0, //in modeArray index 0 means major mode
            MINOR_MODE = 1, //in modeArray index 1 means minor mode
            TRIAD = 3,
            SEVENTH_CHORD = 4;

    public ChordGenerator() {
        pitchClassesArray = new String[] {"C", "D", "E", "F", "G", "A", "B"};
        accidentalsArray = new String[] {"", "#", "b"}; //empty in place of no accidental
        modeArray = new String[] {"-major", "-minor"};
        romanNumeralsMajorArray = new String[] {"I", "ii", "iii", "IV", "V", "vi", "vii\u00B0"};
        romanNumeralsMinorArray = new String[] {"i", "ii\u00B0", "III", "iv", "v", "VI", "VII"}; //uppercase V currently not present
        triadicInversions = new String[] {"", "6", "6/4"}; //I used empty string for a 5/3 chord
        seventhInversions = new String[] {"7", "6/5", "4/3", "4/2"};
        appliedsMajor = new String[] {" / ii", " / iii", " / IV", " / V", " / vi"}; //these major/minor applied arrays aren't
        appliedsMinor = new String[] {" / III", " / iv", " / V", " / VI", " / VII"}; //in synch order; will that be an issue?
        augmentedSixths = new String[] {"It6", "Fr4/3", "Ger6/5", "It\u00B03", "Fr\u00B03", "Ger\u00B03"};
            //all VII 7th chords in minor will be / III and require no accidentals

        //Initialize music quiz variables.
        //generate all possible keys (without double-accidentals)
        makeKeyNames();

        //there is much redundancy in triads/seventh chords generated, because keys like C-major,
        //G-major, D-minor, etc, all will have a C-major chord, for example. This is okay, however,
        //because the program associates keys to chords and it's a convenience that this way
        //it already knows that association automatically.

        //makeChords(true==sharps false==flats, true==major false==minor, true==triad false==7th chord);
        majorKeys = new String[][][][] {
                makeChords(true, true, true), //sharp major key triads
                makeChords(false, true, true), //flat major key triads
                makeChords(true, true, false), //sharp major key 7ths
                makeChords(false, true, false) //flat major key 7ths
        }; //getting redundancy with key of C-major. probably this is OK
        minorKeys = new String[][][][] {
                makeChords(true, false, true), //sharp minor key triads
                makeChords(false, false, true), //flat minor key triads
                makeChords(true, false, false), //sharp minor key 7ths
                makeChords(false, false, false) //flat minor key 7ths
        }; //getting redundancy with key of A-minor. probably this is OK
        chords = new String[][][][][] {majorKeys, minorKeys};
    }
    //accidental == true means use sharps. accidental == false means use flats.
    //mode == true is for major keys. mode == false is for minor keys.
    //chordSize == true is for triads. chordSize == false is for 7th chords.
    public String[][][] makeChords(boolean accidental, boolean mode, boolean chordSize) { //I don't want to phase out the magic numbers associated with chordSize
        String[][][] arr = new String[8][7][chordSize ? 3 : 4]; //8 keys, 7 chords per key, 3 or 4 pitch classes per chord.
        int index = mode ? START_C_MAJOR : START_A_MINOR;	//why 8 keys? because Cmajor to C#major, Aminor to Abminor, etc., is each 8 keys total.
        //mode ? 0 : 5, that means if major key, start the looping on C, if minor key, start on A(minor)
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                for (int k = 0; k < arr[i][j].length; k++) {
                    arr[i][j][k] = pitchClassesArray[index].concat(getAccidental(i, index, accidental));
                    index = (index + 2) % NUM_PITCH_CLASSES; //interval of 2 is a musical interval of a third
                } //for reference: pitchClassesArray = {"C", "D", "E", "F", "G", "A", "B"};
                index = (index + (chordSize ? 2 : 0)) % NUM_PITCH_CLASSES; //chordSize ? 2 : 0 --> if I'm making triads, 2, if seventh chords, 0. this
            }										//is just how traversing the pitch-class array in mod 7 worked out, because
            //C-E-G are intervals of 2 from C to G, but D-F-A (the next chord) is an interval
            //of 4 from G to D (2 + 2 = 4; i.e. 2 from the k loop, and 2 from the j loop)
            //but C-E-G-B is just an interval of 2 from B to D for D-F-A-C (2 + 0 = 2; i.e.
            //2 from the k loop, 0 from the j loop
            index = (index + (accidental ? ASC_FIFTHS : ASC_FOURTHS)) % NUM_PITCH_CLASSES; //if i'm using sharp accidentals, that means i'm ascending by fifths (4 ints)
        }								//but if I'm using flats, that means ascending fourths (3 ints)
        return arr;
    }
    public void makeKeyNames() {
        keyNames = new String[2][2][8]; //keyNames[mode][accidentalType][keyName]; 2 modes (major/minor) 2 accidentals(sharp/flat), 8 keys without doubleaccidentals
        for (int i = 0; i < keyNames.length; i++) {
            for (int j = 0; j < keyNames[i].length; j++) {
                for (int p = 0, q = (i == 0 ? START_C_MAJOR : START_A_MINOR); //i == 1 means get minor key names starting from A-minor
                     p < keyNames[i][j].length;
                     p++, q = (q + (j == 0 ? ASC_FIFTHS : ASC_FOURTHS)) % NUM_PITCH_CLASSES) { //j == 1 means use ascending fourths (flats)
                    keyNames[i][j][p] = pitchClassesArray[q].concat(
                            getAccidental(p, q, (j == 0 ? true : false)).concat( //if 'j' is in the ascending fifths index, it uses sharps. ascending fourths uses flats
                                    modeArray[i == 0 ? MAJOR_MODE : MINOR_MODE])); //if 'i' is in the major keys index, use -major from modeArray. else use -minor
                }
            }
        }
    }

    /*
        getAccidental:
        The value of i refers to the number of accidentals in the key, because
        the looping starts at i = 0 where i is the first key, C-major/A-minor, which
        has zero accidentals. e.g. i = 4 would be either E-major/C#-minor or
        Ab-major/F-minor, both have 4 accidentals. This method will just cascade down
        applying accidentals to each appropriate note within the 'i' related key.
    */
    //accidental == true means return sharps. accidental == false means return flats.
    public String getAccidental(int i, int index, boolean accidental) { //{"", "#", "b"};
        String acc = accidentalsArray[0]; //this counts for case 0 of the switch below
        switch (i) {
            case 7: if (accidental && index == 6) { //B#
                acc = accidentalsArray[1];
            } else if (!accidental && index == 3) { //Fb
                acc = accidentalsArray[2];
            }
                //fall through
            case 6: if (accidental && index == 2) { //E#
                acc = accidentalsArray[1];
            } else if (!accidental && index == 0) { //Cb
                acc = accidentalsArray[2];
            }
                //fall through
            case 5: if (accidental && index == 5) { //A#
                acc = accidentalsArray[1];
            } else if (!accidental && index == 4) { //Gb
                acc = accidentalsArray[2];
            }
                //fall through
            case 4: if (accidental && index == 1) { //D#
                acc = accidentalsArray[1];
            } else if (!accidental && index == 1) { //Db
                acc = accidentalsArray[2];
            }
                //fall through
            case 3: if (accidental && index == 4) { //G#
                acc = accidentalsArray[1];
            } else if (!accidental && index == 5) { //Ab
                acc = accidentalsArray[2];
            }
                //fall through
            case 2: if (accidental && index == 0) { //C#
                acc = accidentalsArray[1];
            } else if (!accidental && index == 2) { //Eb
                acc = accidentalsArray[2];
            }
                //fall through
            case 1: if (accidental && index == 3) { //F#
                acc = accidentalsArray[1];
            } else if (!accidental && index == 6) { //Bb
                acc = accidentalsArray[2];
            }
                break;
            default: acc = accidentalsArray[0];
                break;
        }
        return acc;
    }
}