package ir.mvbdx.mywallet.exception;

import java.io.Serial;

public class DBException {

    //Entity is not exist
    public static class EntityNotFoundException extends BaseException {
        @Serial
        private static final long serialVersionUID = -8386874640463845582L;

        public EntityNotFoundException(String entity, Long id) {
            super(String.format("%s with Id %d not found.", entity, id));
        }
    }

    //Entity have relation, like foreign key constraint
    public static class EntityHaveRelationException extends BaseException {
        @Serial
        private static final long serialVersionUID = 442966684752031782L;

        public EntityHaveRelationException(String entity, Long id) {
            super(String.format("%s with Id %d have relation with another entity and can't be delete.", entity, id));
        }
    }

    //SQL execution error
    public static class BadExecution extends BaseException {
        @Serial
        private static final long serialVersionUID = 3555714415375055302L;

        public BadExecution(String msg) {
            super(msg);
        }
    }

    //No data exist where we expect at least one row
    public static class NoData extends BaseException {
        @Serial
        private static final long serialVersionUID = 8777415230393628334L;

        public NoData(String msg) {
            super(msg);
        }
    }

    //Multiple rows exist where we expect only single row
    public static class MoreData extends BaseException {
        @Serial
        private static final long serialVersionUID = -3987707665150073980L;

        public MoreData(String msg) {
            super(msg);
        }
    }

    //Invalid parameters error
    public static class InvalidParam extends BaseException {
        @Serial
        private static final long serialVersionUID = 4235225697094262603L;

        public InvalidParam(String msg) {
            super(msg);
        }
    }
}