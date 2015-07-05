// Note: this program only interprets the chords at face value. It does not understand applied
// chords, passing chords, suspensions modulation, or any chord function in the Riemannian sense;
// analyze accordingly. For example, a Bb7 chord in C-minor will have to be answered literally as
// VII7 rather than something like "V7 of I in the relative major."

package com.nihk.github.musictheoryromannumeralanalysisquiz;

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

    //declare boring interface members
    private Button[] answerButtonArray;
    private TextView aTextView, bTextView, cTextView, dTextView,
            keyTextView, scorePointsTextView;
    private TextView[] qTextViews;
    private RadioButton easyRadio, hardRadio;
    private boolean stopCounting, isTrebleClef;
    private String rightAnswer, wrongAnswer, checkMark, xMark;
    private String[] wrongAnswerArray;
    private int rightAnswerIndex, correct, total, pink, green;
    //public static final String TAG = MTRNAQuizActivity.class.getSimpleName(); //for Logging errors

    //declare the music staff members
    private String dim, halfDim;
    private TextView trebleClef, bassClef;
    private TextView[] noteheads, noteheadsR;
    private TextView[][][] accidentals;

    //new object from a class in this package
    private ChordGenerator cg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtrnaquiz);

        //declarations and initializations of both member non-member vars
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

        checkMark = "\u2714";
        xMark = "\u2718";

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
        TextView[][] accidentalsA = new TextView[][] {
                sharpsTreb, flatsBass //this weird order just works out this way because of % 2 in the display key sig method
        };
        TextView[][] accidentalsB = new TextView[][] {
                sharpsBass, flatsTreb //this weird order just works out this way because of % 2 in the display key sig method
        };
        accidentals = new TextView[][][] {
                accidentalsA, accidentalsB
        };

        for (TextView[] acc: accidentalsB) {
            for (TextView tv: acc) {
                tv.setVisibility(View.INVISIBLE);
            }
        };
        bassClef.setVisibility(View.INVISIBLE);

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

        pink = Color.parseColor("#f2ac9c");
        green = Color.parseColor("#87e9b8");
        dim = "\u00B0";
        halfDim = "\u00F8";
       //float scale = getResources().getDisplayMetrics().density; //so I can use java to move layout elements by dp and not just px
        cg = new ChordGenerator();

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
        String neutonBoldPath = "fonts/Neuton-Bold.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), neutonBoldPath);
        for (Button b: answerButtonArray) {
            b.setTypeface(tf);
        }
        aTextView.setTypeface(tf);
        bTextView.setTypeface(tf);
        cTextView.setTypeface(tf);
        dTextView.setTypeface(tf);
        generateChordButton.setTypeface(tf);
        scoreTextView.setTypeface(tf);
        scorePointsTextView.setTypeface(tf);
        numAccidentalsTextView.setTypeface(tf);
        easyRadio.setTypeface(tf);
        hardRadio.setTypeface(tf);
        keyTextView.setTypeface(tf);

        getRandomChord(); //so when the app starts it will produce its first chord to be analysed
    }
    public void getRandomChord() {
        stopCounting = false; //score can go again up with each Next Chord button click
        isTrebleClef = false; //a reset so it can try for either treb or bass clef
        clearChecksAndXs(); //hide previous chords' wrong/right answer textviews

        //lengths of each dimension: chords[2][4][8][7]); all arrays meet those lengths, i.e. there are no ragged arrays,
        //so I can just use lengths of the zeroth index of things like chords[0].length with confidence
        int a = (int)(Math.random() * cg.chords.length), //chords.length == 2
                b = (int)(Math.random() * cg.chords[0].length), //chords[0].length == 4
                c = (int)(Math.random() * (easyRadio.isChecked() ? 4 : cg.chords[0][0].length)), //chords[0][0].length == 8
                d = (int)(Math.random() * cg.chords[0][0][0].length); //chords[0][0][0].length == 7
        String[] aRandomChord = cg.chords[a][b][c][d],
                aRandomChordShuffled = shuffleChord(aRandomChord);
        String bassNote = aRandomChordShuffled[0];
        int inversionIndex = Arrays.asList(aRandomChord).indexOf(bassNote);
        rightAnswer = (a == 0
                ? cg.romanNumeralsMajorArray[d]
                : cg.romanNumeralsMinorArray[d]).concat(
                b < 2 ? cg.triadicInversions[inversionIndex] //b < 2 because chords[][0] and chords[][1] are triads, chords[][2] and chords[][3] are 7th chords
                        : cg.seventhInversions[inversionIndex]
        );

        if (b >= 2 && rightAnswer.contains(dim)) { //if chord is a 7th chord (b==2 or b==3) and contains a degree, it's actually a half-diminished chord
            rightAnswer = rightAnswer.replace(dim, halfDim); //so replace the degree with a O WITH STROKE
        }

        //changeVII7toV7ofIII();

        keyTextView.setText(cg.keyNames[a][b % 2][c].concat(": ")); //again, b % 2 because the size of that array in keyNames != the parallel one in "chords"
        rightAnswerIndex = (int)(Math.random() * answerButtonArray.length);
        answerButtonArray[rightAnswerIndex].setText(rightAnswer);

        setNotationInvisible();
        displayKeySignature(b, c);
        displayPitches(aRandomChordShuffled, c, d);
        createWrongAnswers(aRandomChordShuffled.length, rightAnswerIndex);
    }
    public void displayPitches(String[] aRandomChordShuffled, int key, int chord) {
        int verticalIndex = 0;
        String[] everythingButBassNote = new String[aRandomChordShuffled.length - 1];

        //get every pitch from the shuffled chord except the bass note and sort them;
        //this is needed for a tightly packed chord input on the musical staff
        for (int i = 0; i < everythingButBassNote.length; i++) {
            everythingButBassNote[i] = aRandomChordShuffled[i + 1].substring(0, 1);
        }
        Arrays.sort(everythingButBassNote);

        //This is a hacky fix for a bug that happens with a B-A-D-F or B-G-D-F chord in the bass clef only. The staff runs out of space
        //after filling in the first two pitches of that chord, because there is no low B available below the bass staff in this music font.
        //Normally this would easily be possible to fix by hand, but arrays.sort sorts from A-Z for the non-bass-note pitches. I could just change arrays.sort
        //into something that starts from a pitch other than A, but this would cause even more unforeseen issues down the line.
        if (!isTrebleClef && aRandomChordShuffled[0].equals(cg.pitchClassesArray[6])
                && (everythingButBassNote[0].equals(cg.pitchClassesArray[4]) || everythingButBassNote[0].equals(cg.pitchClassesArray[5]))) {
            String firstElement = everythingButBassNote[0];
            for (int i = 1; i < everythingButBassNote.length; i++) {
                everythingButBassNote[i - 1] = everythingButBassNote[i];
            }
            everythingButBassNote[everythingButBassNote.length - 1] = firstElement;
        }

        //get the bass note of the shuffled chord and display it
        for (int i = isTrebleClef ? 5 : 0, j = 0; //trebclef ? start on pitchclass A, else start on pitchclass C
             j < cg.pitchClassesArray.length;
             i = (i + 1) % ChordGenerator.NUM_PITCH_CLASSES, j++) {
            if (cg.pitchClassesArray[i].equals(
                    aRandomChordShuffled[0].substring(0, 1))
                    ) {
                verticalIndex = j;
                noteheads[verticalIndex].setVisibility(View.VISIBLE);  //move to the right by num_accidentals * a constant?
                break;
            }
        }
        //input the remaining 2-3 pitches
        //confusing line below: the trebleclef lowest possible displayed note is A3, but pitchClassesArray starts at C, so I'll start at pitchClassesArray[(0 - 2) % 7] to begin on A instead,
        //but it might be a negative so + 7 (NUM_PITCH..) solves that, since I'm using modulus anyway (but modulus doesnt work on negative values)
        for (int i = isTrebleClef ? ((verticalIndex - 2 + ChordGenerator.NUM_PITCH_CLASSES) % ChordGenerator.NUM_PITCH_CLASSES) : verticalIndex, j = verticalIndex, k = 0;
             j < noteheads.length && k < everythingButBassNote.length;
             i = (i + 1) % ChordGenerator.NUM_PITCH_CLASSES, j++) {
            if (cg.pitchClassesArray[i].equals(everythingButBassNote[k])) {
                noteheads[j].setVisibility(View.VISIBLE);
                k++;
                if (j > 0 && noteheads[j - 1].getVisibility() == View.VISIBLE) { //if the pitch added before this one is an interval of a 2nd below
                    noteheads[j].setVisibility(View.INVISIBLE);
                    noteheadsR[j].setVisibility(View.VISIBLE);  //it, then it must shift to the right a little to make space
                }
            }
        }
    }
    //creates text for the three answerButtons which hold incorrect answer values
    public void createWrongAnswers(int chordSize, int rightAnswerIndex) {
        int randomRNIndex = 0,
                randomInversionIndex = 0,
                randomModeIndex = 0; //default to zero values for these.
        for (int i = 0, j = 0; i < answerButtonArray.length && j < wrongAnswerArray.length; i++) {
            if (i == rightAnswerIndex) {
                continue;
            } else {
                if (wrongAnswer != null) {
                    wrongAnswerArray[j] = wrongAnswer;
                    j++;
                }
                do {
                    randomRNIndex = (int)(Math.random() * cg.romanNumeralsMajorArray.length); //romanNumeralsMajorArray.length == romanNumeralsMinorArray.length
                    randomInversionIndex = (int)(Math.random() * chordSize);
                    randomModeIndex = (int)(Math.random() * cg.modeArray.length);
                    wrongAnswer = (randomModeIndex == 0 //randomModeIndex will be 0 or 1 by randomness.
                            ? cg.romanNumeralsMajorArray[randomRNIndex]
                            : cg.romanNumeralsMinorArray[randomRNIndex]).concat(
                            chordSize == cg.triadicInversions.length //chord a triad? wrong answer inversion should be triadic only. otherwise only 7th inversions.
                                    ? cg.triadicInversions[randomInversionIndex] //arr len = 3
                                    : cg.seventhInversions[randomInversionIndex] //arr len = 4
                    );
                } while (wrongAnswer.equalsIgnoreCase(rightAnswer) //avoids a duplicate right answer
                        || containsCaseInsensitive(wrongAnswer, wrongAnswerArray)  //avoids duplicate wrong answers
                        );
                if (chordSize == 4
                        && wrongAnswer.contains("\u00B0")) { //randomly give a wrong answer a half-diminished symbol, otherwise
                    if (Math.random() < 0.5) { //when a half-dim symbol appears, that'd always be the correct answer
                        wrongAnswer = wrongAnswer.replace("\u00B0", "\u00F8");
                    }
                }
                answerButtonArray[i].setText(wrongAnswer);
            }
        }
    }
    public void displayKeySignature(int sharpsOrFlats, int numAccidentals) {
        int bassOrTreble = (int)(Math.random() * 2); //randomly choose between treble and bass clef
        if (bassOrTreble == 0) {
            trebleClef.setVisibility(View.VISIBLE);
            isTrebleClef = true;
        } else {
            bassClef.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < numAccidentals; i++) {
            accidentals[bassOrTreble][sharpsOrFlats % 2][i].setVisibility(View.VISIBLE);
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
    public void clearChecksAndXs() {
        for (TextView tv: qTextViews) {
            tv.setTextColor(Color.parseColor("#f9f9f9"));
        }
    }
    //"reset" the clef / key signature / noteheads
    public void setNotationInvisible() {
        trebleClef.setVisibility(View.INVISIBLE);
        bassClef.setVisibility(View.INVISIBLE);
        for (TextView[][] clef: accidentals) {
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
    }
    public void changeCounterAndCheckmarks(TextView tv, int clickedButton) {
        if (rightAnswer != null && rightAnswerIndex == clickedButton) {
            if (!stopCounting) {
                correct++;
                total++;
                scorePointsTextView.setText(correct + " / " + total);
                stopCounting = true;
            }
            tv.setText(checkMark);
            tv.setTextColor(green);
        } else if (rightAnswer != null) {
            if (!stopCounting) {
                total++;
                scorePointsTextView.setText(correct + " / " + total);
                stopCounting = true;
            }
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
