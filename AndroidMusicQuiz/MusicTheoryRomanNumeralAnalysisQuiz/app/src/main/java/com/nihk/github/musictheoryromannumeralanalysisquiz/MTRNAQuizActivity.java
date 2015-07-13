//When I made the modal mixture/applied chords, a lot of them exclude certain keys with many accidentals
//This is because it excludes every chord that would require a double sharp or double flat.  The sole reason
//for this is that the font (MusiQwik) doesn't support these double accidentals!

package com.nihk.github.musictheoryromannumeralanalysisquiz;

/*
* TODO change main fonts into something more monospaced and all-caps. Find a font that supports the superscripted minus/plus unicode symbols
* TODO also change the keysig font, it's ugly as sin with the unicode sharps/flats. basically eradicate neuton entirely
* TODO another vertical line of flats, naturals, and sharps; if j - 1 is visible and j > 0 then switch off notehead j-1 and turn on LeftNotehead j-1
* TODO why is my LG phone using the default layout and not 320dp?
* TODO if a new music font doesnt work out, replace + and - with aug and dim ?
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MTRNAQuizActivity extends ActionBarActivity {
    private Button[] answerButtonArray;
    private TextView aTextView, bTextView, cTextView, dTextView,
            keyTextView, scorePointsTextView, trebleClef, bassClef, altoClef;
    private TextView[] qTextViews, noteheads, noteheadsR;
    private TextView[][] noteheadsAccidentals;
    private TextView[][][] keySigAccidentals;
    private boolean stopCounting, isTrebleClef, isBassClef,
            isAppliedChords, isModalMixtureChords, isAlteredChords, noAccidentalsAdded;
    private String rightAnswer, wrongAnswer, checkMark, xMark,
            dim, halfDim, colonSpace, bigII, plusSign, minusSign;
    private String[] wrongAnswerArray, displayedRandomChord;
    private int rightAnswerIndex, correct, total, pink, green, numKeySigAccidentalsInt;
    public static final int ROOT = 0, THIRD = 1, FIFTH = 2, SEVENTH = 3,
        NATURAL_OR_NOTHING = 0, SHARP = 1, FLAT = 2;
    private int[] verticalIndices, shuffledChordOrderOfIntervals;
    private Random rand;
    private SharedPreferences SP;
    private Intent settingsIntent;
    private ChordGenerator cg;
    private WrongAnswerGenerator wag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtrnaquiz);

        settingsIntent = new Intent(this, MyPreferencesActivity.class);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //declarations and/or initializations of member and non-member vars
        Button answerButton1 = (Button)findViewById(R.id.answerButton1);
        Button answerButton2 = (Button)findViewById(R.id.answerButton2);
        Button answerButton3 = (Button)findViewById(R.id.answerButton3);
        Button answerButton4 = (Button)findViewById(R.id.answerButton4);

        answerButtonArray = new Button[] {answerButton1,
                answerButton2, answerButton3, answerButton4
        };
        wrongAnswerArray = new String[answerButtonArray.length - 1]; //4 buttons, so (4 - 1) wrong answers

        aTextView = (TextView)findViewById(R.id.aTextView);
        bTextView = (TextView)findViewById(R.id.bTextView);
        cTextView = (TextView)findViewById(R.id.cTextView);
        dTextView = (TextView)findViewById(R.id.dTextView);

        checkMark = getString(R.string.CheckCharacter);
        xMark = getString(R.string.XCharacter);
        colonSpace = ": ";
        bigII = "II";
       // plusSign = "\u207A";
      //  minusSign = "\u207B";
        plusSign = "+";
        minusSign = "-";
        rand = new Random();

        qTextViews = new TextView[] {
                aTextView, bTextView, cTextView, dTextView
        };

        Button generateChordButton = (Button)findViewById(R.id.generateChordButton);
        Button refreshButton = (Button)findViewById(R.id.refreshButton);
        Button settingsButton = (Button)findViewById(R.id.settingsButton);

        keyTextView = (TextView)findViewById(R.id.keyTextView);
        scorePointsTextView = (TextView)findViewById(R.id.scorePointsTextView);

        trebleClef = (TextView)findViewById(R.id.trebleClef);
        bassClef = (TextView)findViewById(R.id.bassClef);
        altoClef = (TextView)findViewById(R.id.altoClef);

        TextView fSharpTreb = (TextView)findViewById(R.id.fSharpTreb);
        TextView cSharpTreb = (TextView)findViewById(R.id.cSharpTreb);
        TextView gSharpTreb = (TextView)findViewById(R.id.gSharpTreb);
        TextView dSharpTreb = (TextView)findViewById(R.id.dSharpTreb);
        TextView aSharpTreb = (TextView)findViewById(R.id.aSharpTreb);
        TextView eSharpTreb = (TextView)findViewById(R.id.eSharpTreb);
        TextView bSharpTreb = (TextView)findViewById(R.id.bSharpTreb);

        TextView bFlatTreb = (TextView)findViewById(R.id.bFlatTreb);
        TextView eFlatTreb = (TextView)findViewById(R.id.eFlatTreb);
        TextView aFlatTreb = (TextView)findViewById(R.id.aFlatTreb);
        TextView dFlatTreb = (TextView)findViewById(R.id.dFlatTreb);
        TextView gFlatTreb = (TextView)findViewById(R.id.gFlatTreb);
        TextView cFlatTreb = (TextView)findViewById(R.id.cFlatTreb);
        TextView fFlatTreb = (TextView)findViewById(R.id.fFlatTreb);

        TextView fSharpBass = (TextView)findViewById(R.id.fSharpBass);
        TextView cSharpBass = (TextView)findViewById(R.id.cSharpBass);
        TextView gSharpBass = (TextView)findViewById(R.id.gSharpBass);
        TextView dSharpBass = (TextView)findViewById(R.id.dSharpBass);
        TextView aSharpBass = (TextView)findViewById(R.id.aSharpBass);
        TextView eSharpBass = (TextView)findViewById(R.id.eSharpBass);
        TextView bSharpBass = (TextView)findViewById(R.id.bSharpBass);

        TextView bFlatBass = (TextView)findViewById(R.id.bFlatBass);
        TextView eFlatBass = (TextView)findViewById(R.id.eFlatBass);
        TextView aFlatBass = (TextView)findViewById(R.id.aFlatBass);
        TextView dFlatBass = (TextView)findViewById(R.id.dFlatBass);
        TextView gFlatBass = (TextView)findViewById(R.id.gFlatBass);
        TextView cFlatBass = (TextView)findViewById(R.id.cFlatBass);
        TextView fFlatBass = (TextView)findViewById(R.id.fFlatBass);

        TextView fSharpAlto = (TextView)findViewById(R.id.fSharpAlto);
        TextView cSharpAlto = (TextView)findViewById(R.id.cSharpAlto);
        TextView gSharpAlto = (TextView)findViewById(R.id.gSharpAlto);
        TextView dSharpAlto = (TextView)findViewById(R.id.dSharpAlto);
        TextView aSharpAlto = (TextView)findViewById(R.id.aSharpAlto);
        TextView eSharpAlto = (TextView)findViewById(R.id.eSharpAlto);
        TextView bSharpAlto = (TextView)findViewById(R.id.bSharpAlto);

        TextView bFlatAlto = (TextView)findViewById(R.id.bFlatAlto);
        TextView eFlatAlto = (TextView)findViewById(R.id.eFlatAlto);
        TextView aFlatAlto = (TextView)findViewById(R.id.aFlatAlto);
        TextView dFlatAlto = (TextView)findViewById(R.id.dFlatAlto);
        TextView gFlatAlto = (TextView)findViewById(R.id.gFlatAlto);
        TextView cFlatAlto = (TextView)findViewById(R.id.cFlatAlto);
        TextView fFlatAlto = (TextView)findViewById(R.id.fFlatAlto);

        TextView[] sharpsTreb = new TextView[] {
                fSharpTreb, cSharpTreb, gSharpTreb, dSharpTreb,
                aSharpTreb, eSharpTreb, bSharpTreb
        };
        TextView[] flatsTreb = new TextView[] {
                bFlatTreb, eFlatTreb, aFlatTreb,
                dFlatTreb, gFlatTreb, cFlatTreb, fFlatTreb
        };
        TextView[] sharpsBass = new TextView[] {
                fSharpBass, cSharpBass, gSharpBass, dSharpBass,
                aSharpBass, eSharpBass, bSharpBass
        };
        TextView[] flatsBass = new TextView[] {
                bFlatBass, eFlatBass, aFlatBass,
                dFlatBass, gFlatBass, cFlatBass, fFlatBass
        };
        TextView[] sharpsAlto = new TextView[] {
                fSharpAlto, cSharpAlto, gSharpAlto, dSharpAlto,
                aSharpAlto, eSharpAlto, bSharpAlto
        };
        TextView[] flatsAlto = new TextView[] {
                bFlatAlto, eFlatAlto, aFlatAlto,
                dFlatAlto, gFlatAlto, cFlatAlto, fFlatAlto
        };

        TextView[][] accidentalsTreb = new TextView[][] {
                sharpsTreb, flatsTreb
        };
        TextView[][] accidentalsBass = new TextView[][] {
                sharpsBass, flatsBass
        };
        TextView[][] accidentalsAlto = new TextView[][] {
                sharpsAlto, flatsAlto
        };

        keySigAccidentals = new TextView[][][] {
                accidentalsTreb, accidentalsBass, accidentalsAlto
        };

        TextView noteheadA3 = (TextView)findViewById(R.id.noteheadA3);
        TextView noteheadB3 = (TextView)findViewById(R.id.noteheadB3);
        TextView noteheadC4 = (TextView)findViewById(R.id.noteheadC4);
        TextView noteheadD4 = (TextView)findViewById(R.id.noteheadD4);
        TextView noteheadE4 = (TextView)findViewById(R.id.noteheadE4);
        TextView noteheadF4 = (TextView)findViewById(R.id.noteheadF4);
        TextView noteheadG4 = (TextView)findViewById(R.id.noteheadG4);
        TextView noteheadA4 = (TextView)findViewById(R.id.noteheadA4);
        TextView noteheadB4 = (TextView)findViewById(R.id.noteheadB4);
        TextView noteheadC5 = (TextView)findViewById(R.id.noteheadC5);
        TextView noteheadD5 = (TextView)findViewById(R.id.noteheadD5);
        TextView noteheadE5 = (TextView)findViewById(R.id.noteheadE5);
        TextView noteheadF5 = (TextView)findViewById(R.id.noteheadF5);
        TextView noteheadG5 = (TextView)findViewById(R.id.noteheadG5);
        TextView noteheadA5 = (TextView)findViewById(R.id.noteheadA5);

        TextView noteheadA3r = (TextView)findViewById(R.id.noteheadA3r);
        TextView noteheadB3r = (TextView)findViewById(R.id.noteheadB3r);
        TextView noteheadC4r = (TextView)findViewById(R.id.noteheadC4r);
        TextView noteheadD4r = (TextView)findViewById(R.id.noteheadD4r);
        TextView noteheadE4r = (TextView)findViewById(R.id.noteheadE4r);
        TextView noteheadF4r = (TextView)findViewById(R.id.noteheadF4r);
        TextView noteheadG4r = (TextView)findViewById(R.id.noteheadG4r);
        TextView noteheadA4r = (TextView)findViewById(R.id.noteheadA4r);
        TextView noteheadB4r = (TextView)findViewById(R.id.noteheadB4r);
        TextView noteheadC5r = (TextView)findViewById(R.id.noteheadC5r);
        TextView noteheadD5r = (TextView)findViewById(R.id.noteheadD5r);
        TextView noteheadE5r = (TextView)findViewById(R.id.noteheadE5r);
        TextView noteheadF5r = (TextView)findViewById(R.id.noteheadF5r);
        TextView noteheadG5r = (TextView)findViewById(R.id.noteheadG5r);
        TextView noteheadA5r = (TextView)findViewById(R.id.noteheadA5r);

        TextView noteheadA3Sharp = (TextView)findViewById(R.id.noteheadA3Sharp);
        TextView noteheadB3Sharp = (TextView)findViewById(R.id.noteheadB3Sharp);
        TextView noteheadC4Sharp = (TextView)findViewById(R.id.noteheadC4Sharp);
        TextView noteheadD4Sharp = (TextView)findViewById(R.id.noteheadD4Sharp);
        TextView noteheadE4Sharp = (TextView)findViewById(R.id.noteheadE4Sharp);
        TextView noteheadF4Sharp = (TextView)findViewById(R.id.noteheadF4Sharp);
        TextView noteheadG4Sharp = (TextView)findViewById(R.id.noteheadG4Sharp);
        TextView noteheadA4Sharp = (TextView)findViewById(R.id.noteheadA4Sharp);
        TextView noteheadB4Sharp = (TextView)findViewById(R.id.noteheadB4Sharp);
        TextView noteheadC5Sharp = (TextView)findViewById(R.id.noteheadC5Sharp);
        TextView noteheadD5Sharp = (TextView)findViewById(R.id.noteheadD5Sharp);
        TextView noteheadE5Sharp = (TextView)findViewById(R.id.noteheadE5Sharp);
        TextView noteheadF5Sharp = (TextView)findViewById(R.id.noteheadF5Sharp);
        TextView noteheadG5Sharp = (TextView)findViewById(R.id.noteheadG5Sharp);
        TextView noteheadA5Sharp = (TextView)findViewById(R.id.noteheadA5Sharp);

        TextView noteheadA3Flat = (TextView)findViewById(R.id.noteheadA3Flat);
        TextView noteheadB3Flat = (TextView)findViewById(R.id.noteheadB3Flat);
        TextView noteheadC4Flat = (TextView)findViewById(R.id.noteheadC4Flat);
        TextView noteheadD4Flat = (TextView)findViewById(R.id.noteheadD4Flat);
        TextView noteheadE4Flat = (TextView)findViewById(R.id.noteheadE4Flat);
        TextView noteheadF4Flat = (TextView)findViewById(R.id.noteheadF4Flat);
        TextView noteheadG4Flat = (TextView)findViewById(R.id.noteheadG4Flat);
        TextView noteheadA4Flat = (TextView)findViewById(R.id.noteheadA4Flat);
        TextView noteheadB4Flat = (TextView)findViewById(R.id.noteheadB4Flat);
        TextView noteheadC5Flat = (TextView)findViewById(R.id.noteheadC5Flat);
        TextView noteheadD5Flat = (TextView)findViewById(R.id.noteheadD5Flat);
        TextView noteheadE5Flat = (TextView)findViewById(R.id.noteheadE5Flat);
        TextView noteheadF5Flat = (TextView)findViewById(R.id.noteheadF5Flat);
        TextView noteheadG5Flat = (TextView)findViewById(R.id.noteheadG5Flat);
        TextView noteheadA5Flat = (TextView)findViewById(R.id.noteheadA5Flat);

        TextView noteheadA3Natural = (TextView)findViewById(R.id.noteheadA3Natural);
        TextView noteheadB3Natural = (TextView)findViewById(R.id.noteheadB3Natural);
        TextView noteheadC4Natural = (TextView)findViewById(R.id.noteheadC4Natural);
        TextView noteheadD4Natural = (TextView)findViewById(R.id.noteheadD4Natural);
        TextView noteheadE4Natural = (TextView)findViewById(R.id.noteheadE4Natural);
        TextView noteheadF4Natural = (TextView)findViewById(R.id.noteheadF4Natural);
        TextView noteheadG4Natural = (TextView)findViewById(R.id.noteheadG4Natural);
        TextView noteheadA4Natural = (TextView)findViewById(R.id.noteheadA4Natural);
        TextView noteheadB4Natural = (TextView)findViewById(R.id.noteheadB4Natural);
        TextView noteheadC5Natural = (TextView)findViewById(R.id.noteheadC5Natural);
        TextView noteheadD5Natural = (TextView)findViewById(R.id.noteheadD5Natural);
        TextView noteheadE5Natural = (TextView)findViewById(R.id.noteheadE5Natural);
        TextView noteheadF5Natural = (TextView)findViewById(R.id.noteheadF5Natural);
        TextView noteheadG5Natural = (TextView)findViewById(R.id.noteheadG5Natural);
        TextView noteheadA5Natural = (TextView)findViewById(R.id.noteheadA5Natural);

        noteheads = new TextView[] {
                noteheadA3,
                noteheadB3, noteheadC4, noteheadD4,
                noteheadE4, noteheadF4, noteheadG4,
                noteheadA4, noteheadB4, noteheadC5,
                noteheadD5, noteheadE5, noteheadF5,
                noteheadG5, noteheadA5
        };
        noteheadsR = new TextView[] {
                noteheadA3r,
                noteheadB3r, noteheadC4r, noteheadD4r,
                noteheadE4r, noteheadF4r, noteheadG4r,
                noteheadA4r, noteheadB4r, noteheadC5r,
                noteheadD5r, noteheadE5r, noteheadF5r,
                noteheadG5r, noteheadA5r
        };
        TextView[] noteheadsSharps = new TextView[] {
                noteheadA3Sharp,
                noteheadB3Sharp, noteheadC4Sharp, noteheadD4Sharp,
                noteheadE4Sharp, noteheadF4Sharp, noteheadG4Sharp,
                noteheadA4Sharp, noteheadB4Sharp, noteheadC5Sharp,
                noteheadD5Sharp, noteheadE5Sharp, noteheadF5Sharp,
                noteheadG5Sharp, noteheadA5Sharp
        };
        TextView[] noteheadsFlats = new TextView[] {
                noteheadA3Flat,
                noteheadB3Flat, noteheadC4Flat, noteheadD4Flat,
                noteheadE4Flat, noteheadF4Flat, noteheadG4Flat,
                noteheadA4Flat, noteheadB4Flat, noteheadC5Flat,
                noteheadD5Flat, noteheadE5Flat, noteheadF5Flat,
                noteheadG5Flat, noteheadA5Flat
        };
        TextView[] noteheadsNaturals = new TextView[] {
                noteheadA3Natural,
                noteheadB3Natural, noteheadC4Natural, noteheadD4Natural,
                noteheadE4Natural, noteheadF4Natural, noteheadG4Natural,
                noteheadA4Natural, noteheadB4Natural, noteheadC5Natural,
                noteheadD5Natural, noteheadE5Natural, noteheadF5Natural,
                noteheadG5Natural, noteheadA5Natural
        };
        noteheadsAccidentals = new TextView[][] {
                noteheadsNaturals, noteheadsSharps, noteheadsFlats
        };

        pink = Color.parseColor("#f2ac9c");
        green = Color.parseColor("#87e9b8");
        dim = getString(R.string.dim);
        halfDim = getString(R.string.halfDim);
       //float scale = getResources().getDisplayMetrics().density; //so I can use java to move layout elements by dp and not just px
        cg = new ChordGenerator();
        wag = new WrongAnswerGenerator();

        //set and attach listeners
        View.OnClickListener gcListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomChord();
            }
        };
        View.OnClickListener a1Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCounterAndCheckmarks(aTextView, 0);
            }
        };
        View.OnClickListener a2Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCounterAndCheckmarks(bTextView, 1);
            }
        };
        View.OnClickListener a3Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCounterAndCheckmarks(cTextView, 2);
            }
        };
        View.OnClickListener a4Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCounterAndCheckmarks(dTextView, 3);
            }
        };
        View.OnClickListener refreshListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correct = 0;
                total = 0;
                scorePointsTextView.setText(correct + " / " + total);
            }
        };
        View.OnClickListener settingsListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(settingsIntent);
            }
        };
        generateChordButton.setOnClickListener(gcListener);
        answerButton1.setOnClickListener(a1Listener);
        answerButton2.setOnClickListener(a2Listener);
        answerButton3.setOnClickListener(a3Listener);
        answerButton4.setOnClickListener(a4Listener);
        refreshButton.setOnClickListener(refreshListener);
        settingsButton.setOnClickListener(settingsListener);

        //altering the xml layout with javacode
        Typeface neutonFont = Typefaces.get(this, "Neuton-Bold"); //font doesn't have the check/X mark characters or superscripted + and -
        for (Button b: answerButtonArray) {
            b.setTypeface(neutonFont);
            b.setTransformationMethod(null); //turns off all caps for API < 14
        }
        generateChordButton.setTypeface(neutonFont);
        generateChordButton.setTransformationMethod(null);
        scorePointsTextView.setTypeface(neutonFont);
        keyTextView.setTypeface(neutonFont);

        getRandomChord(); //so when the app starts it will produce its first chord to be analysed
    }
    public void getRandomChord() {
        resetThings(); //in preparation for the new chord

        isAppliedChords = SP.getBoolean("appliedChords", true);
        isModalMixtureChords = SP.getBoolean("modalMixture", true);
        isAlteredChords = SP.getBoolean("alteredChords", true);
        numKeySigAccidentalsInt = Integer.parseInt(SP.getString("keySignatureRange", "8"));

        //lengths of each dimension: chords[2][4][8][7]); all arrays meet those lengths, i.e. there are no ragged arrays,
        //so I can just use lengths of the zeroth index of things like chords[0].length with confidence
        int mode = (int)(Math.random() * cg.chords.length), //chords.length == 2
                accidentalAndChordSize = (int)(Math.random() * cg.chords[0].length), //chords[0].length == 4
                key = (int)(Math.random() * numKeySigAccidentalsInt), //chords[0][0].length == 8
                chord = (int)(Math.random() * cg.chords[0][0][0].length); //chords[0][0][0].length == 7
        String[] aRandomChord = cg.chords[mode][accidentalAndChordSize][key][chord],
                aRandomChordShuffled = shuffleChord(aRandomChord);
        String newBassNoteFromShuffling = aRandomChordShuffled[0];
        int inversionIndex = Arrays.asList(aRandomChord).indexOf(newBassNoteFromShuffling);

        rightAnswer = getNoAccidentalChord(mode, chord, accidentalAndChordSize, inversionIndex);

        //write the keyname to the keyTextView
        keyTextView.setText(cg.keyNames[mode][accidentalAndChordSize % 2][key].concat(colonSpace)); //again, b % 2 because the size of that array in keyNames != the parallel one in "chords"

        displayKeySignature(accidentalAndChordSize, key);
        displayPitches(aRandomChordShuffled);
        rightAnswer = getAndDisplayModalMixtureOrAppliedChords(aRandomChord, inversionIndex, mode, accidentalAndChordSize, key, chord);
        //give the right answer to a random button.
        rightAnswerIndex = (int)(Math.random() * answerButtonArray.length);
        answerButtonArray[rightAnswerIndex].setText(rightAnswer);
        createWrongAnswers();
    }
    public String replaceDimWithHalfDim() {
        return rightAnswer.replace(dim, halfDim);
    }
    public void displayKeySignature(int sharpsOrFlats, int numAccidentals) {
        double bassOrTrebleOrAlto = Math.random(); //randomly choose between treble, bass, and alto clef
        int randomClef;
        if (bassOrTrebleOrAlto > 0.5) {
            trebleClef.setVisibility(View.VISIBLE);
            isTrebleClef = true;
            randomClef = 0;
        } else if (bassOrTrebleOrAlto < 0.01) { //1% alto clef chance as a joke
            altoClef.setVisibility(View.VISIBLE);
            randomClef = 2;
        } else {
            bassClef.setVisibility(View.VISIBLE);
            isBassClef = true;
            randomClef = 1;
        }
        for (int i = 0; i < numAccidentals; i++) {
            keySigAccidentals[randomClef][sharpsOrFlats % 2][i].setVisibility(View.VISIBLE);
        }
    }
    public String getNoAccidentalChord(int mode, int chord, int accidental, int inversionIndex) {
        return (mode == ChordGenerator.MAJOR_MODE
                ? cg.romanNumeralsMajorArray[chord]
                : cg.romanNumeralsMinorArray[chord]
                    ).concat(accidental < 2 //concat a potential inversion to the RN
                        ? cg.triadicInversions[inversionIndex] //b < 2 because chords[][0] and chords[][1] are triads, chords[][2] and chords[][3] are 7th chords
                        : cg.seventhInversions[inversionIndex]
        );
    }
    //put an accidental on 1 pitch of the chord
    public void showChordAccidentals(int accidental, int chordMember) {
        noteheadsAccidentals[accidental][verticalIndices[shuffledChordOrderOfIntervals[chordMember]]].setVisibility(View.VISIBLE);
    }
    //put an accidental on 2 pitches of the chord
    public void showChordAccidentals(int firstAccidental, int secondAccidental, int firstChordMember, int secondChordMember) {
        noteheadsAccidentals[firstAccidental][verticalIndices[shuffledChordOrderOfIntervals[firstChordMember]]].setVisibility(View.VISIBLE);
        noteheadsAccidentals[secondAccidental][verticalIndices[shuffledChordOrderOfIntervals[secondChordMember]]].setVisibility(View.VISIBLE);
    }
    //put an accidental on 3 pitches of the chord
    public void showChordAccidentals(
            int firstAccidental, int secondAccidental, int thirdAccidental,
            int firstChordMember, int secondChordMember, int thirdChordMember
            ) {
        noteheadsAccidentals[firstAccidental][verticalIndices[shuffledChordOrderOfIntervals[firstChordMember]]].setVisibility(View.VISIBLE);
        noteheadsAccidentals[secondAccidental][verticalIndices[shuffledChordOrderOfIntervals[secondChordMember]]].setVisibility(View.VISIBLE);
        noteheadsAccidentals[thirdAccidental][verticalIndices[shuffledChordOrderOfIntervals[thirdChordMember]]].setVisibility(View.VISIBLE);
    }
    public String getAndDisplayModalMixtureOrAppliedChords(String[] aRandomChord, int inversionIndex, int mode, int accidental, int key, int chord) {
        if (rand.nextBoolean() && (isAlteredChords || isModalMixtureChords || isAppliedChords)) { //50% chance because I don't want it always applied/modal/altered chords
            //verticalIndices is a member array to use here.
            //shuffledChordOrderOfIntervals keeps track of the new shuffled order of a random chord
            //e.g. A-C-E-F will be [3, 0, 1, 2] because the root is in index 3, third in 0, fifth in 1, and seventh in 2. Don't get confused!
            //e.g. A-D-F will be [1, 2, 0] because the root is in index 1, third in index 2, and fifth in index 0
            //e.g. G-A-C-E will be [1, 2, 3, 0].
            //therefore when relating to verticalIndices, if the chord was E-A-C [1, 2, 0] vi6/4 in C-major key and I wanted to make it
            //bVI6/4 then I'd flatten verticalIndices[order[0]] and verticalIndices[order[2]] since 0 and 2 are root and fifth
            // == verticalIndices[1] and verticalIndices[0] get flattened, the E and A.
            //e.g. G-B-E [2, 0, 1] v6 in A-minor key I want to make it V6 (with G#), then verticalIndices[order[1]]
            shuffledChordOrderOfIntervals = new int[aRandomChord.length];
            for (int i = 0; i < shuffledChordOrderOfIntervals.length; i++) { //length of aRandomChord, aRandomShuff, and this new array are all the
                for (int j = 0; j < shuffledChordOrderOfIntervals.length; j++) { //same so I avoided writing many superfuluous conditions concerning .length
                    if (aRandomChord[i].substring(0, 1).equals(displayedRandomChord[j])) { //substring because of goofyness with how displayedRandomChord is created in displayPitches(); optimize later!
                        shuffledChordOrderOfIntervals[i] = j;
                        break; //to the i loop body
                    }
                }
            }
            if (mode == ChordGenerator.MAJOR_MODE) {
                switch (chord) {
                    case 0:
                        return getAndDisplayModalMixtureOrAppliedChords_Major_0(accidental, key, inversionIndex);
                    case 1:
                        return getAndDisplayModalMixtureOrAppliedChords_Major_1(accidental, key, inversionIndex);
                    case 2:
                        return getAndDisplayModalMixtureOrAppliedChords_Major_2(accidental, key, inversionIndex);
                    case 3:
                        return getAndDisplayModalMixtureOrAppliedChords_Major_3(accidental, key, inversionIndex);
                    case 4:
                        return getAndDisplayModalMixtureOrAppliedChords_Major_4(accidental, key, inversionIndex);
                    case 5:
                        return getAndDisplayModalMixtureOrAppliedChords_Major_5(accidental, key, inversionIndex);
                    case 6:
                        return getAndDisplayModalMixtureOrAppliedChords_Major_6(accidental, key, inversionIndex);
                }
            } else { //minor mode
                switch (chord) {
                    case 0:
                        return getAndDisplayModalMixtureOrAppliedChords_Minor_0(accidental, key, inversionIndex);
                    case 1:
                        return getAndDisplayModalMixtureOrAppliedChords_Minor_1(accidental, key, inversionIndex);
                    case 2:
                        return getAndDisplayModalMixtureOrAppliedChords_Minor_2(accidental, key, inversionIndex);
                    case 3:
                        return getAndDisplayModalMixtureOrAppliedChords_Minor_3(accidental, key, inversionIndex);
                    case 4:
                        return getAndDisplayModalMixtureOrAppliedChords_Minor_4(accidental, key, inversionIndex);
                    case 5:
                        return getAndDisplayModalMixtureOrAppliedChords_Minor_5(accidental, key, inversionIndex);
                    case 6: //commented out because it's redundant -- it already appears at the end of this method
                        //return getAndDisplayModalMixtureOrAppliedChords_Minor_6(accidental, key, inversionIndex);
                        break;
                }
            }
        }
        //If no chords were changed, I still need to change viio7 to viihalfdim7 in major and ii for minor
        if (accidental > 1 && ((mode == ChordGenerator.MAJOR_MODE && chord == 6)
            || mode == ChordGenerator.MINOR_MODE && chord == 1)) {
            rightAnswer = replaceDimWithHalfDim();
            noAccidentalsAdded = true;
        }
        //and if Applied chords were checked but it didn't randomly decide to do it, it should still never
        //say VII7 in minor if that boolean were true.
        if (isAppliedChords && accidental > 1 && (mode == ChordGenerator.MINOR_MODE && chord == 6)) {
            noAccidentalsAdded = false;
            return getAndDisplayModalMixtureOrAppliedChords_Minor_6(accidental, key, inversionIndex);
        }
        noAccidentalsAdded = true;
        return rightAnswer; //when no criteria matched to add accidentals to the chord
    }
    //this method too many lines?
    public void displayPitches(String[] aRandomChordShuffled) {
        int verticalIndex = 0;
        String[] everythingButBassNote = new String[aRandomChordShuffled.length - 1];
        verticalIndices = new int[aRandomChordShuffled.length];

        //get every pitch from the shuffled chord except the bass note and sort them;
        //this is needed for a tightly packed chord input on the musical staff
        for (int i = 0; i < everythingButBassNote.length; i++) {
            everythingButBassNote[i] = aRandomChordShuffled[i + 1].substring(0, 1);
        }
        Arrays.sort(everythingButBassNote);

        //This is a fix for a bug that happens with a B-A-D-F chord in the bass clef only. The staff runs out of space
        //after filling in the first two pitches of that chord, because there is no low B available below the bass staff in this music font.
        //Normally this would easily be possible to fix by hand, but arrays.sort sorts from A-Z for the non-bass-note pitches. I could just change arrays.sort
        //into something that starts from a pitch other than A, but this would cause even more unforeseen issues down the line.
        if (isBassClef && aRandomChordShuffled[0].substring(0, 1).equals(cg.pitchClassesArray[6])
                && everythingButBassNote[0].equals(cg.pitchClassesArray[5])) {
            String firstElement = everythingButBassNote[0];
            for (int i = 1; i < everythingButBassNote.length; i++) {
                everythingButBassNote[i - 1] = everythingButBassNote[i];
            }
            everythingButBassNote[everythingButBassNote.length - 1] = firstElement;
        }

        //this combines the bass note of the random shuffled chord AND the everythingButBassNote
        //array which I have to do because the latter array just did Arrays.sort();
        displayedRandomChord = new String[aRandomChordShuffled.length];
        displayedRandomChord[0] = aRandomChordShuffled[0].substring(0, 1);
        for (int i = 1; i < displayedRandomChord.length; i++) {
            displayedRandomChord[i] = everythingButBassNote[i - 1];
        }

        //get the bass note of the shuffled chord and display it
        for (int i = isTrebleClef ? 5 : (isBassClef ? 0 : 6), j = 0; //trebclef ? start on pitchclass A, else start on pitchclass C
             j < cg.pitchClassesArray.length;
             i = (i + 1) % ChordGenerator.NUM_PITCH_CLASSES, j++) {
            if (cg.pitchClassesArray[i].equals(aRandomChordShuffled[0].substring(0, 1))) {
                verticalIndex = j;
                noteheads[verticalIndex].setVisibility(View.VISIBLE);  //move to the right by num_accidentals * a constant?
                verticalIndices[0] = j;
                break;
            }
        }
        //input the remaining 2-3 pitches.
        //confusing line below: the trebleclef lowest possible displayed note is A3, but pitchClassesArray starts at C, so I'll start at pitchClassesArray[(0 - 2) % 7] to begin on A instead,
        //but it might be a negative so + 7 (==NUM_PITCH..) solves that, since I'm using modulus anyway (but modulus doesnt work on negative values). same logic applies to alto clef
        //but for verticalIndex - 1 instead of 2.
        for (int i = isTrebleClef ? ((verticalIndex - 2 + ChordGenerator.NUM_PITCH_CLASSES) % ChordGenerator.NUM_PITCH_CLASSES)
                : (isBassClef ? verticalIndex
                : (verticalIndex - 1 + ChordGenerator.NUM_PITCH_CLASSES) % ChordGenerator.NUM_PITCH_CLASSES),
             j = verticalIndex, k = 0;
             j < noteheads.length && k < everythingButBassNote.length;
             i = (i + 1) % ChordGenerator.NUM_PITCH_CLASSES, j++) {
            if (cg.pitchClassesArray[i].equals(everythingButBassNote[k])) {
                noteheads[j].setVisibility(View.VISIBLE);
                k++;
                verticalIndices[k] = j; //after k++ since k starts at 0 and i already assigned verticalIndicies[0] beforehand.
                //this is just a wacky workaround so I don't have to create another variable like x = 1 for example.
                if (j > 0 && noteheads[j - 1].getVisibility() == View.VISIBLE) { //if the pitch added before this one is an interval of a 2nd below
                    noteheads[j].setVisibility(View.INVISIBLE); //it, then it must shift to the right a little to make space
                    noteheadsR[j].setVisibility(View.VISIBLE);
                }
            }
        }
    }
    //creates text for the three answerButtons which hold incorrect answer values
    public void createWrongAnswers() {
        for (int i = 0, j = 0; i < answerButtonArray.length && j < wrongAnswerArray.length; i++) {
            if (i != rightAnswerIndex) {
                if (wrongAnswer != null) {
                    wrongAnswerArray[j] = wrongAnswer;
                    j++;
                }
                do {
                    //passing displayedRandomChord.length so I can get all triad wrong answers if
                    // rightAnswer is a triad, and all seventh wrong answers if rightAnswer is a seventh chord
                    // and other booleans so wrongAnswers mimic the rightAnswer "type" of chord
                    wrongAnswer = wag.getRandomWrongAnswer(displayedRandomChord.length, isModalMixtureChords, isAppliedChords, noAccidentalsAdded);
                } while (wrongAnswer.equalsIgnoreCase(rightAnswer) //avoids a duplicate right answer
                        || containsCaseInsensitive(wrongAnswer, wrongAnswerArray)  //avoids duplicate wrong answers
                        );
                answerButtonArray[i].setText(wrongAnswer);
            }
        }
    }
    public static boolean containsCaseInsensitive(String s, String[] sArr) {
        for (int i = 0; i < sArr.length; i++) {
            if (sArr[i] != null && s.equalsIgnoreCase(sArr[i])) {
                return true;
            }
        }
        return false;
    }
    //cleans up the interface; a clean slate for the new chord
    public void resetThings() {
        stopCounting = false; //score can go again up with each Next Chord button click
        isTrebleClef = false; //a reset so it can try for either treb, bass, or alto clef
        isBassClef = false;
        noAccidentalsAdded = false;
        setNotationInvisible();
    }
    //"reset" the clef / key signature / noteheads /checkmarks / accidentals to make a clean slate for the next random chord
    public void setNotationInvisible() {
        trebleClef.setVisibility(View.INVISIBLE);
        bassClef.setVisibility(View.INVISIBLE);
        altoClef.setVisibility(View.INVISIBLE);
        for (TextView[][] clef: keySigAccidentals) {
            for (TextView[] mode: clef) {
                for (TextView acc: mode) {
                    acc.setVisibility(View.INVISIBLE);
                }
            }
        }
        for (TextView note: noteheads) {
            note.setVisibility(View.INVISIBLE);
        }
        for (TextView noteR: noteheadsR) {
            noteR.setVisibility(View.INVISIBLE);
        }
        for (TextView tv: qTextViews) {
            tv.setVisibility(View.INVISIBLE);
        }
        for (TextView[] accType: noteheadsAccidentals) {
            for (TextView acc: accType) {
                acc.setVisibility(View.INVISIBLE);
            }
        }
    }
    public void changeCounterAndCheckmarks(TextView tv, int clickedButton) {
        if (rightAnswer != null && rightAnswerIndex == clickedButton) {
            if (!stopCounting) {
                correct++;
                total++;
                scorePointsTextView.setText(correct + " / " + total);
                stopCounting = true;
            }
            tv.setVisibility(View.VISIBLE);
            tv.setText(checkMark);
            tv.setTextColor(green);
        } else if (rightAnswer != null) {
            if (!stopCounting) {
                total++;
                scorePointsTextView.setText(correct + " / " + total);
                stopCounting = true;
            }
            tv.setVisibility(View.VISIBLE);
            tv.setText(xMark);
            tv.setTextColor(pink);
        }
    }
    public String[] shuffleChord(String[] arr) {
        List<String> arrList = new ArrayList<>(Arrays.asList(arr));
        Collections.shuffle(arrList);
        return arrList.toArray(new String[arrList.size()]);
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Major_0(int accidental, int key, int inversionIndex) {
        //make V7/IV in major
        if (isAppliedChords) {
            if (accidental == 2) {
                //from key C-major
                if (key == 0) {
                    showChordAccidentals(FLAT, SEVENTH);
                    //the rest, from G-major to C#-major just use naturals for that
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, SEVENTH);
                }
                return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMajor[2]);
            }
            //from C-major to Gb-major
            if ((accidental == 3) && key <= 6) {
                showChordAccidentals(FLAT, SEVENTH);
                return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMajor[2]);
            }
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Major_1(int accidental, int key, int inversionIndex) {
        //something needs to be done to the ii chord of the major mode. chance will determine whether
        //it becomes iidim/halfdim7 or bII
        if (rand.nextBoolean() && (isModalMixtureChords || isAlteredChords)) {
            //make iidim/halfDim7 (the RN will have to change its degree/o with stroke accordingly)
            if (isModalMixtureChords && accidental == 0) {
                //from keys C-major to D-major need all flats
                if (key <= 2) {
                    showChordAccidentals(FLAT, FIFTH);
                    //the rest, from A-major to C#-major just use naturals for that
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, FIFTH);
                }
                return cg.romanNumeralsMinorArray[1].concat(cg.triadicInversions[inversionIndex]);
            }
            //from keys C-major to Ab-major just use flats
            if (isModalMixtureChords && (accidental == 1) && key <= 4) {
                showChordAccidentals(FLAT, FIFTH);
                return cg.romanNumeralsMinorArray[1].concat(cg.triadicInversions[inversionIndex]);
            }
            //chance between Fr4/3 and ii halfdim7. I want to stack it in favour of Fr4/3 though, so no rand.nextBoolean()
            if (isAlteredChords && shuffledChordOrderOfIntervals[2] == 0) { //the fifth of the chord is the bassnote
                if (accidental == 2) { //French 6th
                    //from keys C-major to D-major need all flats and sharps
                    if (key <= 2) {
                        showChordAccidentals(SHARP, FLAT, THIRD, FIFTH);
                        return cg.augmentedSixths[1];
                        //the rest, from A-major to F#-major
                    } else if (key <= 6) {
                        showChordAccidentals(SHARP, NATURAL_OR_NOTHING, THIRD, FIFTH);
                        return cg.augmentedSixths[1];
                    }
                }
                //from keys C-major to Ab-major just use flats for the 3rd and 7th of iv7
                if (accidental == 3) {
                    if (key == 0) {
                        showChordAccidentals(SHARP, FLAT, THIRD, FIFTH);
                        return cg.augmentedSixths[1];
                    } else if (key <= 4) {
                        showChordAccidentals(NATURAL_OR_NOTHING, FLAT, THIRD, FIFTH);
                        return cg.augmentedSixths[1];
                    }
                }
                //ii half dim seventh chords need to use the half dim symbol
            } else if (isModalMixtureChords && accidental == 2) {
                //from keys C-major to D-major need all flats
                if (key <= 2) {
                    showChordAccidentals(FLAT, FIFTH);
                    //the rest, from A-major to C#-major just use naturals for that
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, FIFTH);
                }
                return cg.romanNumeralsMajorArray[1].concat(halfDim).concat(cg.seventhInversions[inversionIndex]);
            }

            if (isModalMixtureChords && (accidental == 3) && key <= 4) {
                showChordAccidentals(FLAT, FIFTH);
                return cg.romanNumeralsMajorArray[1].concat(halfDim).concat(cg.seventhInversions[inversionIndex]);
            }
        } else {
            //make bII in major keys. only as triads, or V7/V only as sevenths
            //bII
            if (isModalMixtureChords && accidental == 0) {
                //from keys C-major to D-major need all flats
                if (key <= 2) {
                    showChordAccidentals(FLAT, FLAT, ROOT, FIFTH);
                    //A-major is a special case that needs a flat for root and natural for fifth
                } else if (key == 3) {
                    showChordAccidentals(FLAT, NATURAL_OR_NOTHING, ROOT, FIFTH);
                    //the rest, from E-major to C#-major just use naturals for that
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, NATURAL_OR_NOTHING, ROOT, FIFTH);
                }
                return cg.accidentalsArray[FLAT].concat(bigII).concat(cg.triadicInversions[inversionIndex]);
            }
            //for bII: from keys C-major to Eb-major just use flats.
            if (isModalMixtureChords && (accidental == 1) && key <= 3) {
                showChordAccidentals(FLAT, FLAT, ROOT, FIFTH);
                return cg.accidentalsArray[FLAT].concat(bigII).concat(cg.triadicInversions[inversionIndex]);
            }
            //V7/V
            if (isAppliedChords && accidental == 2) {
                //C-major to F#-major
                if (key <= 6) {
                    showChordAccidentals(SHARP, THIRD);
                    return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMajor[3]);
                }
            }
            //for V7/V
            if (isAppliedChords && accidental == 3) {
                //for C-major only
                if (key == 0) {
                    showChordAccidentals(SHARP, THIRD);
                    //from F-major to the rest
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, THIRD);
                }
                return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMajor[3]);
            }
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Major_2(int accidental, int key, int inversionIndex) {
        //make bIII in major keys. only as triads. but V7/vi for the sevenths
        //bIII
        if (isModalMixtureChords && accidental == 0) {
            //from keys C-major
            if (key == 0) {
                showChordAccidentals(FLAT, FLAT, ROOT, FIFTH);
                //G-major is a special case that needs a flat for root and natural for fifth
            } else if (key == 1) {
                showChordAccidentals(FLAT, NATURAL_OR_NOTHING, ROOT, FIFTH);
                //the rest, from A-major to C#-major just use naturals for that
            } else {
                showChordAccidentals(NATURAL_OR_NOTHING, NATURAL_OR_NOTHING, ROOT, FIFTH);
            }
            return cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[2]).concat(cg.triadicInversions[inversionIndex]);
        }
        //bIII: from keys C-major to Db-major just use flats.
        if (isModalMixtureChords && (accidental == 1) && key <= 5) {
            showChordAccidentals(FLAT, FLAT, ROOT, FIFTH);
            return cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[2]).concat(cg.triadicInversions[inversionIndex]);
        }
        //V7/vi
        if (isAppliedChords && accidental == 2) {
            //from keys C-major to Emajor
            if (key <= 4) {
                showChordAccidentals(SHARP, THIRD);
                return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMajor[4]);
            }
        }
        //for V7/vi: from keys C-major to Eb-major
        if (isAppliedChords && accidental == 3) {
            //for C-major to Bb-major
            if (key <= 2) {
                showChordAccidentals(SHARP, THIRD);
                //from Eb-major to the rest
            } else {
                showChordAccidentals(NATURAL_OR_NOTHING, THIRD);
            }
            return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMajor[4]);
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Major_3(int accidental, int key, int inversionIndex) {
        //make augmented 6ths (it6 or ger6,5 see chord==1 for fr4/3)
        //make iv in major keys. both as triads and sevenths (I feel sevenths are ok; there's no V7 / bVII
        //in this program so the IV chord is only changed ever to "iv" through this method in major
        if (isAlteredChords && shuffledChordOrderOfIntervals[1] == 0) { //[1] == 0 means the third of the chord is the bass note
            if (accidental == 0) { //italian 6th
                //from keys C-major to D-major need all flats and sharps
                if (key <= 2) {
                    showChordAccidentals(SHARP, FLAT, ROOT, THIRD);
                    return cg.augmentedSixths[0];
                    //the rest, from A-major to F#-major
                } else if (key <= 6) {
                    showChordAccidentals(SHARP, NATURAL_OR_NOTHING, ROOT, THIRD);
                    return cg.augmentedSixths[0];
                }
            }
            //from keys C-major to Ab-major just use flats for the 3rd and 7th of iv7
            if (accidental == 1) {
                if (key == 0) {
                    showChordAccidentals(SHARP, FLAT, ROOT, THIRD);
                    return cg.augmentedSixths[0];
                } else if (key <= 4) {
                    showChordAccidentals(NATURAL_OR_NOTHING, FLAT, ROOT, THIRD);
                    return cg.augmentedSixths[0];
                }
            }
        } else if (isModalMixtureChords) {
            if (accidental == 0) {
                //from keys C-major to D-major need all flats
                if (key <= 2) {
                    showChordAccidentals(FLAT, THIRD);
                    //the rest, from A-major to C#-major just use naturals for that
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, THIRD);
                }
                return cg.romanNumeralsMinorArray[3].concat(cg.triadicInversions[inversionIndex]);
            }
            //from keys C-major to Ab-major just use flats for the 3rd and 7th of iv7
            if ((accidental == 1) && key <= 4) {
                showChordAccidentals(FLAT, THIRD);
                return cg.romanNumeralsMinorArray[3].concat(cg.triadicInversions[inversionIndex]);
            }
        }
        //sometimes have iv7, sometimes have ger6/5. Fr4/3 is found in the ii chords (chord == 1)
        //Ger6/5 has clashes with intervals for major mode; commenting it out.
            /*
            if (isAlteredChords && shuffledChordOrderOfIntervals[1] == 0) {//Ger6/5
                if (accidental == 2) {
                    //from keys C-major to D-major need all flats and sharps
                    if (key <= 1) {
                        showChordAccidentals(SHARP, FLAT, FLAT, ROOT, THIRD, SEVENTH);
                        return cg.augmentedSixths[2];
                    } else if (key == 2) {
                        showChordAccidentals(SHARP, FLAT, NATURAL_OR_NOTHING, ROOT, THIRD, SEVENTH);
                        return cg.augmentedSixths[2];
                    } else if (key <= 6) {
                        showChordAccidentals(SHARP, NATURAL_OR_NOTHING, NATURAL_OR_NOTHING, ROOT, THIRD, SEVENTH);
                        return cg.augmentedSixths[2];
                    }
                }
                //from keys C-major to Ab-major just use flats for the 3rd and 7th of iv7
                if (accidental == 3) {
                    if (key == 0) {
                        showChordAccidentals(SHARP, FLAT, FLAT, ROOT, THIRD, SEVENTH);
                        return cg.augmentedSixths[2];
                    } else if (key <= 4) {
                        showChordAccidentals(NATURAL_OR_NOTHING, FLAT, FLAT, ROOT, THIRD, SEVENTH);
                        return cg.augmentedSixths[2];
                    }
                }
                //iv7
            } else */ if (isModalMixtureChords) {
            //special case for 7th chords, because the 7th of the chord needs to be altered if the randomly generate chord is a length of 4 (i.e.7th chord)
            if (accidental == 2) {
                //C major and Gmajor have a flat 7th within iv7
                if (key <= 1) {
                    showChordAccidentals(FLAT, FLAT, THIRD, SEVENTH);
                    //G-major has both a flat and nat for iv7
                } else if (key == 2) {
                    showChordAccidentals(FLAT, NATURAL_OR_NOTHING, THIRD, SEVENTH);
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, NATURAL_OR_NOTHING, THIRD, SEVENTH);
                }
                return cg.romanNumeralsMinorArray[3].concat(cg.seventhInversions[inversionIndex]);
            }

            if ((accidental == 3) && key <= 4) {
                showChordAccidentals(FLAT, FLAT, THIRD, SEVENTH);
                return cg.romanNumeralsMinorArray[3].concat(cg.seventhInversions[inversionIndex]);
            }
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Major_4(int accidental, int key, int inversionIndex) {
        if (rand.nextBoolean() && isAlteredChords) {
            //make V+ in major, both as triads and sevenths
            if (accidental % 2 == 0) {
                //from keys C-major to A-major
                if (key <= 3) {
                    showChordAccidentals(SHARP, FIFTH);
                    return cg.romanNumeralsMajorArray[4].concat(plusSign).concat(
                            accidental == 0 ? cg.triadicInversions[inversionIndex]
                                    : cg.seventhInversions[inversionIndex] //accidental == 2 therefore seventh chord
                    );
                }
            }
            //from keys C-major to Db-major just use flats.
            if (accidental % 2 == 1) {
                if (key <= 3) {
                    showChordAccidentals(SHARP, FIFTH);
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, FIFTH);
                }
                return cg.romanNumeralsMajorArray[4].concat(plusSign).concat(
                        accidental == 1 ? cg.triadicInversions[inversionIndex]
                                : cg.seventhInversions[inversionIndex] //accidental == 3 therefore seventh chord
                );
            }
        } else if (isAlteredChords) {
            //make Vdim in major, both as triads and sevenths
            if (accidental % 2 == 0) {
                //from keys C-major to A-major
                if (key <= 3) {
                    showChordAccidentals(FLAT, FIFTH);
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, FIFTH);
                }
                return cg.romanNumeralsMajorArray[4].concat(minusSign).concat(
                        accidental == 0 ? cg.triadicInversions[inversionIndex]
                                : cg.seventhInversions[inversionIndex] //accidental == 2 therefore seventh chord
                );
            }
            //from keys C-major to Eb-major just use flats.
            if ((accidental % 2 == 1) && key <= 3) {
                showChordAccidentals(FLAT, FIFTH);
                return cg.romanNumeralsMajorArray[4].concat(minusSign).concat(
                        accidental == 1 ? cg.triadicInversions[inversionIndex]
                                : cg.seventhInversions[inversionIndex] //accidental == 3 therefore seventh chord
                );
            }
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Major_5(int accidental, int key, int inversionIndex) {
        //make bVI in major keys. just as triads
        if (isModalMixtureChords && accidental == 0) {
            //from keys C-major to G-major need all flats
            if (key <= 1) {
                showChordAccidentals(FLAT, FLAT, ROOT, FIFTH);
                //D-major is a special case that needs a flat for root and natural for fifth
            } else if (key == 2) {
                showChordAccidentals(FLAT, NATURAL_OR_NOTHING, ROOT, FIFTH);
                //the rest, from A-major to C#-major just use naturals for that
            } else {
                showChordAccidentals(NATURAL_OR_NOTHING, NATURAL_OR_NOTHING, ROOT, FIFTH);
            }
            return cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[5]).concat(cg.triadicInversions[inversionIndex]);
        }
        //from keys C-major to Ab-major just use flats
        if (isModalMixtureChords && (accidental == 1) && key <= 4) {
            showChordAccidentals(FLAT, FLAT, ROOT, FIFTH);
            return cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[5]).concat(cg.triadicInversions[inversionIndex]);
        }
        //V7/ii
        if (isAppliedChords && accidental == 2) {
            //from keys C-major to Bmajor
            if (key <= 5) {
                showChordAccidentals(SHARP, THIRD);
                return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMajor[0]);
            }
        }
        //for V7/ii
        if (isAppliedChords && accidental == 3) {
            //for C-major to F-major
            if (key <= 1) {
                showChordAccidentals(SHARP, THIRD);
                //from Bb-major to the rest
            } else {
                showChordAccidentals(NATURAL_OR_NOTHING, THIRD);
            }
            return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMajor[0]);
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Major_6(int accidental, int key, int inversionIndex) {
        //I need this extra condition for now because seventh chords can't get changed to bVII,
        //and it they fell inside this area they'd then just remain unchanged and never reach
        //the "else" which gives viidim7 its proper title viihalfdim7
        if (isModalMixtureChords && rand.nextBoolean() && accidental < 2) { //&& !(accidental == 1 && key == 7) (for when V7/iii is enabled)
            //make bVII in major keys, just as triads
            if (accidental == 0) {
                //the key of C-major needs all flats
                if (key == 0) {
                    showChordAccidentals(FLAT, ROOT);
                    //the rest, from G-major to C#-major just use naturals for that
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, ROOT);
                }
                return cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[6]).concat(cg.triadicInversions[inversionIndex]);
            }
            //from keys C-major to Gb-major just use flats.
            if (isModalMixtureChords && (accidental == 1) && key <= 6) {
                showChordAccidentals(FLAT, ROOT);
                return cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[6]).concat(cg.triadicInversions[inversionIndex]);
            }
            //for now, V7/iii is avoided because of accidental collision between sharpened thirds and fifths

        } else { //this else should happen whether applied chords/modal mixture checked or not
            //just change the viio7 to viihalfdim7
            //so replace the degree with a O WITH STROKE
            if (accidental > 1) {
                noAccidentalsAdded = true;
                return replaceDimWithHalfDim();
            }
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }

    public String getAndDisplayModalMixtureOrAppliedChords_Minor_0(int accidental, int key, int inversionIndex) {
        //make V7/iv
        if (isAppliedChords) {
            if (accidental == 2) {
                //from key A-minor to G#-minor
                if (key <= 5) {
                    showChordAccidentals(SHARP, THIRD);
                    return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMinor[1]);
                }
            }
            //from A-minor to D-minor
            if (accidental == 3) {
                if (key <= 1) {
                    showChordAccidentals(SHARP, THIRD);
                    //G-minor onwards requires a natural
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, THIRD);
                }
                return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMinor[1]);
            }
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Minor_1(int accidental, int key, int inversionIndex) {
        //I need this extra condition for now because seventh chords can't get changed to bII,
        //and it they fell inside this area they'd then just remain unchanged and never reach
        //the "else" which gives iidim7 its proper title iihalfdim7
        if (isModalMixtureChords && accidental < 2) {
            //make bII in minor, just as triads
            if (accidental == 0) {
                //A-minor only uses a flat
                if (key == 0) {
                    showChordAccidentals(FLAT, ROOT);
                    //The rest use a natural
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, ROOT);
                }
                return cg.accidentalsArray[FLAT].concat(bigII).concat(cg.triadicInversions[inversionIndex]);
            }
            //from keys A-minor to Eb-minor just use flats.
            if ((accidental == 1) && key <= 6) {
                showChordAccidentals(FLAT, ROOT);
                return cg.accidentalsArray[FLAT].concat(bigII).concat(cg.triadicInversions[inversionIndex]);
            }
        } else if (rand.nextBoolean() && isAlteredChords
                && (shuffledChordOrderOfIntervals[1] == 0 || shuffledChordOrderOfIntervals[2] == 0)) {

            if (accidental == 2) {
                //from keys C-major to D-major need all flats and sharps
                if (key <= 3) {
                    showChordAccidentals(SHARP, THIRD);
                    //the same rules apply in minor for italin and german 6ths
                    if (shuffledChordOrderOfIntervals[1] == 0) { //third was the bass note
                        return cg.augmentedSixths[4];
                    } else { //fifth was the bass note
                        return cg.augmentedSixths[1];
                    }
                }
            }
            //from keys C-major to Ab-major just use flats for the 3rd and 7th of iv7
            if (accidental == 3) {
                if (key <= 3) {
                    showChordAccidentals(SHARP, THIRD);
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, THIRD);
                }
                //the same rules apply in minor for italin and german 6ths
                if (shuffledChordOrderOfIntervals[1] == 0) { //third was the bass note
                    return cg.augmentedSixths[4];
                } else { //fifth was the bass note
                    return cg.augmentedSixths[1];
                }
            }

        } else {//should happen whether applied chords checkbox checked or not
            //just change the iio7 to viihalfdim7
            //so replace the degree with a O WITH STROKE
            if (accidental > 1) { // > 1 means seventh chords only
                noAccidentalsAdded = true;
                return replaceDimWithHalfDim();
            }
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Minor_2(int accidental, int key, int inversionIndex) {
        //make V7/VI
        if (isAppliedChords) {
            if (accidental == 2) {
                //from key A-minor
                if (key == 0) {
                    showChordAccidentals(FLAT, SEVENTH);
                    //the rest take a natural
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, SEVENTH);
                }
                return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMinor[3]);
            }
            //from A-minor to Eb-minor
            if (accidental == 3) {
                if (key <= 6) {
                    showChordAccidentals(FLAT, SEVENTH);
                    return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMinor[3]);
                }
            }
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Minor_3(int accidental, int key, int inversionIndex) {
        //make IV in minor. just as triads. or V7/VII for sevenths OR
        //italian 6ths and German 6ths
        if (isAlteredChords
                && (shuffledChordOrderOfIntervals[0] == 0 || shuffledChordOrderOfIntervals[1] == 0)) { //[1] == 0 means the third of the chord is the bass note
            if (accidental % 2 == 0) {
                //from keys C-major to D-major need all flats and sharps
                if (key <= 3) {
                    showChordAccidentals(SHARP, ROOT);
                    //the same rules apply in minor for italin and german 6ths
                    if (shuffledChordOrderOfIntervals[0] == 0) { //root was the bass note
                        return accidental == 0 ? cg.augmentedSixths[3] : cg.augmentedSixths[5];
                    } else { //third was the bass note
                        return accidental == 0 ? cg.augmentedSixths[0] : cg.augmentedSixths[2];
                    }
                }
            }
            //from keys C-major to Ab-major just use flats for the 3rd and 7th of iv7
            if (accidental % 2 == 1) {
                if (key <= 3) {
                    showChordAccidentals(SHARP, ROOT);
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, ROOT);
                }
                //the same rules apply in minor for italin and german 6ths
                if (shuffledChordOrderOfIntervals[0] == 0) { //root was the bass note
                    return accidental == 1 ? cg.augmentedSixths[3] : cg.augmentedSixths[5];
                } else { //third was the bass note
                    return accidental == 1 ? cg.augmentedSixths[0] : cg.augmentedSixths[2];
                }
            }
        } else if (isModalMixtureChords) {
            if (accidental == 0) {
                //A-minor to D#-minor
                if (key <= 6) {
                    showChordAccidentals(SHARP, THIRD);
                    return cg.romanNumeralsMajorArray[3].concat(cg.triadicInversions[inversionIndex]);
                }
            }
            //from keys A-minor to Eb-minor just use flats.
            if (accidental == 1) {
                if (key == 0) {
                    showChordAccidentals(SHARP, THIRD);

                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, THIRD);
                }
                return cg.romanNumeralsMajorArray[3].concat(cg.triadicInversions[inversionIndex]);
            }
        }
        //make V7/VII
        if (isAppliedChords && accidental == 2) {
            //from key A-minor to D#-minor
            if (key <= 6) {
                showChordAccidentals(SHARP, THIRD);
                return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMinor[4]);
            }
        }
        //from A-minor
        if (isAppliedChords && accidental == 3) {
            if (key == 0) {
                showChordAccidentals(SHARP, THIRD);
            } else {
                showChordAccidentals(NATURAL_OR_NOTHING, THIRD);
            }
            return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMinor[4]);
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Minor_4(int accidental, int key, int inversionIndex) {

        //make v into V for minor
        if (isModalMixtureChords) {
            if ((accidental % 2 == 0) && key <= 4) {
                showChordAccidentals(SHARP, THIRD);
                return cg.romanNumeralsMajorArray[4].concat(
                        accidental == 0 ? cg.triadicInversions[inversionIndex]
                                : cg.seventhInversions[inversionIndex]
                );
            }
            if (accidental % 2 == 1) {
                //from A-minor to G-minor take sharps as leading tones
                if (key <= 2) {
                    showChordAccidentals(SHARP, THIRD);
                    //from C-minor to Ab-minor take naturals as leading tones
                } else {
                    showChordAccidentals(NATURAL_OR_NOTHING, THIRD);
                }
                return cg.romanNumeralsMajorArray[4].concat(
                        accidental == 1 ? cg.triadicInversions[inversionIndex]
                                : cg.seventhInversions[inversionIndex]
                );
            }
        }
            /*
         this one's broken for two reasons. 1) the accidentals are sometimes a third apart and
        collide with each other vertically. 2) v-->V above it already changes the chord to upper-case
        V. Because this is the minor mode, however, it tries to replace lower-case v with V-, but such
        lower-case v no longer exists due to the aforementioend v-->V
        //make Vdim in minor. this adds on to what was already done above by making v-->V in minor
        if (mode == ChordGenerator.MINOR_MODE && chord == 4) {
            if (accidental % 2 == 0) {
                //for A-minor
                if (key == 0) {
                    showChordAccidentals(mode, FLAT, FIFTH, chord,
                            cg.romanNumeralsMajorArray[chord].concat(minusSign));
                    //from D-minor to C#-minor
                } else if (key <= 4) {
                    showChordAccidentals(mode, NATURAL_OR_NOTHING, FIFTH, chord,
                            cg.romanNumeralsMajorArray[chord].concat(minusSign));
                }
            }
            //from A-minor to Eb-minor
            if ((accidental % 2 == 1) && key <= 6) {
                showChordAccidentals(mode, FLAT, FIFTH, chord,
                        cg.romanNumeralsMajorArray[chord].concat(minusSign));
            }
        }
*/
        //no V+ in minor for the most part (they are enharmonic V13s)
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Minor_5(int accidental, int key, int inversionIndex) {
        //nothing yet
        noAccidentalsAdded = true;
        return rightAnswer;
    }
    public String getAndDisplayModalMixtureOrAppliedChords_Minor_6(int accidental, int key, int inversionIndex) {
        //make V7/III. Just for seventh chords and no accidentals need to be added; the chord is already
        //naturally an applied chord
        //should always happen regardless of applied chords checkbox checked or not
        if (isAppliedChords) {
            if (accidental > 1) {
                return cg.romanNumeralsMajorArray[4].concat(cg.seventhInversions[inversionIndex]).concat(cg.appliedsMinor[0]);
            }
        }
        noAccidentalsAdded = true;
        return rightAnswer;
    }
        //TODO for applied/modal/altered chords:
        //these augmented chords will certainly have accidental collisions in MAJOR only. but how will I handle chord inversions for the auggy6ths?
        //add only after having added another vertical column of naturals/sharps/flats as buffers
        //make italian6th (triad)
        //make fr6 (7th chord) //how to not confuse this with Vdim7? V+7 == FR6 / I. just leave it as V+7dim since there are only 4 options and never anything like FR6 / I as a wrong answer
        //make ger6 (7th chord)

        //7th chords only

        //V7/iii in major requires extra accidental buffers
        //make V7 / V in minor //won't work yet without a buffer row of accidentals

        //will viio7s require a new method for accidentals visible? yes, viio4/2/ii in c-major is Bb-C#-E-G, clash of the augmented 2nd between Bb-C#

        //make viio7 / ii in major
        //make viio7 / iii in major
        //make viio7 / IV in major
        //make viio7 / V in major
        //make viio7 / vi in major

        //make viio7 / III in minor
        //make viio7 / iv in minor
        //make viio7 / V in minor
        //make viio7 / VI in minor
        //make viio7 / VII in minor
}
