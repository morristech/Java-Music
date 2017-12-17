package models;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Octave_Factory implements Factory<Octave> {
  private final Provider<Integer> octaveProvider;

  public Octave_Factory(Provider<Integer> octaveProvider) {
    this.octaveProvider = octaveProvider;
  }

  @Override
  public Octave get() {
    return new Octave(octaveProvider.get());
  }

  public static Factory<Octave> create(Provider<Integer> octaveProvider) {
    return new Octave_Factory(octaveProvider);
  }
}
