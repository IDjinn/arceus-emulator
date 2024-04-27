package habbo;

import utils.result.GameError;

public final class GameErrors {
    public final class Packets {
        public final class Generic {
            public static final GameError MUST_BE_IN_ROOM = new GameError("packet.error.must_be_in_room");
        }

        public final class Room {
            public final class Entities {
                public static final GameError CANNOT_WALK = new GameError("packet.error.room.entities.cant_walk");
                public static final GameError CANNOT_LOOK = new GameError("packet.error.room.entities.cant_look");
                public static final GameError CANNOT_SIT = new GameError("packet.error.room.entities.cant_sit");
                public static final GameError CANNOT_LAY = new GameError("packet.error.room.entities.cant_lay");
                public static final GameError CANNOT_TALK = new GameError("packet.error.room.entities.cant_talk");
                public static final GameError CANNOT_WHISPER = new GameError("packet.error.room.entities.cant_whisper");
                public static final GameError CANNOT_SHOUT = new GameError("packet.error.room.entities.cant_shout");


                public static final GameError INVALID_WALK = new GameError("packet.error.room.entities.invalid_walk");
            }
        }
    }
}
