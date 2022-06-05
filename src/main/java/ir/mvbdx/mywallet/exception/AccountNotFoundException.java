package ir.mvbdx.mywallet.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Account id not found: " + id);
    }
}
