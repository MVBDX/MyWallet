package ir.mvbdx.mywallet.exception;

public class EntityHaveRelationException extends RuntimeException {
    public EntityHaveRelationException(String entity) {
        super(entity + " have relation with another entity and can't be delete.");
    }
}
