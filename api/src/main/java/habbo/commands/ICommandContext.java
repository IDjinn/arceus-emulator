package habbo.commands;

import habbo.habbos.IHabbo;
import habbo.internationalization.LocalizedString;
import habbo.rooms.IRoom;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;

import java.util.Optional;

public interface ICommandContext {
    int getCurrentArg();

    void setCurrentArg(int currentArg);

    IPlayerEntity getPlayer();

    Optional<Integer> popInt();

    Optional<Double> popDouble();

    Optional<String> popArg();

    Optional<IHabbo> popHabbo();

    Optional<IRoomEntity> popEntity();

    Optional<IRoom> popRoom();

    Optional<String> popText();

    void whisper(LocalizedString message);

    void shout(LocalizedString message);

    void talk(LocalizedString message);
}
