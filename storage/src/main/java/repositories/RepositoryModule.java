package repositories;

import com.google.inject.AbstractModule;
import repositories.habbo.HabboRepository;
import storage.repositories.habbo.IHabboRepository;

public class RepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IHabboRepository.class).to(HabboRepository.class);
    }
}
