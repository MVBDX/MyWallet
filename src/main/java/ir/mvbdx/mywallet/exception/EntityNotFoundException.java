package ir.mvbdx.mywallet.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, String entity) {
        super(entity + " id not found: " + id);
    }
}
