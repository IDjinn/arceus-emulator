package habbo.rooms;

public enum RoomRightLevel {
    None,

    Rights,

    GuildRights,
    GuildAdmin,

    Owner,

    Moderator,

    ;

    public boolean equals(RoomRightLevel level) {
        return this.ordinal() == level.ordinal();
    }

    public boolean isEqualOrGreaterThan(RoomRightLevel level) {
        return this.ordinal() >= level.ordinal();
    }

    public boolean isGreaterThan(RoomRightLevel level) {
        return this.ordinal() > level.ordinal();
    }

    public boolean isLessThan(RoomRightLevel level) {
        return this.ordinal() < level.ordinal();
    }

    public boolean isGuildRights() {
        return this == GuildRights || this == GuildAdmin;
    }

    public boolean isOwner() {
        return this == Owner || this == Moderator;
    }
}