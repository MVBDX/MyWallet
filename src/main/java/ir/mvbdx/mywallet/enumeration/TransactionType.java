package ir.mvbdx.mywallet.enumeration;

public enum TransactionType {
    WITHDRAW("Withdraw"),
    DEPOSIT("Deposit"),
    TRANSFER("Transfer");

    private final String displayValue;

    TransactionType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
