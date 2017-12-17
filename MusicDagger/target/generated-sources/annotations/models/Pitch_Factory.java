package models;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Pitch_Factory implements Factory<Pitch> {
  private final Provider<PitchClass> pitchClassProvider;

  private final Provider<Octave> octaveProvider;

  public Pitch_Factory(Provider<PitchClass> pitchClassProvider, Provider<Octave> octaveProvider) {
    this.pitchClassProvider = pitchClassProvider;
    this.octaveProvider = octaveProvider;
  }

  @Override
  public Pitch get() {
    return new Pitch(pitchClassProvider.get(), octaveProvider.get());
  }

  public static Factory<Pitch> create(
      Provider<PitchClass> pitchClassProvider, Provider<Octave> octaveProvider) {
    return new Pitch_Factory(pitchClassProvider, octaveProvider);
  }
}
