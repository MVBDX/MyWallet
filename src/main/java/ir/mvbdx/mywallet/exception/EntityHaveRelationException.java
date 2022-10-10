package ir.mvbdx.mywallet.exception;

public class EntityHaveRelationException extends RuntimeException {
    public EntityHaveRelationException(String entity, Long id) {
        super(String.format("%s with Id %d have relation with another entity and can't be delete.", entity, id));
    }
}
