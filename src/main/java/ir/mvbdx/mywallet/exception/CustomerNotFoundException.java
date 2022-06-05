package ir.mvbdx.mywallet.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Customer id not found: " + id);
    }
}
