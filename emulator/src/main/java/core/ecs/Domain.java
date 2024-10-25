package core.ecs;

import com.google.inject.Singleton;
import dev.dominion.ecs.api.Dominion;

@Singleton
public final class Domain implements IDomain {
    private final Dominion dominion;

    public Domain(){
        dominion = Dominion.create();
    }

    @Override
    public Dominion getDominion() {
        return dominion;
    }
}
