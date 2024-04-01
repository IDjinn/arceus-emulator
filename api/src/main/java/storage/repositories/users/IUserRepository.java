package storage.repositories.users;

public interface IUserRepository {
    public void getUserByAuthTicket(String authTicket);
}
