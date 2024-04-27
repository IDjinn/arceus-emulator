package utils.result;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public interface Result<T, E> extends Iterable<T> {
    @SuppressWarnings("rawtypes")
    static final Result sucess = new ResultOk<>(true);
    @SuppressWarnings("rawtypes")
    static final Result error = new ResultError<>(false);

    @SuppressWarnings("unchecked")
    static <T, E> Result<Boolean, E> of(final boolean ok) {
        if (ok)
            return sucess;
        return error;
    }

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

    void match(
            final Consumer<T> onSuccess,
            final Consumer<E> onError
    );
}
