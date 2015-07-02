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

    //Music staff members
   // private Font musicFont55, musicFont58, musicFont115, musicFont120, musicFont160; //numbers at end of variable name refer to font sizes
    private TextView trebleClef, bassClef,
            staffLines1, staffLines2, staffLines3,
            fSharp, cSharp, gSharp, dSharp, aSharp, eSharp, bSharp,
            bFlat, eFlat, aFlat, dFlat, gFlat, cFlat, fFlat,
            notehead0, notehead1, notehead2, notehead3,
            notehead4, notehead5, notehead6, notehead7,
            notehead8, notehead9, notehead10, notehead11,
            notehead12, notehead13,
            ledgerLineC4, suppLedgerLineC4, ledgerLineA5;
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
        // trebleClef.setText("\uD834\uDD1E");

/*
        //font
        freeSerifPath = "fonts/FreeSerif.ttf";
        trebleClef = (TextView)findViewById(R.id.trebleClef);
        tf = Typeface.createFromAsset(getAssets(), freeSerifPath);
        trebleClef.setTypeface(tf); */




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

        trebleClef.setText(Arrays.toString(aRandomChordShuffled));
        //changeVII7toV7ofIII();

        keyTextView.setText(cg.keyNames[a][b % 2][c].concat(": ")); //again, b % 2 because the size of that array in keyNames != the parallel one in "chords"
        rightAnswerIndex = (int)(Math.random() * answerButtonArray.length);
        answerButtonArray[rightAnswerIndex].setText(rightAnswer);

        //setNotationInvisible();
        //displayPitches(aRandomChordShuffled, c, d);
        //displayKeySignature(b, c);
        createWrongAnswers(aRandomChordShuffled.length, rightAnswerIndex);
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
/*
    //"reset" the key signature / noteheads
    public void setNotationInvisible() {
        for (TextView[] mode: accidentals) {
            for (TextView acc: mode) {
                acc.setVisible(false);
            }
        }
        for (TextView note: noteheads) {
            note.setVisible(false);
            note.setTranslateX(10);
        }
        ledgerLineA5.setVisible(false);
        ledgerLineC4.setVisible(false);
        suppLedgerLineC4.setVisible(false);
    } */
    public String[] shuffleChord(String[] arr) {
        List<String> arrList = new ArrayList<String>(Arrays.asList(arr));
        Collections.shuffle(arrList);
        return arrList.toArray(new String[arrList.size()]);
    }
}
