package components;

import dagger.Component;
import models.Pitch;
import modules.PitchModule;
import scopes.PitchScope;

@PitchScope
@Component(modules = {PitchModule.class})
public interface PitchComponent {
    Pitch pitch();
}
