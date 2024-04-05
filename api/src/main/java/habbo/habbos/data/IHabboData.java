package habbo.habbos.data;

import habbo.habbos.IHabboComponent;

public interface IHabboData extends IHabboComponent {
    int getId();
    int getRank();

    String getUsername();

    String getEmail();

    String getMotto();

    String getLook();

    String getGender();

    int getCredits();

    int getPixels();

    int getDiamonds();

    int getSeasonalPoints();

    String getAccountCreated();

    String getLastLogin();

    String getLastOnline();

    boolean isOnline();

    String getAuthTicket();

    String getRegisterIp();

    String getCurrentIp();

    String getMachineId();

    int getHomeRoom();
}
