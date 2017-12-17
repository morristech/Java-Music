package modules;

import dagger.Module;
import dagger.Provides;
import models.*;
import qualifiers.AccidentalDegree;
import qualifiers.OctaveValue;
import scopes.PitchScope;

@Module
public class PitchModule {

    private final PitchLetter pitchLetter;
    private final AccidentalType accidentalType;
    private final int accidentalDegree;
    private final int octaveValue;

    public PitchModule(PitchLetter pitchLetter, AccidentalType accidentalType, int accidentalDegree, int octaveValue) {
        this.pitchLetter = pitchLetter;
        this.accidentalType = accidentalType;
        this.accidentalDegree = accidentalDegree;
        this.octaveValue = octaveValue;
    }

    @PitchScope
    @Provides
    public PitchLetter pitchLetter() {
        return pitchLetter;
    }

    @PitchScope
    @Provides
    public AccidentalType accidentalType() {
        return accidentalType;
    }

    @PitchScope
    @Provides
    @AccidentalDegree
    public int accidentalDegree() {
        return accidentalDegree;
    }

    @PitchScope
    @Provides
    @OctaveValue
    public int octaveValue() {
        return octaveValue;
    }
}
