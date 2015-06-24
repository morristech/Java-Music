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
*	Change a lot of the magic numbers to final int variables? Might be even less readable that way.
*	Add timer? Add score? Add average time per question [from generate button clicked to
*	correct answer answered]?
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
	GridPane myGridPane;
	Button answerButton1, answerButton2,
		answerButton3, answerButton4,
		generateChordButton;
	Button[] answerButtonArray;
	TextField questionTextField;
	Label questionLabel, correctMsgLabel,
		aLabel, bLabel, cLabel, dLabel,
		keyLabel, notesLabel, keySubLabel;
	Scene scene;
	MyMusicQuiz mmq;
	boolean a1, a2, a3, a4;
	String rightAnswer, wrongAnswer;
	String[] wrongAnswerArray;
	Font buttonFont;
	int rightAnswerIndex;

	//Music quiz members
	String[] pitchClassesArray, accidentalsArray,
		modeArray, romanNumeralsMajorArray,
		romanNumeralsMinorArray, triadicInversions,
		seventhInversions;
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
		accidentalsArray = new String[] {"", "#", "b"}; //empty in place of no accidental
		modeArray = new String[] {"-major", "-minor"};
		romanNumeralsMajorArray = new String[] {"I", "ii", "iii", "IV", "V", "vi", "vii°"};
		romanNumeralsMinorArray = new String[] {"i", "ii°", "III", "iv", "v", "VI", "VII"}; //no modal mixture; will this be an issue?
		triadicInversions = new String[] {"", "6", "6/4"}; //I used empty string for a 5/3 chord
		seventhInversions = new String[] {"7", "6/5", "4/3", "4/2"};

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


		//initialize JavaFX class members
		myGridPane = new GridPane();
		answerButton1 = new Button();
		answerButton2 = new Button();
		answerButton3 = new Button();
		answerButton4 = new Button();
		answerButtonArray = new Button[] {answerButton1,
			answerButton2, answerButton3, answerButton4};
		generateChordButton = new Button("Generate new question");
		questionTextField = new TextField();
		questionLabel = new Label("Analyze this chord");
		aLabel = new Label("A.");
		bLabel = new Label("B.");
		cLabel = new Label("C.");
		dLabel = new Label("D.");
		keyLabel = new Label();
		keySubLabel = new Label("(the leftmost pitch-class is\nconsidered the bass-note.)");
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
		myGridPane.add(questionTextField, 0, 1);
		myGridPane.add(keyLabel, 0, 2);
		myGridPane.add(keySubLabel, 0, 3);
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
		questionTextField.setAlignment(Pos.CENTER);
		GridPane.setColumnSpan(questionLabel, 4);
		GridPane.setColumnSpan(questionTextField, 4);
		GridPane.setColumnSpan(keyLabel, 4);
		GridPane.setColumnSpan(keySubLabel, 4);
		GridPane.setColumnSpan(correctMsgLabel, 4);
		GridPane.setColumnSpan(generateChordButton, 4);
		GridPane.setColumnSpan(notesLabel, 4);
		GridPane.setHalignment(generateChordButton, HPos.CENTER);
		GridPane.setHalignment(questionLabel, HPos.CENTER);
		GridPane.setHalignment(correctMsgLabel, HPos.CENTER);
		GridPane.setHalignment(keyLabel, HPos.CENTER);
		GridPane.setHalignment(keySubLabel, HPos.CENTER);
		GridPane.setHalignment(notesLabel, HPos.CENTER);
		answerButton1.setMinWidth(85);
		answerButton2.setMinWidth(85);
		answerButton3.setMinWidth(85);
		answerButton4.setMinWidth(85);
		GridPane.setHalignment(aLabel, HPos.LEFT);
		GridPane.setHalignment(bLabel, HPos.RIGHT);
		GridPane.setHalignment(cLabel, HPos.LEFT);
		GridPane.setHalignment(dLabel, HPos.RIGHT);
		questionTextField.setEditable(false);
		correctMsgLabel.setStyle("-fx-font-weight: bold;");

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
		primaryStage.setTitle("RN Analysis Quiz");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

		/* //old thoughts on how to add appropriate accidentals to the chords generated

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
	public String[][][] makeChords(boolean accidental, boolean mode, boolean chordSize) {
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
			}											//is just how traversing the pitch-class array in mod 7 worked out, because
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
		/* deprecated code, but I might want to look back at it:
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
	public static void printEveryChordMade(ChordGenerator m) {
		for (int i = 0; i < m.chords.length; i++) { //len = 2
			for (int j = 0; j < m.chords[i].length; j++) { //len = 4 (2 when commented out half)
				for (int p = 0; p < m.chords[i][j].length; p++) { //len = 8, eight keys;
					System.out.println(m.keyNames[i][j % 2][p]); //j % 2 because I repeat key names for both triads and 7th chords; see getRandomChord()
					for (int q = 0; q < m.chords[i][j][p].length; q++) { //len = 7, seven chords per key
						p(m.chords[i][j][p][q]);
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

		keyLabel.setText("in the key of " + keyNames[a][b % 2][c]); //again, b % 2 because the size of that array in keyNames != the parallel one in "chords"
		questionTextField.setText(Arrays.toString(aRandomChordShuffled));	//, the "chords" one is a length of 4 (thus, b could be a value of 3 or 4).
		rightAnswerIndex = (int)(Math.random() * answerButtonArray.length);
		answerButtonArray[rightAnswerIndex].setText(rightAnswer);

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
		//lengths of each dimension: mmq.chords[2][4][8][7]);
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