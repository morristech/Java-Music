# Java-Music
Here resides a bunch of music related Java programs I'm making in my free time

**ChordGenerator** uses the JavaFX toolkit to display a music quiz program. 
It randomly generates one of 480 potential key/chord combinations and displays this in musical
notation. The user is then expected to choose one of four choices to correctly analyze the
chord. Each is a Roman numeral signifying what scale degree the chord is built upon and 
Arabic numeral\(s\) to denote its inversion. This is a common activity in undergraduate music classes and my 
intention with writing this program was to assist students having difficulty with that concept.

<div align="center">
   <img src="https://github.com/nihk/Java-Music/blob/master/ChordGenerator/screenshot.png">
</div>
<br><br>

In the **AndroidMusicQuiz** folder is an Android version of ChordGenerator. Here's a look at it on a
Samsung Galaxy S3 emulator (Genymotion). It can be found on the Google Play store here:
https://play.google.com/store/apps/details?id=com.nihk.github.musictheoryromannumeralanalysisquiz

<div align="center">
   <img src="https://github.com/nihk/Java-Music/blob/master/AndroidMusicQuiz/screenshot.png">
</div>
<br><br>

**SetTheoryCalculatorJavaFX** also uses JavaFX to display a music analysis tool used in
pitch-class set theory, a topic commonly taught to both undergraduate and graduate students at university.
A user enters a collection of pitch-classes and the program essentially
sorts these values in a variety of ways. The Normal Form sort, for instance, arranges the 
input into its most left-packed rotation (or right-packed, depending
on whether the Forte or Rahn algorithm is chosen, respectively).

<div align="center">
   <img src="https://github.com/nihk/Java-Music/blob/master/SetTheoryCalculatorJavaFX/screenshot.png">
</div>
