package modules;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PitchModule_AccidentalDegreeFactory implements Factory<Integer> {
  private final PitchModule module;

  public PitchModule_AccidentalDegreeFactory(PitchModule module) {
    this.module = module;
  }

  @Override
  public Integer get() {
    return module.accidentalDegree();
  }

  public static Factory<Integer> create(PitchModule module) {
    return new PitchModule_AccidentalDegreeFactory(module);
  }
}
