package ir.mvbdx.mywallet.enumeration;

public enum AccountType {
    CASH("Cash"),
    BANK_ACCOUNT("Bank Account"),
    CRYPTO_CURRENCY("Cryptocurrency");

    private final String displayValue;

    AccountType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}