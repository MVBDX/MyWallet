package ir.mvbdx.mywallet.enumeration;

public enum RoleType {
    CUSTOMER("Customer"),
    MANAGER("Manager"),
    ADMIN("Admin");

    private final String displayValue;

    RoleType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}