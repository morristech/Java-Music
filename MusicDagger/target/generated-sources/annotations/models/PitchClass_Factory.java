package models;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PitchClass_Factory implements Factory<PitchClass> {
  private final Provider<PitchLetter> pitchLetterProvider;

  private final Provider<Accidental> accidentalProvider;

  public PitchClass_Factory(
      Provider<PitchLetter> pitchLetterProvider, Provider<Accidental> accidentalProvider) {
    this.pitchLetterProvider = pitchLetterProvider;
    this.accidentalProvider = accidentalProvider;
  }

  @Override
  public PitchClass get() {
    return new PitchClass(pitchLetterProvider.get(), accidentalProvider.get());
  }

  public static Factory<PitchClass> create(
      Provider<PitchLetter> pitchLetterProvider, Provider<Accidental> accidentalProvider) {
    return new PitchClass_Factory(pitchLetterProvider, accidentalProvider);
  }
}
