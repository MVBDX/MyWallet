package ir.mvbdx.mywallet.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, String entity) {
        super(String.format("%s with Id %d not found.", entity, id));
    }
}
