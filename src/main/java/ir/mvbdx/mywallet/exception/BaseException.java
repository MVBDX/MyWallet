package ir.mvbdx.mywallet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaseException extends RuntimeException {
    private String message;
}