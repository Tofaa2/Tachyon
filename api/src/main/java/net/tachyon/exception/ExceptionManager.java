package net.tachyon.exception;

import net.tachyon.Tachyon;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Manages the handling of exceptions.
 */
public final class ExceptionManager {

    private ExceptionHandler exceptionHandler = Throwable::printStackTrace;

    /**
     * Handles an exception, if no {@link ExceptionHandler} is set, it just prints the stack trace.
     *
     * @param e the occurred exception
     */
    public void handleException(Throwable e) {
        if (e instanceof OutOfMemoryError oom) {
            oom.printStackTrace();
            Tachyon.getServer().stopCleanly(); // OOM handles differently
        }
        this.getExceptionHandler().handleException(e);
    }

    /**
     * Changes the exception handler, to allow custom exception handling.
     *
     * @param exceptionHandler the new {@link ExceptionHandler}, can be set to null to apply the default provider
     */
    public void setExceptionHandler(@Nullable ExceptionHandler exceptionHandler) {
        this.exceptionHandler = Objects.requireNonNullElseGet(exceptionHandler, () -> Throwable::printStackTrace);
    }

    /**
     * Retrieves the current {@link ExceptionHandler}, can be the default one if none is defined.
     *
     * @return the current {@link ExceptionHandler}
     */
    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }
}
