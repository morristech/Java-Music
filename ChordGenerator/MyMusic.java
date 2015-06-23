/*
	This code was mostly an exercise in how to generate every major/minor
	triad and seventh chord using only two small arrays. Later on I might
	write some music quizzes of sorts using this logic.
*/

import java.util.*;

public class MyMusic {
	String[] pitchClassesArray, accidentalsArray, triad;
	String[][][][] majorKeys, minorKeys;
	String[][][][][] chords; //chords[mode][accidental type][key][chord size][pitch class]

	public MyMusic() {
		pitchClassesArray = new String[] {"C", "D", "E", "F", "G", "A", "B"};
		accidentalsArray = new String[] {"", "#", "b"}; //empty in place of no accidental

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
	public static void main(String[] args) {
		MyMusic m = new MyMusic();
		printEveryChordMade(m);

		/*

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
	}
	//accidental == true means use sharps. accidental == false means use flats.
	//mode == true is for major keys. mode == false is for minor keys.
	//chordSize == true is for triads. chordSize == false is for 7th chords.
	public String[][][] makeChords(boolean accidental, boolean mode, boolean chordSize) { //{"C", "D", "E", "F", "G", "A", "B"};
		String[][][] arr = new String[8][7][chordSize ? 3 : 4]; //8 keys, 7 chords per key, 3 or 4 pitch classes per chord.
		int index = mode ? 0 : 5;	//why 8 keys? because Cmajor to C#major, Aminor to Abminor, etc., is each 8 keys total.
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				for (int k = 0; k < arr[i][j].length; k++) {
					arr[i][j][k] = pitchClassesArray[index] + getAccidental(i, index, accidental);
					index = (index + 2) % 7;
				}
				index = (index + (chordSize ? 2 : 0)) % 7;
			}
			index = (index + (accidental ? 4 : 3)) % 7; //these magic numbers just have to do with
		}						//how intervals work in music
		return arr;
	}
	//accidental == true means return sharps. accidental == false means return flats.
	public String getAccidental(int i, int index, boolean accidental) { //{"", "#", "b"};
		String acc = accidentalsArray[0]; //this counts for case 0 of the switch below
		switch (i) { //the value of i refers to the number of accidentals in the key
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
	public static void printEveryChordMade(MyMusic m) {
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
	}
	public static <E> void p(E[] arr) {
		System.out.printf("[%-2s, ", arr[0]);
		for (int i = 1; i < arr.length - 1; i++) {
			System.out.printf("%-2s, ", arr[i]);
		}
		System.out.printf("%-2s]%n", arr[arr.length - 1]);
	}
}