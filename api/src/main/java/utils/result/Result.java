package utils.result;

import java.util.Objects;
import java.util.Optional;

public interface Result<T, E> extends Iterable<T> {

    static <T, E> Result<T, E> ok(final T ok) {
        return new ResultOk<T, E>(Objects.requireNonNull(ok));
    }

    static <T, E> Result<T, E> error(final E error) {
        return new ResultError<T, E>(Objects.requireNonNull(error));
    }

    boolean isSuccess();

    boolean isFailure();

    Optional<T> getResult();

    Optional<E> getError();

    T unwrapResult();

    E unwrapError();

//    E expectError();
}
