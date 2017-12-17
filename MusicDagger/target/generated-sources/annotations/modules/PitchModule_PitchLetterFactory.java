package modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import models.PitchLetter;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PitchModule_PitchLetterFactory implements Factory<PitchLetter> {
  private final PitchModule module;

  public PitchModule_PitchLetterFactory(PitchModule module) {
    this.module = module;
  }

  @Override
  public PitchLetter get() {
    return Preconditions.checkNotNull(
        module.pitchLetter(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<PitchLetter> create(PitchModule module) {
    return new PitchModule_PitchLetterFactory(module);
  }
}
