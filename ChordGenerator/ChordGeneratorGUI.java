/**
*	This code was originally an exercise in how to generate every major/minor
*	triad and seventh chord built from small arrays.
*	But now I shoved it into a GUI to make it a music theory quiz.
*
*	TODO musicians don't want to look at sets of chords like [G, B, D], they
*	want to look at music notation. How can I use javafx paint to draw these chords?
*	Make interface look less basic.
*	Add other common "modal-mixture" chords like V in minor, applied chords...this latter
*	addition would be interpretive. How can I ensure that the user agrees with my interpretation,
*	e.g. a VII7 in C-minor would 'usually' be a V7 of III.
*	Add timer? Add score? Add average time per question [from generate button clicked to
*	correct answer answered]? Wrong attempts vs correct attempts?
*
*	Drawing staff ideas: an array of x/y values for each potential pitch class. Perhaps use random
*	decision making to decide which octave to use (the bass note should always use the lowest possible
*	octave, though).
*
*/ 

import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.text.*;

public class ChordGeneratorGUI extends Application {

	//JavaFX members
	GridPane myGridPane, musicStaffGridPane;
	Button answerButton1, answerButton2,
		answerButton3, answerButton4,
		generateChordButton;
	Button[] answerButtonArray;
	Label questionLabel, correctMsgLabel,
		aLabel, bLabel, cLabel, dLabel,
		keyLabel, notesLabel;
	Scene scene;
	boolean a1, a2, a3, a4;
	String rightAnswer, wrongAnswer;
	String[] wrongAnswerArray;
	Font buttonFont;
	int rightAnswerIndex;

	//JavaFX music staff members
	Font musicFont55, musicFont58, musicFont115, musicFont120, musicFont160; //numbers refer to find sizes
	Label trebleClef, bassClef,
		staffLines1, staffLines2, staffLines3,
		fSharp, cSharp, gSharp, dSharp, aSharp, eSharp, bSharp,
		bFlat, eFlat, aFlat, dFlat, gFlat, cFlat, fFlat,
		notehead0, notehead1, notehead2, notehead3,
		notehead4, notehead5, notehead6, notehead7,
		notehead8, notehead9, notehead10, notehead11,
		notehead12, notehead13,
		ledgerLineC4, ledgerLineA5;
	Label[] sharps, flats, noteheads;
	Label[][] accidentals;

	//Music quiz members
	String[] pitchClassesArray, accidentalsArray,
		modeArray, romanNumeralsMajorArray,
		romanNumeralsMinorArray, triadicInversions,
		seventhInversions, pitchClassesArrayFromB;
	String[][][] keyNames;
	String[][][][] majorKeys, minorKeys;
	String[][][][][] chords; //chords[mode][accidental type][key][chord size][pitch class]
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
		MINOR_MODE = 1; //in modeArray index 1 means minor mode

