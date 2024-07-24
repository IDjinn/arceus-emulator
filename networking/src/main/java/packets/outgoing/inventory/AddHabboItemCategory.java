package packets.outgoing.inventory;

public enum AddHabboItemCategory {
    Unknown(0),
    OwnedFurni(1),
    RentedFurni(2),
    Pet(3),
    Badge(4),
    Bot(5),
    Game(6),

    ;

    private final int id;

    AddHabboItemCategory(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
