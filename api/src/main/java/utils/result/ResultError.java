package utils.result;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;

public class ResultError<T, E> implements Result<T, E> {
    private final E error;

    public ResultError(E error) {
        this.error = error;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public Optional<T> getResult() {
        return Optional.empty();
    }

    @Override
    public Optional<E> getError() {
        return Optional.of(this.error);
    }

    @Override
    public T unwrapResult() {
//        return this.error;
        return null;
    }

    @Override
    public E unwrapError() {
//        throw new ResultException("Result has no error to unwrap");
        return this.error;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                throw new ArrayIndexOutOfBoundsException();
            }
        };
    }
}
