package habbohotel.users.providers;

public interface ILoginProvider {
    public boolean handle(String authTicket);
}
