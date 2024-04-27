package utils.result;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;

public class ResultOk<T, E> implements Result<T, E> {
    private final T value;

    public ResultOk(T value) {
        this.value = value;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public Optional<T> getResult() {
        return Optional.of(this.value);
    }

    @Override
    public Optional<E> getError() {
        return Optional.empty();
    }

    @Override
    public T unwrapResult() {
        return this.value;
    }

    @Override
    public E unwrapError() {
//        throw new ResultException("Result has no error to unwrap");
        return null;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public T next() {
                this.hasNext = false;
                return ResultOk.this.value;
            }
        };
    }
}
