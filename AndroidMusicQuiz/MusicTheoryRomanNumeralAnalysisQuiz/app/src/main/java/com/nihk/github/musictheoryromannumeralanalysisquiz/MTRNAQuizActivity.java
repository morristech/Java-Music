package com.nihk.github.musictheoryromannumeralanalysisquiz;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class MTRNAQuizActivity extends ActionBarActivity {

    //Boring interface members
    private Button answerButton1, answerButton2,
            answerButton3, answerButton4,
            generateChordButton, refreshButton;
    private Button[] answerButtonArray;
    private TextView aTextView, bTextView, cTextView, dTextView,
            keyTextView, notesTextView, scoreTextView,
            scorePointsTextView, numTextView, acciTextView,
            dentalsTextView;
    private TextView[] qTextViews;
    private RadioButton easyRadio, hardRadio;
    //private ToggleGroup difficultyRadios;
    private boolean a1, a2, a3, a4, stopCounting;
    private String rightAnswer, wrongAnswer;
    private String[] wrongAnswerArray;
    private int rightAnswerIndex, correct, total;
    private float scale;
    public static final String TAG = MTRNAQuizActivity.class.getSimpleName(); //for Logging errors

    //Music staff members
    private TextView trebleClef, bassClef,
            staffLines,
            fSharp, cSharp, gSharp, dSharp, aSharp, eSharp, bSharp,
            bFlat, eFlat, aFlat, dFlat, gFlat, cFlat, fFlat,
            notehead0, notehead1, notehead2, notehead3,
            notehead4, notehead5, notehead6, notehead7,
            notehead8, notehead9, notehead10, notehead11,
            notehead12, notehead13,
            ledgerLineC4, suppLedgerLineC4;
    private TextView[] sharps, flats, noteheads;
    private TextView[][] accidentals;
    private String freeSerifPath;
    private Typeface tf;

    //Music quiz members
    private ChordGenerator cg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtrnaquiz);

        //instantiations
        cg = new ChordGenerator();
        scale = getResources().getDisplayMetrics().density; //so I can use java to move by dp and not just px
        trebleClef = (TextView)findViewById(R.id.trebleClef);
        generateChordButton = (Button)findViewById(R.id.generateChordButton);
        keyTextView = (TextView)findViewById(R.id.keyTextView);
        easyRadio = (RadioButton)findViewById(R.id.easyRadio);
        hardRadio = (RadioButton)findViewById(R.id.hardRadio);
        answerButton1 = (Button)findViewById(R.id.answerButton1);
        answerButton2 = (Button)findViewById(R.id.answerButton2);
        answerButton3 = (Button)findViewById(R.id.answerButton3);
        answerButton4 = (Button)findViewById(R.id.answerButton4);
        answerButtonArray = new Button[] {answerButton1,
                answerButton2, answerButton3, answerButton4};
        scorePointsTextView = (TextView)findViewById(R.id.scorePointsTextView);
        aTextView = (TextView)findViewById(R.id.aTextView);
        bTextView = (TextView)findViewById(R.id.bTextView);
        cTextView = (TextView)findViewById(R.id.cTextView);
        dTextView = (TextView)findViewById(R.id.dTextView);
        qTextViews = new TextView[] {
                aTextView, bTextView, cTextView, dTextView
        };
        refreshButton = (Button)findViewById(R.id.refreshButton);
        trebleClef = (TextView)findViewById(R.id.trebleClef);
        staffLines = (TextView)findViewById(R.id.staffLines);

        fSharp = (TextView)findViewById(R.id.fSharp);
        cSharp = (TextView)findViewById(R.id.cSharp);
        gSharp = (TextView)findViewById(R.id.gSharp);
        dSharp = (TextView)findViewById(R.id.dSharp);
        aSharp = (TextView)findViewById(R.id.aSharp);
        eSharp = (TextView)findViewById(R.id.eSharp);
        bSharp = (TextView)findViewById(R.id.bSharp);

        bFlat = (TextView)findViewById(R.id.bFlat);
        eFlat = (TextView)findViewById(R.id.eFlat);
        aFlat = (TextView)findViewById(R.id.aFlat);
        dFlat = (TextView)findViewById(R.id.dFlat);
        gFlat = (TextView)findViewById(R.id.gFlat);
        cFlat = (TextView)findViewById(R.id.cFlat);
        fFlat = (TextView)findViewById(R.id.fFlat);

        sharps = new TextView[] {
                fSharp, cSharp, gSharp, dSharp,
                aSharp, eSharp, bSharp
        };
        flats = new TextView[] {
                bFlat, eFlat, aFlat,
                dFlat, gFlat, cFlat, fFlat
        };
        accidentals = new TextView[][]{
                sharps, flats
        };

        notehead0 = (TextView)findViewById(R.id.notehead0);
        notehead1 = (TextView)findViewById(R.id.notehead1);
        notehead2 = (TextView)findViewById(R.id.notehead2);
        notehead3 = (TextView)findViewById(R.id.notehead3);
        notehead4 = (TextView)findViewById(R.id.notehead4);
        notehead5 = (TextView)findViewById(R.id.notehead5);
        notehead6 = (TextView)findViewById(R.id.notehead6);
        notehead7 = (TextView)findViewById(R.id.notehead7);
        notehead8 = (TextView)findViewById(R.id.notehead8);
        notehead9 = (TextView)findViewById(R.id.notehead9);
        notehead10 = (TextView)findViewById(R.id.notehead10);
        notehead11 = (TextView)findViewById(R.id.notehead11);
        notehead12 = (TextView)findViewById(R.id.notehead12);
        notehead13 = (TextView)findViewById(R.id.notehead13);

        noteheads = new TextView[] {
                notehead0, notehead1,
                notehead2, notehead3, notehead4,
                notehead5, notehead6, notehead7,
                notehead8, notehead9, notehead10,
                notehead11, notehead12, notehead13,
        };

        ledgerLineC4 = (TextView)findViewById(R.id.ledgerLineC4);
        suppLedgerLineC4 = (TextView)findViewById(R.id.suppLedgerLineC4);

        //set listeners
        View.OnClickListener gcListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomChord();
            }
        };
        View.OnClickListener a1Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightAnswer != null && rightAnswerIndex == 0) {
                    if (!stopCounting) {
                        correct++;
                        total++;
                        scorePointsTextView.setText(correct + " / " + total);
                        stopCounting = true;
                    }
                    aTextView.setText("\u2714");
                    aTextView.setTextColor(Color.parseColor("#87e9b8"));
                } else if (rightAnswer != null) {
                    if (!stopCounting) {
                        total++;
                        scorePointsTextView.setText(correct + " / " + total);
                        stopCounting = true;
                    }
                    aTextView.setText("\u2718");
                    aTextView.setTextColor(Color.parseColor("#f2ac9c"));
                }
            }
        };
        View.OnClickListener a2Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightAnswer != null && rightAnswerIndex == 1) {
                    if (!stopCounting) {
                        correct++;
                        total++;
                        scorePointsTextView.setText(correct + " / " + total);
                        stopCounting = true;
                    }
                    bTextView.setText("\u2714");
                    bTextView.setTextColor(Color.parseColor("#87e9b8"));
                } else if (rightAnswer != null) {
                    if (!stopCounting) {
                        total++;
                        scorePointsTextView.setText(correct + " / " + total);
                        stopCounting = true;
                    }
                    bTextView.setText("\u2718");
                    bTextView.setTextColor(Color.parseColor("#f2ac9c"));
                }
            }
        };
        View.OnClickListener a3Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightAnswer != null && rightAnswerIndex == 2) {
                    if (!stopCounting) {
                        correct++;
                        total++;
                        scorePointsTextView.setText(correct + " / " + total);
                        stopCounting = true;
                    }
                    cTextView.setText("\u2714");
                    cTextView.setTextColor(Color.parseColor("#87e9b8"));
                } else if (rightAnswer != null) {
                    if (!stopCounting) {
                        total++;
                        scorePointsTextView.setText(correct + " / " + total);
                        stopCounting = true;
                    }
                    cTextView.setText("\u2718");
                    cTextView.setTextColor(Color.parseColor("#f2ac9c"));
                }
            }
        };
        View.OnClickListener a4Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightAnswer != null && rightAnswerIndex == 3) {
                    if (!stopCounting) {
                        correct++;
                        total++;
                        scorePointsTextView.setText(correct + " / " + total);
                        stopCounting = true;
                    }
                    dTextView.setText("\u2714");
                    dTextView.setTextColor(Color.parseColor("#87e9b8"));
                } else if (rightAnswer != null) {
                    if (!stopCounting) {
                        total++;
                        scorePointsTextView.setText(correct + " / " + total);
                        stopCounting = true;
                    }
                    dTextView.setText("\u2718");
                    dTextView.setTextColor(Color.parseColor("#f2ac9c"));
                }
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

        //object settings
        hardRadio.setChecked(true);
        trebleClef.setText("\uD834\uDD1E"); //for some reason xml doesn't lke these "paired" unicode strings
        staffLines.setText("\uD834\uDD1A\uD834\uDD1A\uD834\uDD1A\uD834\uDD1A");
        for (TextView tv: noteheads) {
            tv.setText("\uD834\uDD5D");
        }

