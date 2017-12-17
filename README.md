# Java-Music
Here resides a bunch of music related Java programs I'm making in my free time

**MusicDagger** is a fairly contrived example of creating musical pitches facilitated by 
the dependency injection library, Dagger2. It is similar in spirit to the Music API project,
also in this repo. The distinction between using DI and not is outlined below.

        // Ingredients for a Bbb3 pitch
        final PitchLetter pitchLetter = PitchLetter.B;
        final AccidentalType accidentalType = AccidentalType.FLAT;
        final @AccidentalDegree int accidentalDegree = 2;
        final @OctaveValue int octaveValue = 3;

        // Without DI
        Accidental accidental = new Accidental(accidentalType, accidentalDegree);
        PitchClass pitchClass = new PitchClass(pitchLetter, accidental);
        Octave octave = new Octave(octaveValue);
        Pitch bDoubleFlat3 = new Pitch(pitchClass, octave);

        System.out.println(bDoubleFlat3);  // Bbb3

        // With DI
        PitchModule pitchModule = 
                new PitchModule(pitchLetter, accidentalType, accidentalDegree, octaveValue);

        Pitch pitch = DaggerPitchComponent.builder()
                .pitchModule(pitchModule)
                .build()
                .pitch();

        System.out.println(pitch);  // Bbb3

**Music API** is another music-generating project which lets the user create music chord/collection objects and calculate
pitch[class] intervals easily. It also includes static collections of virtually every reasonable diatonic collection, pitch class, pitch, chord, and pitched chord (4455 unique pitched chords spread over about twelve octaves, to be exact!).

Here are a few examples.

        // Query the static dictionaries for objects
        MusicCollections.PITCH_CLASSES.get("Abb");
        MusicCollections.PITCHES.get("Fx3");
        MusicCollections.DIATONIC_COLLECTIONS.get("C#maj");  // [C#, D#, E#, F#, G#, A#, B#]
        MusicCollections.CHORDS.get("Bbaug9");  // [Bb, D, F#, A, C]
        MusicCollections.PITCHED_CHORDS.get("A#4m9");  // [A#4, C#5, E#5, G#5, B#5]
        
        // Create and manipulate objects from scratch
        Pitch b3 = new Pitch(new PitchClass(PitchLetter.B, Accidental.NATURAL), Octave.THIRD);
        System.out.println(b3.transpose(Interval.M3).transpose(Interval.m3));  // F#4 
        
        PitchClass g = new PitchClass(PitchLetter.G, Accidental.NATURAL);
        DiatonicCollection gMinor = new DiatonicCollection(g, CollectionMode.MINOR);  // [G, A, Bb, C, D, Eb, F] 
        DiatonicCollection gMajor = new DiatonicCollection(g, CollectionMode.MAJOR);  // [G, A, B, C, D, E, F#] 
        
        PitchClass c = new PitchClass(PitchLetter.C, Accidental.NATURAL);
        Chord cMinor9th = new Chord(c, ChordType.MINOR, ChordSize.NINTH);  // [C, Eb, G, Bb, D] 
        
        Pitch db4 = new Pitch(new PitchClass(PitchLetter.D, Accidental.FLAT), Octave.FOURTH);
        PitchedChord db4Major7th = new PitchedChord(db4, ChordType.MAJOR, ChordSize.SEVENTH);  // [Db4, F4, Ab4, C5] 

**MusicStuff** is a project which generates all possible diatonic major/minor triads and major/minor scales. 
It outputs this to the console. The purpose of writing this project was to try and write something that is
a bit more maintainable and readable than how I did it for the other projects in this repo.


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
Google Nexus 5 emulator (Genymotion). It is published on the Google Play store and can be found here:
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
