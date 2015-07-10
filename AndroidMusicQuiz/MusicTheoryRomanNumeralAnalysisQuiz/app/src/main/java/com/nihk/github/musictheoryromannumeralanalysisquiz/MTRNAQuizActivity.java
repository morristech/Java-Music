package com.nihk.github.musictheoryromannumeralanalysisquiz;

/*
* TODO add applied chords and modal mixture, these should be 1/5 or 1/10 chance
* use an applied chord boolean so the wrong answers can have applied RNs too
*   slight problem: accidentals can have collisions when the noteheads are in some inversion
*   where they get tightly packed together..seems to be okay so far
*
*   NOTE WELL BEFORE MAKING CHANGES:
*   I've commented out the VII conversion and v --> V conversion
*   also, in ChordGenerator it is still "v", i.e. not V
*   all the layout files have a wider music notation because of the addition of accidentals
*       this might cause problems for some screens, its untested
*       if editing for a small update, use the files in the GitHub folder instead!!
*       ALSO all answer boxes will need much wider text. do all layouts fit?
*
* make options pane
* key sig accidentals, more options: 0-1, 0-3, 0-5, All (0-7)
* applied chords checkbox //don't forget to include V7 / III on/off for this? think about this. applied chords off means VII7, do I want that?
* modal mixture checkbox
* what about ONLY modal mixture or ONLY applied chords?
* make wrong answer RNs only ones in the same key as the right answer...or dont?
* wrong answers need some chance of being applied/mixed at any rate
*
* reoorganize the appliedChordMethod to better take parameters. it can take right/wrongAnswer as params, but what about the index?
*
* applied chords, modal mixture, augmented/diminished V chords, augmented6th chords
*
* 3 modes easy (just chords in key)  or just everything
* medium (applied chords, bII)
* hard (modal mixture, augmented/dim chords, auggy6th)
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MTRNAQuizActivity extends ActionBarActivity {
    private Button[] answerButtonArray;
    private TextView aTextView, bTextView, cTextView, dTextView,
            keyTextView, scorePointsTextView, trebleClef, bassClef, altoClef;
    private TextView[] qTextViews, noteheads, noteheadsR;
    private TextView[][] noteheadsAccidentals;
    private TextView[][][] keySigAccidentals;
    private RadioButton easyRadio, hardRadio;
    private boolean stopCounting, isTrebleClef, isBassClef;
    private String rightAnswer, wrongAnswer, checkMark, xMark, dim, halfDim, colonSpace, bigII;
    private String[] wrongAnswerArray, displayedRandomChord;
    private int rightAnswerIndex, correct, total, pink, green;
    public static final int ROOT = 0, THIRD = 1, FIFTH = 2, SEVENTH = 3,
        NATURAL_OR_NOTHING = 0, SHARP = 1, FLAT = 2;
    private int[] verticalIndices, shuffledChordOrderOfIntervals;
    //public static final String TAG = MTRNAQuizActivity.class.getSimpleName(); //for Logging errors
    private ChordGenerator cg;
    private WrongAnswerGenerator wag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtrnaquiz);

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

        qTextViews = new TextView[] {
                aTextView, bTextView, cTextView, dTextView
        };

        Button generateChordButton = (Button)findViewById(R.id.generateChordButton);
        Button refreshButton = (Button)findViewById(R.id.refreshButton);

        keyTextView = (TextView)findViewById(R.id.keyTextView);
        easyRadio = (RadioButton)findViewById(R.id.easyRadio);
        hardRadio = (RadioButton)findViewById(R.id.hardRadio);
        TextView scoreTextView = (TextView)findViewById(R.id.scoreTextView);
        scorePointsTextView = (TextView)findViewById(R.id.scorePointsTextView);
        TextView numAccidentalsTextView = (TextView)findViewById(R.id.numAccidentalsTextView);

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
        generateChordButton.setOnClickListener(gcListener);
        answerButton1.setOnClickListener(a1Listener);
        answerButton2.setOnClickListener(a2Listener);
        answerButton3.setOnClickListener(a3Listener);
        answerButton4.setOnClickListener(a4Listener);
        refreshButton.setOnClickListener(refreshListener);

        //altering the xml layout with javacode
        hardRadio.setChecked(true);
        Typeface neutonFont = Typeface.createFromAsset(getAssets(), "fonts/Neuton-Bold.ttf"); //font doesn't have the check/X mark characters
        for (Button b: answerButtonArray) {
            b.setTypeface(neutonFont);
        }
        generateChordButton.setTypeface(neutonFont);
        scoreTextView.setTypeface(neutonFont);
        scorePointsTextView.setTypeface(neutonFont);
        numAccidentalsTextView.setTypeface(neutonFont);
        easyRadio.setTypeface(neutonFont);
        hardRadio.setTypeface(neutonFont);
        keyTextView.setTypeface(neutonFont);

        /* comment this out if I ever revisit using applied chords */
        for (TextView[] accType: noteheadsAccidentals) {
            for (TextView acc: accType) {
                acc.setVisibility(View.INVISIBLE);
            }
        }

        getRandomChord(); //so when the app starts it will produce its first chord to be analysed
    }
    public void getRandomChord() {
        resetThings(); //in preparation for the new chord

        //lengths of each dimension: chords[2][4][8][7]); all arrays meet those lengths, i.e. there are no ragged arrays,
        //so I can just use lengths of the zeroth index of things like chords[0].length with confidence
        int mode = (int)(Math.random() * cg.chords.length), //chords.length == 2
                accidentalAndChordSize = (int)(Math.random() * cg.chords[0].length), //chords[0].length == 4
                key = (int)(Math.random() * (easyRadio.isChecked() ? 4 : cg.chords[0][0].length)), //chords[0][0].length == 8
                chord = (int)(Math.random() * cg.chords[0][0][0].length); //chords[0][0][0].length == 7
        String[] aRandomChord = cg.chords[mode][accidentalAndChordSize][key][chord],
                aRandomChordShuffled = shuffleChord(aRandomChord);
        String newBassNoteFromShuffling = aRandomChordShuffled[0];
        int inversionIndex = Arrays.asList(aRandomChord).indexOf(newBassNoteFromShuffling);
        rightAnswer = (mode == ChordGenerator.MAJOR_MODE
                    ? cg.romanNumeralsMajorArray[chord]
                    : cg.romanNumeralsMinorArray[chord]
                ).concat(accidentalAndChordSize < 2 //concat a potential inversion to the RN
                    ? cg.triadicInversions[inversionIndex] //b < 2 because chords[][0] and chords[][1] are triads, chords[][2] and chords[][3] are 7th chords
                    : cg.seventhInversions[inversionIndex]
        );

        //if the chord is VII7 in minor, I'm pretty sure a smaller majority of the music theory population
        //would instead analyze it as V7 / III. this changes that
        if (mode == ChordGenerator.MINOR_MODE && accidentalAndChordSize >= 2 && chord == 6) { // >= 2 means a seventh chord, 6 is the VII RN in the minor roman numeral array.
            rightAnswer = changeVII7Chords(rightAnswer);
        }

        //write the keyname to the keyTextView
        keyTextView.setText(cg.keyNames[mode][accidentalAndChordSize % 2][key].concat(colonSpace)); //again, b % 2 because the size of that array in keyNames != the parallel one in "chords"
        //give the right answer to a random button.
        rightAnswerIndex = (int)(Math.random() * answerButtonArray.length);
        answerButtonArray[rightAnswerIndex].setText(rightAnswer);

        displayKeySignature(accidentalAndChordSize, key);
        displayPitches(aRandomChordShuffled, mode, accidentalAndChordSize, key, chord);
        makeModalMixtureOrAppliedChords(aRandomChord, mode, accidentalAndChordSize, key, chord);
        createWrongAnswers();
    }
    //change VII 7th chords in minor into applieds. No extra accidentals needed!
    public String changeVII7Chords(String answer) {
        answer = answer.replace(cg.romanNumeralsMinorArray[6], cg.romanNumeralsMajorArray[4])
                .concat(cg.appliedsMinor[0]); //replace VII with V and then add " / III"
        return answer;
    }
    public void displayKeySignature(int sharpsOrFlats, int numAccidentals) {
        double bassOrTrebleOrAlto = Math.random(); //randomly choose between treble, bass, and alto clef
        int randomClef = -1; //-1 for failsafe
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
    //put an accidental on 1 pitch of the chord and change the rightAnswer to reflect it
    public void replaceWithModalMixtureOrAppliedChord(int mode, int accidental, int chordMember, int chord, String replaceWith) {
        noteheadsAccidentals[accidental][verticalIndices[shuffledChordOrderOfIntervals[chordMember]]].setVisibility(View.VISIBLE);
        rightAnswer = rightAnswer.replace((mode == ChordGenerator.MAJOR_MODE ? cg.romanNumeralsMajorArray[chord] : cg.romanNumeralsMinorArray[chord]), replaceWith);
        answerButtonArray[rightAnswerIndex].setText(rightAnswer);
    }
    //put an accidental on 1 pitch of the chord and don't change the RN String name
    public void replaceWithModalMixtureOrAppliedChord(int accidental, int chordMember) {
        noteheadsAccidentals[accidental][verticalIndices[shuffledChordOrderOfIntervals[chordMember]]].setVisibility(View.VISIBLE);
    }
    //put an accidental on 2 pitches of the chord
    public void replaceWithModalMixtureOrAppliedChord(
            int mode, int firstAccidental, int secondAccidental, int firstChordMember, int secondChordMember, int chord, String replaceWith
    ) {
        noteheadsAccidentals[firstAccidental][verticalIndices[shuffledChordOrderOfIntervals[firstChordMember]]].setVisibility(View.VISIBLE);
        noteheadsAccidentals[secondAccidental][verticalIndices[shuffledChordOrderOfIntervals[secondChordMember]]].setVisibility(View.VISIBLE);
        rightAnswer = rightAnswer.replace((mode == ChordGenerator.MAJOR_MODE ? cg.romanNumeralsMajorArray[chord] : cg.romanNumeralsMinorArray[chord]), replaceWith);
        answerButtonArray[rightAnswerIndex].setText(rightAnswer);
    }
    //put an accidental on 3 pitches of the chord
    public void replaceWithModalMixtureOrAppliedChord(
            int mode, int firstAccidental, int secondAccidental , int thirdAccidental,
            int firstChordMember, int secondChordMember, int thirdChordMember,
            int chord, String replaceWith
    ) {
        noteheadsAccidentals[firstAccidental][verticalIndices[shuffledChordOrderOfIntervals[firstChordMember]]].setVisibility(View.VISIBLE);
        noteheadsAccidentals[secondAccidental][verticalIndices[shuffledChordOrderOfIntervals[secondChordMember]]].setVisibility(View.VISIBLE);
        noteheadsAccidentals[thirdAccidental][verticalIndices[shuffledChordOrderOfIntervals[thirdChordMember]]].setVisibility(View.VISIBLE);
        rightAnswer = rightAnswer.replace((mode == ChordGenerator.MAJOR_MODE ? cg.romanNumeralsMajorArray[chord] : cg.romanNumeralsMinorArray[chord]), replaceWith);
        answerButtonArray[rightAnswerIndex].setText(rightAnswer);
    }
    //make this its own class that returns a String RN?
    public void makeModalMixtureOrAppliedChords(String[] aRandomChord, int mode, int accidental, int key, int chord) {
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

        //System.out.println("displayedRandomChord = " + Arrays.toString(displayedRandomChord));
        //System.out.println("shuffledChordOrder.. = " + Arrays.toString(shuffledChordOrderOfIntervals));

        //make v into V for sharp keys (applies to accidental % 2 == 0), they all have raised third with a #
        //if mode == minor and accidental == sharp triads or sharp sevenths and keys from A-minor to C#-minor and chord == v
        //chord == 4, this is index 4 in the RN array == v or V
        if (mode == ChordGenerator.MINOR_MODE && chord == 4) {
            if ((accidental % 2 == 0) && key <= 4) {
                replaceWithModalMixtureOrAppliedChord(mode, SHARP, THIRD, chord,
                        cg.romanNumeralsMajorArray[chord]);
            }
            //make v into V for flat keys (all keys work, but take diff acc), they have either a raised third with # or natural sign
            if (accidental % 2 == 1) {
                //from A-minor to G-minor take sharps as leading tones
                if (key <= 2) {
                    replaceWithModalMixtureOrAppliedChord(mode, SHARP, THIRD, chord,
                            cg.romanNumeralsMajorArray[chord]);
                    //from C-minor to Ab-minor take naturals as leading tones
                } else {
                    replaceWithModalMixtureOrAppliedChord(mode, NATURAL_OR_NOTHING, THIRD, chord,
                            cg.romanNumeralsMajorArray[chord]);
                }
            }
        }

        //something needs to be done to the ii chord of the major mode. chance will determine whether
        //it becomes iidim/halfdim7 or bII
        if (Math.random() < 0.5) {
            //make iidim/halfDim7 (the RN will have to change its degree/o with stroke accordingly)
            if (mode == ChordGenerator.MAJOR_MODE && chord == 1) {
                if (accidental == 0) {
                    //from keys C-major to D-major need all flats
                    if (key <= 2) {
                        replaceWithModalMixtureOrAppliedChord(mode, FLAT, FIFTH, chord, cg.romanNumeralsMinorArray[chord]);
                        //the rest, from A-major to C#-major just use naturals for that
                    } else {
                        replaceWithModalMixtureOrAppliedChord(mode, NATURAL_OR_NOTHING, FIFTH, chord, cg.romanNumeralsMinorArray[chord]);
                    }
                }
                //seventh chords need to use the half dim symbol
                if (accidental == 2) {
                    //from keys C-major to D-major need all flats
                    if (key <= 2) {
                        replaceWithModalMixtureOrAppliedChord(mode, FLAT, FIFTH, chord, cg.romanNumeralsMajorArray[chord].concat(halfDim));
                        //the rest, from A-major to C#-major just use naturals for that
                    } else {
                        replaceWithModalMixtureOrAppliedChord(mode, NATURAL_OR_NOTHING, FIFTH, chord, cg.romanNumeralsMajorArray[chord].concat(halfDim));
                    }
                }
                //from keys C-major to Ab-major just use flats for the 3rd and 7th of iv7
                if ((accidental == 1) && key <= 4) {
                    replaceWithModalMixtureOrAppliedChord(mode, FLAT, FIFTH, chord, cg.romanNumeralsMajorArray[chord].concat(halfDim));
                }
                if ((accidental == 3) && key <= 4) {
                    replaceWithModalMixtureOrAppliedChord(mode, FLAT, FIFTH, chord, cg.romanNumeralsMajorArray[chord].concat(halfDim));
                }
            }
        } else {
            //make bII in major keys
            if (mode == ChordGenerator.MAJOR_MODE && chord == 1) {
                if (accidental % 2 == 0) {
                    //from keys C-major to D-major need all flats
                    if (key == 2) {
                        replaceWithModalMixtureOrAppliedChord(mode, FLAT, FLAT, ROOT, FIFTH, chord,
                                cg.accidentalsArray[FLAT].concat(bigII));
                        //A-major is a special case that needs a flat for root and natural for fifth
                    } else if (key == 3) {
                        replaceWithModalMixtureOrAppliedChord(mode, FLAT, NATURAL_OR_NOTHING, ROOT, FIFTH, chord,
                                cg.accidentalsArray[FLAT].concat(bigII));
                        //the rest, from E-major to C#-major just use naturals for that
                    } else {
                        replaceWithModalMixtureOrAppliedChord(mode, NATURAL_OR_NOTHING, NATURAL_OR_NOTHING, ROOT, FIFTH, chord,
                                cg.accidentalsArray[FLAT].concat(bigII));
                    }
                }
                //from keys C-major to Eb-major just use flats.
                if ((accidental % 2 == 1) && key <= 3) {
                    replaceWithModalMixtureOrAppliedChord(mode, FLAT, FLAT, ROOT, FIFTH, chord,
                            cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                }
            }
        }

        //make bIII in major keys
        if (mode == ChordGenerator.MAJOR_MODE && chord == 2) {
            if (accidental % 2 == 0) {
                //from keys C-major
                if (key == 0) {
                    replaceWithModalMixtureOrAppliedChord(mode, FLAT, FLAT, ROOT, FIFTH, chord,
                            cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                    //G-major is a special case that needs a flat for root and natural for fifth
                } else if (key == 1) {
                    replaceWithModalMixtureOrAppliedChord(mode, FLAT, NATURAL_OR_NOTHING, ROOT, FIFTH, chord,
                            cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                    //the rest, from A-major to C#-major just use naturals for that
                } else {
                    replaceWithModalMixtureOrAppliedChord(mode, NATURAL_OR_NOTHING, NATURAL_OR_NOTHING, ROOT, FIFTH, chord,
                            cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                }
            }
            //from keys C-major to Db-major just use flats.
            if ((accidental % 2 == 1) && key <= 5) {
                replaceWithModalMixtureOrAppliedChord(mode, FLAT, FLAT, ROOT, FIFTH, chord,
                        cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
            }
        }

        //make iv in major keys
        if (mode == ChordGenerator.MAJOR_MODE && chord == 3) {
            if (accidental % 2 == 0) {
                //from keys C-major to D-major need all flats
                if (key <= 2) {
                    replaceWithModalMixtureOrAppliedChord(mode, FLAT, THIRD, chord, cg.romanNumeralsMinorArray[chord]);
                    //the rest, from A-major to C#-major just use naturals for that
                } else {
                    replaceWithModalMixtureOrAppliedChord(mode, NATURAL_OR_NOTHING, THIRD, chord, cg.romanNumeralsMinorArray[chord]);
                }
            }
            //special case for 7th chords, becuse the 7th of the chord needs to be altered if the randomly generate chord is a length of 4 (i.e.7th chord)
            if (accidental == 2) {
                //C major and Gmajor have a flat 7th within iv7
                if (key <= 1) {
                    replaceWithModalMixtureOrAppliedChord(FLAT, SEVENTH);
                    //The rest use a natural to lower the 7th within their respective iv7
                } else {
                    replaceWithModalMixtureOrAppliedChord(NATURAL_OR_NOTHING, SEVENTH);
                }
            }
            //from keys C-major to Ab-major just use flats for the 3rd and 7th of iv7
            if ((accidental % 2 == 1) && key <= 4) {
                replaceWithModalMixtureOrAppliedChord(mode, FLAT, THIRD, chord, cg.romanNumeralsMinorArray[chord]);
            }
            if ((accidental == 3) && key <= 4) {
                replaceWithModalMixtureOrAppliedChord(FLAT, SEVENTH);
            }
        }

        //make bVI in major keys
        if (mode == ChordGenerator.MAJOR_MODE && chord == 5) {
            if (accidental % 2 == 0) {
                //from keys C-major to G-major need all flats
                if (key <= 1) {
                    replaceWithModalMixtureOrAppliedChord(mode, FLAT, FLAT, ROOT, FIFTH, chord,
                            cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                    //D-major is a special case that needs a flat for root and natural for fifth
                } else if (key == 2) {
                    replaceWithModalMixtureOrAppliedChord(mode, FLAT, NATURAL_OR_NOTHING, ROOT, FIFTH, chord,
                            cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                    //the rest, from A-major to C#-major just use naturals for that
                } else {
                    replaceWithModalMixtureOrAppliedChord(mode, NATURAL_OR_NOTHING, NATURAL_OR_NOTHING, ROOT, FIFTH, chord,
                            cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                }
            }
            //from keys C-major to Ab-major just use flats
            if ((accidental % 2 == 1) && key <= 4) {
                replaceWithModalMixtureOrAppliedChord(mode, FLAT, FLAT, ROOT, FIFTH, chord,
                        cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
            }
        }

        if (Math.random() > 0.5) {
            //make bVII in major keys
            if (mode == ChordGenerator.MAJOR_MODE && chord == 6) {
                if (accidental % 2 == 0) {
                    //the key of C-major needs all flats
                    if (key == 0) {
                        replaceWithModalMixtureOrAppliedChord(mode, FLAT, ROOT, chord,
                                cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                        //the rest, from G-major to C#-major just use naturals for that
                    } else {
                        replaceWithModalMixtureOrAppliedChord(mode, NATURAL_OR_NOTHING, ROOT, chord,
                                cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                    }
                }
                //from keys C-major to Gb-major just use flats.
                if ((accidental % 2 == 1) && key <= 6) {
                    replaceWithModalMixtureOrAppliedChord(mode, FLAT, ROOT, chord,
                            cg.accidentalsArray[FLAT].concat(cg.romanNumeralsMinorArray[chord]));
                }
            }
        } else {
            //just change the viio7 to viihalfdim7
            //so replace the degree with a O WITH STROKE
            if (mode == ChordGenerator.MAJOR_MODE && chord == 6 && accidental > 1) {
                rightAnswer = rightAnswer.replace(dim, halfDim);
                answerButtonArray[rightAnswerIndex].setText(rightAnswer);
            }
        }

        //just change the iio7 to viihalfdim7
        //so replace the degree with a O WITH STROKE
        if (mode == ChordGenerator.MINOR_MODE && chord == 1 && accidental > 1) { // > 1 means seventh chords only
            rightAnswer = rightAnswer.replace(dim, halfDim);
            answerButtonArray[rightAnswerIndex].setText(rightAnswer);
        }




        //make bII in minor (in major too?)
        //make IV in minor

        //make V+
        //make Vdim

        //these augmented chords will certainly have accidental collisions somewhere. but they will be limited by the b6 and #4 possibilities (no doubles)
        //so it might be OK
        //make italian6th (triad)
        //make fr6 (7th chord) //how to not confuse this with Vdim7? V+7 == FR6 / I. just leave it as V+7dim since there are only 4 options and never anything like FR6 / I as a wrong answer
        //make ger6 (7th chord)


        //7th chords only
        //make V7 / ii in major
        //make V7 / iii in major
        //make V7 / IV in major
        //make V7 / V in major
        //make V7 / vi in major

        //V7 / III already done
        //make V7 / iv in minor
        //make V7 / V in minor
        //make V7 / VI in minor
        //make V7 / VII in minor

        //will viio7s require a new method for accidentals visible?

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
    //this method too many lines?
    public void displayPitches(String[] aRandomChordShuffled, int mode, int accidental, int key, int chord) {
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
                    wrongAnswer = wag.getRandomWrongAnswer();
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
        List<String> arrList = new ArrayList<String>(Arrays.asList(arr));
        Collections.shuffle(arrList);
        return arrList.toArray(new String[arrList.size()]);
    }
}
