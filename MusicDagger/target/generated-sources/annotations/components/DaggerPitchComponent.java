package components;

import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import models.Accidental;
import models.AccidentalType;
import models.Octave;
import models.Pitch;
import models.PitchClass;
import models.PitchLetter;
import modules.PitchModule;
import modules.PitchModule_AccidentalDegreeFactory;
import modules.PitchModule_AccidentalTypeFactory;
import modules.PitchModule_OctaveValueFactory;
import modules.PitchModule_PitchLetterFactory;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerPitchComponent implements PitchComponent {
  private Provider<PitchLetter> pitchLetterProvider;

  private Provider<AccidentalType> accidentalTypeProvider;

  private Provider<Integer> accidentalDegreeProvider;

  private Provider<Integer> octaveValueProvider;

  private DaggerPitchComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  private Accidental getAccidental() {
    return new Accidental(accidentalTypeProvider.get(), accidentalDegreeProvider.get());
  }

  private PitchClass getPitchClass() {
    return new PitchClass(pitchLetterProvider.get(), getAccidental());
  }

  private Octave getOctave() {
    return new Octave(octaveValueProvider.get());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.pitchLetterProvider =
        DoubleCheck.provider(PitchModule_PitchLetterFactory.create(builder.pitchModule));
    this.accidentalTypeProvider =
        DoubleCheck.provider(PitchModule_AccidentalTypeFactory.create(builder.pitchModule));
    this.accidentalDegreeProvider =
        DoubleCheck.provider(PitchModule_AccidentalDegreeFactory.create(builder.pitchModule));
    this.octaveValueProvider =
        DoubleCheck.provider(PitchModule_OctaveValueFactory.create(builder.pitchModule));
  }

  @Override
  public Pitch pitch() {
    return new Pitch(getPitchClass(), getOctave());
  }

  public static final class Builder {
    private PitchModule pitchModule;

    private Builder() {}

    public PitchComponent build() {
      if (pitchModule == null) {
        throw new IllegalStateException(PitchModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerPitchComponent(this);
    }

    public Builder pitchModule(PitchModule pitchModule) {
      this.pitchModule = Preconditions.checkNotNull(pitchModule);
      return this;
    }
  }
}
