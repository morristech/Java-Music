package modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import models.AccidentalType;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PitchModule_AccidentalTypeFactory implements Factory<AccidentalType> {
  private final PitchModule module;

  public PitchModule_AccidentalTypeFactory(PitchModule module) {
    this.module = module;
  }

  @Override
  public AccidentalType get() {
    return Preconditions.checkNotNull(
        module.accidentalType(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<AccidentalType> create(PitchModule module) {
    return new PitchModule_AccidentalTypeFactory(module);
  }
}
