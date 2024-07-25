package packets.dto.outgoing.inventory;

public enum AddHabboItemCategoryComposerDTO {
    Unknown(0),
    OwnedFurni(1),
    RentedFurni(2),
    Pet(3),
    Badge(4),
    Bot(5),
    Game(6),

    ;

    private final int id;

    AddHabboItemCategoryComposerDTO(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
