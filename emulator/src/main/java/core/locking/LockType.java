package core.locking;

public enum LockType implements ILockType {
    CATALOG_PURCHASE("catalog_purchase"),

    ;

    private final String code;

    LockType(String code) {
        this.code = code;
    }

    @Override
    public String getLockCode() {
        return this.code;
    }
}
