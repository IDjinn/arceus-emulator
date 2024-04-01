package repositories;

import com.google.inject.AbstractModule;
import repositories.users.UserRepository;
import storage.repositories.users.IUserRepository;

public class RepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IUserRepository.class).to(UserRepository.class);
    }
}
