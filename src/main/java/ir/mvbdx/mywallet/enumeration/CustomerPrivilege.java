package ir.mvbdx.mywallet.enumeration;

public enum CustomerPrivilege {
    TRANSACTION_READ("transaction:read"),
    TRANSACTION_WRITE("transaction:write"),
    CATEGORY_READ("category:read"),
    CATEGORY_WRITE("category:write"),
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write");

    private final String permission;

    CustomerPrivilege(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