	@Override
	public void start(Stage primaryStage) {

		//Initialize music quiz variables
		pitchClassesArray = new String[] {"C", "D", "E", "F", "G", "A", "B"};
		pitchClassesArrayFromB = new String[] {"B", "C", "D", "E", "F", "G", "A"}; //need this because notehead positions start at B3
		accidentalsArray = new String[] {"", "#", "b"}; //empty in place of no accidental
		modeArray = new String[] {"-major", "-minor"};
		romanNumeralsMajorArray = new String[] {"I", "ii", "iii", "IV", "V", "vi", "vii°"};
		romanNumeralsMinorArray = new String[] {"i", "ii°", "III", "iv", "v", "VI", "VII"}; //no modal mixture; will this be an issue?
		triadicInversions = new String[] {"", "6", "6/4"}; //I used empty string for a 5/3 chord
		seventhInversions = new String[] {"7", "6/5", "4/3", "4/2"};

		//initialize music staff notation variables
		musicStaffGridPane = new GridPane();
		musicFont55 = new Font("FreeSerif", 55);
		musicFont58 = new Font("FreeSerif", 58);
		musicFont115 = new Font("FreeSerif", 115);
		musicFont120 = new Font("FreeSerif", 120);
		musicFont160 = new Font("FreeSerif", 160);
		trebleClef = new Label("\uD834\uDD1E");
		bassClef = new Label("\uD834\uDD22");
		staffLines1 = new Label("\uD834\uDD1A");
		staffLines2 = new Label("\uD834\uDD1A");
		staffLines3 = new Label("\uD834\uDD1A");
		fSharp = new Label("\u266F");
		cSharp = new Label("\u266F");
		gSharp = new Label("\u266F");
		dSharp = new Label("\u266F");
		aSharp = new Label("\u266F");
		eSharp = new Label("\u266F");
		bSharp = new Label("\u266F");
		bFlat = new Label("\u266D");
		eFlat = new Label("\u266D");
		aFlat = new Label("\u266D");
		dFlat = new Label("\u266D");
		gFlat = new Label("\u266D");
		cFlat = new Label("\u266D");
		fFlat = new Label("\u266D");
		notehead0 = new Label("\uD834\uDD5D");
		notehead1 = new Label("\uD834\uDD5D");
		notehead2 = new Label("\uD834\uDD5D");
		notehead3 = new Label("\uD834\uDD5D");
		notehead4 = new Label("\uD834\uDD5D");
		notehead5 = new Label("\uD834\uDD5D");
		notehead6 = new Label("\uD834\uDD5D");
		notehead7 = new Label("\uD834\uDD5D");
		notehead8 = new Label("\uD834\uDD5D");
		notehead9 = new Label("\uD834\uDD5D");
		notehead10 = new Label("\uD834\uDD5D");
		notehead11 = new Label("\uD834\uDD5D");
		notehead12 = new Label("\uD834\uDD5D");
		notehead13 = new Label("\uD834\uDD5D");
		ledgerLineC4 = new Label("\u2015");
		ledgerLineA5 = new Label("\u2015");
		noteheads = new Label[] {
			notehead0, notehead1,
			notehead2, notehead3, notehead4,
			notehead5, notehead6, notehead7,
			notehead8, notehead9, notehead10,
			notehead11, notehead12, notehead13,
		};
		sharps = new Label[] {
			fSharp, cSharp, gSharp, dSharp,
			aSharp, eSharp, bSharp
		};
		flats = new Label[] {
			bFlat, eFlat, aFlat,
			dFlat, gFlat, cFlat, fFlat
		};
		accidentals = new Label[][] {sharps, flats};

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


		//initialize non-music notation JavaFX class members
		myGridPane = new GridPane();
		answerButton1 = new Button();
		answerButton2 = new Button();
		answerButton3 = new Button();
		answerButton4 = new Button();
		answerButtonArray = new Button[] {answerButton1,
			answerButton2, answerButton3, answerButton4};
		generateChordButton = new Button("Generate new question");
		questionLabel = new Label("Analyze the following chord:");
		aLabel = new Label("A.");
		bLabel = new Label("B.");
		cLabel = new Label("C.");
		dLabel = new Label("D.");
		keyLabel = new Label();
		correctMsgLabel = new Label();
		notesLabel = new Label(
			"Note: this program only interprets the chords\n" +
			"at face value. It does not understand applied\n" +
			"chords, passing chords, suspensions, or\n" +
			"modulation; analyze accordingly. For example,\n" +
			"a Bb7 chord in C-minor will have to be answered\n" +
			"literally as VII7 rather than something like,\n" +
			"\"V7 of I in the relative major.\""
		);
		buttonFont = new Font("Courier New", 12);
		for (Button b: answerButtonArray) {
			b.setFont(buttonFont);
		}

		//add to GridPane
		myGridPane.add(questionLabel, 0, 0);
		musicStaffGridPane.add(trebleClef, 0, 0);
		musicStaffGridPane.add(staffLines1, 0, 0);
		musicStaffGridPane.add(staffLines2, 1, 0);
		musicStaffGridPane.add(staffLines3, 2, 0);
		musicStaffGridPane.add(fSharp, 1, 0);
		musicStaffGridPane.add(cSharp, 1, 0);
		musicStaffGridPane.add(gSharp, 1, 0);
		musicStaffGridPane.add(dSharp, 1, 0);
		musicStaffGridPane.add(aSharp, 1, 0);
		musicStaffGridPane.add(eSharp, 1, 0);
		musicStaffGridPane.add(bSharp, 1, 0);
		musicStaffGridPane.add(bFlat, 1, 0);
		musicStaffGridPane.add(eFlat, 1, 0);
		musicStaffGridPane.add(aFlat, 1, 0);
		musicStaffGridPane.add(dFlat, 1, 0);
		musicStaffGridPane.add(gFlat, 1, 0);
		musicStaffGridPane.add(cFlat, 1, 0);
		musicStaffGridPane.add(fFlat, 1, 0);
		musicStaffGridPane.add(notehead0, 2, 0);
		musicStaffGridPane.add(notehead1, 2, 0);
		musicStaffGridPane.add(notehead2, 2, 0);
		musicStaffGridPane.add(notehead3, 2, 0);
		musicStaffGridPane.add(notehead4, 2, 0);
		musicStaffGridPane.add(notehead5, 2, 0);
		musicStaffGridPane.add(notehead6, 2, 0);
		musicStaffGridPane.add(notehead7, 2, 0);
		musicStaffGridPane.add(notehead8, 2, 0);
		musicStaffGridPane.add(notehead9, 2, 0);
		musicStaffGridPane.add(notehead10, 2, 0);
		musicStaffGridPane.add(notehead11, 2, 0);
		musicStaffGridPane.add(notehead12, 2, 0);
		musicStaffGridPane.add(notehead13, 2, 0);
		musicStaffGridPane.add(ledgerLineA5, 2, 0);
		musicStaffGridPane.add(ledgerLineC4, 2, 0);

		myGridPane.add(musicStaffGridPane, 0, 1);
		myGridPane.add(keyLabel, 0, 2);
		myGridPane.add(aLabel, 0, 4);
		myGridPane.add(answerButton1, 1, 4);
		myGridPane.add(bLabel, 2, 4);
		myGridPane.add(answerButton2, 3, 4);
		myGridPane.add(cLabel, 0, 5);
		myGridPane.add(answerButton3, 1, 5);
		myGridPane.add(dLabel, 2, 5);
		myGridPane.add(answerButton4, 3, 5);
		myGridPane.add(generateChordButton, 0, 6);
		myGridPane.add(correctMsgLabel, 0, 7);
		myGridPane.add(notesLabel, 0, 8);

		//set alignments/prettiness
		myGridPane.setHgap(15);
		myGridPane.setVgap(15);
		myGridPane.setPadding(new Insets(10, 10, 10, 10));
		GridPane.setColumnSpan(questionLabel, 4);
		GridPane.setColumnSpan(keyLabel, 4);
		GridPane.setColumnSpan(correctMsgLabel, 4);
		GridPane.setColumnSpan(generateChordButton, 4);
		GridPane.setColumnSpan(notesLabel, 4);
		GridPane.setColumnSpan(musicStaffGridPane, 4);
		GridPane.setHalignment(generateChordButton, HPos.CENTER);
		GridPane.setHalignment(questionLabel, HPos.CENTER);
		GridPane.setHalignment(correctMsgLabel, HPos.CENTER);
		GridPane.setHalignment(keyLabel, HPos.LEFT);
		GridPane.setHalignment(notesLabel, HPos.CENTER);
		answerButton1.setMinWidth(110);
		answerButton2.setMinWidth(110);
		answerButton3.setMinWidth(110);
		answerButton4.setMinWidth(110);
		GridPane.setHalignment(aLabel, HPos.LEFT);
		GridPane.setHalignment(bLabel, HPos.RIGHT);
		GridPane.setHalignment(cLabel, HPos.LEFT);
		GridPane.setHalignment(dLabel, HPos.RIGHT);;
		correctMsgLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
		questionLabel.setPadding(new Insets(0, 0, 20, 0));
		keyLabel.setTranslateX(52);

		fSharp.setTranslateY(-60);		fSharp.setTranslateX(-20);
		cSharp.setTranslateY(-24);		cSharp.setTranslateX(-4);
		gSharp.setTranslateY(-69);		gSharp.setTranslateX(12);
		dSharp.setTranslateY(-37);		dSharp.setTranslateX(28);
		aSharp.setTranslateY(-1);		aSharp.setTranslateX(44);
		eSharp.setTranslateY(-48);		eSharp.setTranslateX(60);
		bSharp.setTranslateY(-13);		bSharp.setTranslateX(76);

		bFlat.setTranslateY(-13);		bFlat.setTranslateX(-20);
		eFlat.setTranslateY(-48);		eFlat.setTranslateX(-4);
		aFlat.setTranslateY(-1);		aFlat.setTranslateX(12);
		dFlat.setTranslateY(-36);		dFlat.setTranslateX(28);
		gFlat.setTranslateY(10);		gFlat.setTranslateX(44);
		cFlat.setTranslateY(-24);		cFlat.setTranslateX(60);
		fFlat.setTranslateY(21);		fFlat.setTranslateX(76);

		notehead13.setTranslateY(-101);	notehead13.setTranslateX(10); //A5
		notehead12.setTranslateY(-90); 	notehead12.setTranslateX(10); //G5
		notehead11.setTranslateY(-78);	notehead11.setTranslateX(10); //F5
		notehead10.setTranslateY(-67); 	notehead10.setTranslateX(10); //E5
		notehead9.setTranslateY(-55);	notehead9.setTranslateX(10); //D5
		notehead8.setTranslateY(-44); 	notehead8.setTranslateX(10); //C5
		notehead7.setTranslateY(-32);	notehead7.setTranslateX(10); //B4
		notehead6.setTranslateY(-21);	notehead6.setTranslateX(10); //A4
		notehead5.setTranslateY(-9);	notehead5.setTranslateX(10); //G4
		notehead4.setTranslateY(2); 	notehead4.setTranslateX(10); //F4
		notehead3.setTranslateY(14);	notehead3.setTranslateX(10); //E4
		notehead2.setTranslateY(26); 	notehead2.setTranslateX(10); //D4
		notehead1.setTranslateY(37); 	notehead1.setTranslateX(10); //C4
		notehead0.setTranslateY(49);	notehead0.setTranslateX(10); //B3

		ledgerLineA5.setTranslateY(-81); ledgerLineA5.setTranslateX(3);
		ledgerLineC4.setTranslateY(57);  ledgerLineC4.setTranslateX(3);

		for (Label[] mode: accidentals) {
			for (Label acc: mode) {
				acc.setFont(musicFont55);
			}
		}
		trebleClef.setFont(musicFont120);
		bassClef.setFont(musicFont120);
		staffLines1.setFont(musicFont120);
		staffLines2.setFont(musicFont120);
		staffLines3.setFont(musicFont120);
		ledgerLineC4.setFont(musicFont58);
		ledgerLineA5.setFont(musicFont58);
		for (Label nh: noteheads) {
			nh.setFont(musicFont115);
		}
		keyLabel.setFont(new Font("Serif", 19));
		questionLabel.setFont(new Font("Serif", 17));

		//set Listeners
		generateChordButton.setOnAction((ActionEvent e) -> {
			getRandomChord();
		});
		answerButton1.setOnAction((ActionEvent e) -> {
			if (rightAnswer != null
				&& answerButton1.getText().equals(rightAnswer)) {
				correctMsgLabel.setText("Correct!");
			} else if (rightAnswer != null) {
				correctMsgLabel.setText("Incorrect.");
			}
		});
		answerButton2.setOnAction((ActionEvent e) -> {
			if (rightAnswer != null
				&& answerButton2.getText().equals(rightAnswer)) {
				correctMsgLabel.setText("Correct!");
			} else if (rightAnswer != null) {
				correctMsgLabel.setText("Incorrect.");
			}
		});
		answerButton3.setOnAction((ActionEvent e) -> {
			if (rightAnswer != null
				&& answerButton3.getText().equals(rightAnswer)) {
				correctMsgLabel.setText("Correct!");
			} else if (rightAnswer != null) {
				correctMsgLabel.setText("Incorrect.");
			}
		});
		answerButton4.setOnAction((ActionEvent e) -> {
			if (rightAnswer != null
				&& answerButton4.getText().equals(rightAnswer)) {
				correctMsgLabel.setText("Correct!");
			} else if (rightAnswer != null) {
				correctMsgLabel.setText("Incorrect.");
			}
		});

		//set up scene/stage
		getRandomChord();
		scene = new Scene(myGridPane);
		generateChordButton.requestFocus();
		primaryStage.setTitle("Nick's RN Analysis Quiz");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

		/* //Old thoughts on how to add appropriate accidentals to the chords generated

		c  d  e  f  g  a  b
		g  a  b  c  d  e  f#	                  [6]
		d  e  f# g  a  b  c#	      [2]         [6]
		a  b  c# d  e  f# g#	      [2]      [5][6]
		e  f# g# a  b  c# d#	   [1][2]      [5][6]
		b  c# d# e  f# g# a#	   [1][2]   [4][5][6]
		f# g# a# b  c# d# e#	[0][1][2]   [4][5][6]
		c# d# e# f# g# a# b#	[0][1][2][3][4][5][6]

		arr.length - 1
		arr.length - 1, arr.length / 3
		arr.length - 1, arr.length - 2, arr.length / 3
		arr.length - 1, arr.length - 2, arr.length / 3, arr.length / 3 - 1
		arr.length - 1, arr.length - 2, arr.length - 3, arr.length / 3, arr.length / 3 - 1
		arr.length - 1, arr.length - 2, arr.length - 3, arr.length / 3, arr.length / 3 - 1, arr.length / 3 - 2
		arr.length - 1, arr.length - 2, arr.length - 3, arr.length - 4, arr.length / 3, arr.length / 3 - 1, arr.length / 3 - 2

		c  d  e  f  g  a  b
		d  e  f# g  a  b  c#	      [2]         [6]
		e  f# g# a  b  c# d#	   [1][2]      [5][6]
		f# g# a# b  c# d# e#	[0][1][2]   [4][5][6]
		g  a  b  c  d  e  f#	                  [6]
		a  b  c# d  e  f# g#	      [2]      [5][6]
		b  c# d# e  f# g# a#	   [1][2]   [4][5][6]

		a  b  c  d  e  f  g
		b  c# d  e  f# g# a
		c# d# e  f# g# a  b
		d# e# f# g# a# b  c#
		e  f# g  a  b  c  d
		f# g# a  b  c# d  e
		g# a# b  c# d# e  f#

		a  b  c  d  e  f  g
		e  f# g  a  b  c  d
		b  c# d  e  f# g# a
		f# g# a  b  c# d  e
		c# d# e  f# g# a  b
		g# a# b  c# d# e  f#
		d# e# f# g# a# b  c#
		a# b# c# d# e# f# g#

		*/

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
				index = (index + (chordSize ? 2 : 0)) % NUM_PITCH_CLASSES; //if I'm making triads, 2, if seventh chords, 0. this
			}										//is just how traversing the pitch-class array in mod 7 worked out, because
													//C-E-G are intervals of 2 from C to G, but D-F-A (the next chord) is an interval
													//of 4 from G to D (2 + 2 = 4; i.e. 2 from the k loop, and now 2 from the j loop)
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
		/* deprecated code, but I might want to look back at it in future:
		keyNames = new String[4][8];
		boolean interval = true,	//true == a fifth interval, false == a fourth interval
				accidental = true,	//true == sharps, false == flats
				mode = true;		//true == major mode, false == minor mode
		for (int i = 0; i < keyNames.length; i++) {
			for (int j = 0, k = mode ? 0 : 5;
					j < keyNames[i].length;
					j++, k = (k + (interval ? 4 : 3)) % 7) {
				keyNames[i][j] =
					pitchClassesArray[k].concat(
						getAccidental(j, k, accidental
					).concat(modeArray[mode ? 0 : 1]));
			}
			if (i == 0 || i == 2) {
				interval = false;
				accidental = false;
			} else {
				interval = true;
				accidental = true;
			}
			if (i == 1) {
				mode = false;
			}
		}

		//vomit-inducing alternative ideas
		for (int i = 0, j = 0, k = 0, m = 8, n = 16, p = 24, q = 5, r = 5;
				i < 8 && m < 16 && n < 24 && p < keyNames.length;
				i++, j = ((j + 4) % 7), k = ((k + 3) % 7), m++,
				n++, p++, q = ((q + 4) % 7), r = ((r + 3) % 7)) {
			keyNames[i] = pitchClassesArray[j].concat(modeArray[0]);
			keyNames[m] = pitchClassesArray[k].concat(modeArray[0]);
			keyNames[n] = pitchClassesArray[q].concat(modeArray[1]);
			keyNames[p] = pitchClassesArray[r].concat(modeArray[1]);
		}

		C-major G-major D-major ... C#-major
		C-major F-major Bbmajor ... Cb-major
		A-minor E-minor B-minor ... A#-minor
		A-minor D-minor G-minor ... Ab-minor

		pitchClassesArray[0].concat(modeArray[0]);
		pitchClassesArray[0].concat(modeArray[1]);
		pitchClassesArray[1].concat(modeArray[0]);
		pitchClassesArray[1].concat(modeArray[0]);

		*/

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
	public static void printEveryChordMade(ChordGeneratorGUI cg) {
		for (int i = 0; i < cg.chords.length; i++) { //len = 2
			for (int j = 0; j < cg.chords[i].length; j++) { //len = 4 (2 when commented out half)
				for (int p = 0; p < cg.chords[i][j].length; p++) { //len = 8, eight keys;
					System.out.println(cg.keyNames[i][j % 2][p]); //j % 2 because I repeat key names for both triads and 7th chords; see getRandomChord()
					for (int q = 0; q < cg.chords[i][j][p].length; q++) { //len = 7, seven chords per key
						p(cg.chords[i][j][p][q]);
					}
				}
			}
		}
		/* //the old way I printed.
		for (String[][][][] mode: m.chords) {
			for (String[][][] accidentalType: mode) {
				for (String[][] key: accidentalType) {
					for (String[] triad: key) {
						p(triad);
					}
					System.out.println("================");
				}
				System.out.println("================");
			}
			System.out.println("================");
		}
		*/
	}
	//generic method to print an array to the terminal
	public static <E> void p(E[] arr) {
		System.out.printf("[%-2s, ", arr[0]);
		for (int i = 1; i < arr.length - 1; i++) {
			System.out.printf("%-2s, ", arr[i]);
		}
		System.out.printf("%-2s]%n", arr[arr.length - 1]);
	}
	public void getRandomChord() {
		correctMsgLabel.setText(null); //erases label until user answers next question

		//lengths of each dimension: chords[2][4][8][7]); all arrays meet those lengths, i.e. there are no ragged arrays,
		//so I can just use lengths of the zeroth index of things like chords[0].length with confidence
		int a = (int)(Math.random() * chords.length), //chords.length == 2
			b = (int)(Math.random() * chords[0].length), //chords[0].length == 4
			c = (int)(Math.random() * chords[0][0].length), //chords[0][0].length == 8
			d = (int)(Math.random() * chords[0][0][0].length); //chords[0][0][0].length == 7
		String[] aRandomChord = chords[a][b][c][d],
				 aRandomChordShuffled = shuffleChord(aRandomChord);
		String bassNote = aRandomChordShuffled[0];
		int inversionIndex = Arrays.asList(aRandomChord).indexOf(bassNote);
		rightAnswer = (a == 0
			? romanNumeralsMajorArray[d]
			: romanNumeralsMinorArray[d]).concat(
			b < 2 ? triadicInversions[inversionIndex] //b < 2 because chords[][0] and chords[][1] are triads, chords[][2] and chords[][3] are 7th chords
				  : seventhInversions[inversionIndex]
		);

		if (b >= 2 && rightAnswer.contains("°")) { //if chord is a 7th chord (b==2 or b==3) and contains a °, it's actually a half-diminished chord
			rightAnswer = rightAnswer.replace("°", "ø"); //so replace the ° with a ø
		}

		keyLabel.setText(keyNames[a][b % 2][c].concat(": ")); //again, b % 2 because the size of that array in keyNames != the parallel one in "chords"
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
		for (int i = 0; i < pitchClassesArrayFromB.length; i++) {
			if (pitchClassesArrayFromB[i].equals(
					aRandomChordShuffled[0].substring(0, 1))) {
				verticalIndex = i;
				noteheads[verticalIndex].setVisible(true);  //move to the right by num_accidentals * a constant?
				if (verticalIndex == 0 || verticalIndex == 1) {
					ledgerLineC4.setVisible(true);
				}
				break;
			}
		}
		//input the remaining 2-3 pitches
		for (int i = verticalIndex, j = verticalIndex, k = 0;
				i < noteheads.length && k < everythingButBassNote.length;
				i++, j = (j + 1) % 7) {
			if (pitchClassesArrayFromB[j].equals(everythingButBassNote[k])) {
				noteheads[i].setVisible(true);
				k++;
				if (i == 13) {
					ledgerLineA5.setVisible(true);
				}
				if (i > 0 && noteheads[i - 1].isVisible()) {
					noteheads[i].setTranslateX(40);
				}
			}
		}
	}
	public void displayKeySignature(int sharpsOrFlats, int numAccidentals) {
		for (int i = 0; i < numAccidentals; i++) {
			accidentals[sharpsOrFlats % 2][i].setVisible(true);
		}
	}
	//"reset" the key signature / noteheads
	public void setNotationInvisible() {
		for (Label[] mode: accidentals) {
			for (Label acc: mode) {
				acc.setVisible(false);
			}
		}
		for (Label note: noteheads) {
			note.setVisible(false);
			note.setTranslateX(10);
		}
		ledgerLineA5.setVisible(false);
		ledgerLineC4.setVisible(false);
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
					randomRNIndex = (int)(Math.random() * romanNumeralsMajorArray.length); //romanNumeralsMajorArray.length == romanNumeralsMinorArray.length
					randomInversionIndex = (int)(Math.random() * chordSize);
					randomModeIndex = (int)(Math.random() * modeArray.length);
					wrongAnswer = (randomModeIndex == 0 //randomModeIndex will be 0 or 1 by randomness.
						? romanNumeralsMajorArray[randomRNIndex]
						: romanNumeralsMinorArray[randomRNIndex]).concat(
						chordSize == triadicInversions.length //chord a triad? wrong answer inversion should be triadic only. otherwise only 7th inversions.
							? triadicInversions[randomInversionIndex] //arr len = 3
							: seventhInversions[randomInversionIndex] //arr len = 4
					);
				} while (wrongAnswer.equals(rightAnswer) //avoids a duplicate right answer
						|| Arrays.asList(wrongAnswerArray).contains(wrongAnswer)  //avoids duplicate wrong answers
				);
				if (chordSize == 4
						&& wrongAnswer.contains("°")) { //randomly give a wrong answer a half-diminished symbol, otherwise
					if (Math.random() < 0.5) { //when a half-dim symbol appears, that'd always be the correct answer
						wrongAnswer = wrongAnswer.replace("°", "ø");
					}
				}
				answerButtonArray[i].setText(wrongAnswer);
			}
		}
	}
	//for printing to the terminal (testing)
	//getRandomChord() replaces all the magic numbers in this method
	//with less magical values (e.g. array lengths)
	public void printRandomChordKeyRN() {
		//lengths of each dimension: chords[2][4][8][7]);
		//random triad or 7th chord
		int a = (int)(Math.random() * 2),
			b = (int)(Math.random() * 4),
			c = (int)(Math.random() * 8),
			d = (int)(Math.random() * 7);

		String[] aChord = chords[a][b][c][d];
		String[] aShuffledChord = shuffleChord(aChord);
		String bassNote = aShuffledChord[0];
		int inversionIndex = Arrays.asList(aChord).indexOf(bassNote);

		System.out.println(keyNames[a][b % 2][c]);
		p(aShuffledChord);
		System.out.println((a == 0
			? romanNumeralsMajorArray[d]
			: romanNumeralsMinorArray[d]).concat(
			b < 2 ? triadicInversions[inversionIndex]
				  : seventhInversions[inversionIndex]
			)
		);
		/*
		//random triad:
		p(mmq.chords[(int)(Math.random() * 2)]
			[(int)(Math.random() * 2)]
			[(int)(Math.random() * 8)]
			[(int)(Math.random() * 7)]
		);
		*/
		/*
		//random 7th chord:
		p(mmq.chords[(int)(Math.random() * 2)]
			[(int)(2 + Math.random() * 2)]
			[(int)(Math.random() * 8)]
			[(int)(Math.random() * 7)]
		);
		*/
	}
	public String[] shuffleChord(String[] arr) {
		List<String> arrList = new ArrayList<String>(Arrays.asList(arr));
		Collections.shuffle(arrList);
		return arrList.toArray(new String[arrList.size()]);
	}
}
