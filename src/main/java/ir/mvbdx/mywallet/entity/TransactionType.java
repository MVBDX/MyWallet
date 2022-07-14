package ir.mvbdx.mywallet.entity;

public enum TransactionType {
    EXPENSE("Expense"),
    INCOME("Income"),
    TRANSFER("Transfer");

    private final String displayValue;

    private TransactionType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
