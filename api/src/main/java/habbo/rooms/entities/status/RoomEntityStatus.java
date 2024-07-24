package habbo.rooms.entities.status;

import java.util.EnumSet;
import java.util.Set;

public enum RoomEntityStatus implements RequiresStatus, ExcludesStatus {
    MOVE("mv"),

    SIT_IN("sit-in"),
    SIT("sit") {
        @Override
        public Set<RoomEntityStatus> getExcludes() {
            return EnumSet.of(MOVE);
        }
    },
    SIT_OUT("sit-out"),

    LAY_IN("lay-in"),
    LAY("lay") {
        @Override
        public Set<RoomEntityStatus> getExcludes() {
            return EnumSet.of(MOVE);
        }
    },
    LAY_OUT("lay-out"),

    FLAT_CONTROL("flatctrl"),
    SIGN("sign"),
    GESTURE("gst"),
    WAVE("wav"),
    TRADING("trd"),

    DIP("dip"),

    EAT_IN("eat-in"),
    EAT("eat"),
    EAT_OUT("eat-out"),

    BEG("beg") {
        @Override
        public Set<RoomEntityStatus> getExcludes() {
            return EnumSet.of(MOVE);
        }
    },

    DEAD_IN("ded-in"),
    DEAD("ded") {
        @Override
        public Set<RoomEntityStatus> getExcludes() {
            return EnumSet.of(MOVE);
        }
    },
    DEAD_OUT("ded-out"),

    JUMP_IN("jmp-in"),
    JUMP("jmp") {
        @Override
        public Set<RoomEntityStatus> getExcludes() {
            return EnumSet.of(MOVE);
        }
    },
    JUMP_OUT("jmp-out"),

    PLAY_IN("pla-in"),
    PLAY("pla") {
        @Override
        public Set<RoomEntityStatus> getExcludes() {
            return EnumSet.of(MOVE);
        }
    },
    PLAY_OUT("pla-out"),

    SPEAK("spk"),
    CROAK("crk"),
    RELAX("rlx"),
    WINGS("wng") {
        @Override
        public Set<RoomEntityStatus> getExcludes() {
            return EnumSet.of(MOVE);
        }
    },
    FLAME("flm"),
    RIP("rip"),
    GROW("grw"),
    GROW_1("grw1"),
    GROW_2("grw2"),
    GROW_3("grw3"),
    GROW_4("grw4"),
    GROW_5("grw5"),
    GROW_6("grw6"),
    GROW_7("grw7"),

    KICK("kck"),
    WAG_TAIL("wag"),
    DANCE("dan"),
    AMS("ams"),
    SWIM("swm"),
    TURN("trn"),

    SRP("srp"),
    SRP_IN("srp-in"),

    SLEEP_IN("slp-in"),
    SLEEP("slp") {
        @Override
        public Set<RoomEntityStatus> getExcludes() {
            return EnumSet.of(MOVE);
        }
    },
    SLEEP_OUT("slp-out");

    public final String key;

    RoomEntityStatus(String key) {
        this.key = key;
    }

    public static RoomEntityStatus fromString(String key) {
        for (RoomEntityStatus status : values()) {
            if (status.key.equalsIgnoreCase(key)) {
                return status;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.key;
    }
}