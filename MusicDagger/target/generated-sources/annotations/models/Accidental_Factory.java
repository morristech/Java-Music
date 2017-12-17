package models;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Accidental_Factory implements Factory<Accidental> {
  private final Provider<AccidentalType> accidentalTypeProvider;

  private final Provider<Integer> degreeProvider;

  public Accidental_Factory(
      Provider<AccidentalType> accidentalTypeProvider, Provider<Integer> degreeProvider) {
    this.accidentalTypeProvider = accidentalTypeProvider;
    this.degreeProvider = degreeProvider;
  }

  @Override
  public Accidental get() {
    return new Accidental(accidentalTypeProvider.get(), degreeProvider.get());
  }

  public static Factory<Accidental> create(
      Provider<AccidentalType> accidentalTypeProvider, Provider<Integer> degreeProvider) {
    return new Accidental_Factory(accidentalTypeProvider, degreeProvider);
  }
}
