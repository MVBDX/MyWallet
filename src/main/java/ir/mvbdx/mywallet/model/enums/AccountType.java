package ir.mvbdx.mywallet.model.enums;

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