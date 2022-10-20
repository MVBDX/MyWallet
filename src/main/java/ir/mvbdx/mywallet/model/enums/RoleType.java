package ir.mvbdx.mywallet.model.enums;

public enum RoleType {
    ROLE_CUSTOMER("Customer"),
    ROLE_MANAGER("Manager"),
    ROLE_ADMIN("Admin");

    private final String displayValue;

    RoleType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}