/*
        //font
        freeSerifPath = "fonts/FreeSerif.ttf";
        trebleClef = (TextView)findViewById(R.id.trebleClef);
        tf = Typeface.createFromAsset(getAssets(), freeSerifPath);
        trebleClef.setTypeface(tf); */
        String firaMonoPath = "fonts/FiraMono-Bold.ttf";
        for (Button b: answerButtonArray) {
            b.setTypeface(Typeface.createFromAsset(getAssets(), firaMonoPath));
        }


        getRandomChord();

    }
    public void getRandomChord() {
        stopCounting = false;
        clearChecksAndXs();

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

        if (b >= 2 && rightAnswer.contains("\u00B0")) { //if chord is a 7th chord (b==2 or b==3) and contains a degree, it's actually a half-diminished chord
            rightAnswer = rightAnswer.replace("\u00B0", "\u00F8"); //so replace the degree with a O WITH STROKE
        }

        //changeVII7toV7ofIII();

        keyTextView.setText(cg.keyNames[a][b % 2][c].concat(": ")); //again, b % 2 because the size of that array in keyNames != the parallel one in "chords"
        rightAnswerIndex = (int)(Math.random() * answerButtonArray.length);
        answerButtonArray[rightAnswerIndex].setText(rightAnswer);

        setNotationInvisible();
        displayPitches(aRandomChordShuffled, c, d);
        displayKeySignature(b, c);
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

        //get the bass note of the shuffled chord and display it and the
        //C4 ledger line if necessary
        for (int i = 6, j = 0;
             j < cg.pitchClassesArray.length;
             i = (i + 1) % cg.NUM_PITCH_CLASSES, j++) { //i = 6 because noteheads starts at "B", while the pitchClassesArray starts at "C"
            if (cg.pitchClassesArray[i].equals(
                    aRandomChordShuffled[0].substring(0, 1))
                    ) {
                verticalIndex = j;
                noteheads[verticalIndex].setVisibility(View.VISIBLE);  //move to the right by num_accidentals * a constant?
                if (verticalIndex == 0 || verticalIndex == 1) { //if the pitch is either B3 or C4 it needs a ledger line
                    ledgerLineC4.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
        //for B-C-E-G the ledger line needs to be double the length. use a supplementary ledger
        //input the remaining 2-3 pitches
        for (int i = verticalIndex, j = verticalIndex + 1, k = 0; //j = verticalIndex + 1 again because noteheads starts at "B" while pitchClassesArray starts at "C"
             j < noteheads.length && k < everythingButBassNote.length; //this could be much more readable if I created an alternate pitchClassesArray that started on "B"
             i = (i + 1) % cg.NUM_PITCH_CLASSES, j++) {
            if (cg.pitchClassesArray[i].equals(everythingButBassNote[k])) {
                noteheads[j].setVisibility(View.VISIBLE);
                k++;
                if (j > 0 && noteheads[j - 1].getVisibility() == View.VISIBLE) { //if the pitch added before this one is an interval of a 2nd below
                    noteheads[j].setTranslationX(22 * scale + 0.5f); //it, then it must shift to the right a little to make space
                    if (j == 1) { //if the baseNote is "B" and the next pitch to be added is "C", a huge ledger line is needed
                        suppLedgerLineC4.setVisibility(View.VISIBLE); //supplementary ledger
                    }
                }
            }
        }
    }
    public void createWrongAnswers(int chordSize, int rightAnswerIndex) {
        wrongAnswerArray = new String[answerButtonArray.length - 1]; //4 buttons, so (4 - 1) wrong answers
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
        for (int i = 0; i < numAccidentals; i++) {
            accidentals[sharpsOrFlats % 2][i].setVisibility(View.VISIBLE);
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
    //"reset" the key signature / noteheads
    public void setNotationInvisible() {
        for (TextView[] mode: accidentals) {
            for (TextView acc: mode) {
                acc.setVisibility(View.INVISIBLE);
            }
        }

        for (TextView note: noteheads) {
            note.setVisibility(View.INVISIBLE);
            note.setTranslationX(0); //need minimum API level of 14 for this
        }
        ledgerLineC4.setVisibility(View.INVISIBLE);
        suppLedgerLineC4.setVisibility(View.INVISIBLE);
    }
    public String[] shuffleChord(String[] arr) {
        List<String> arrList = new ArrayList<String>(Arrays.asList(arr));
        Collections.shuffle(arrList);
        return arrList.toArray(new String[arrList.size()]);
    }
}
