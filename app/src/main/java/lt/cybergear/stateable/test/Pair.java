package lt.cybergear.stateable.test;

/**
 * Created by Marius Kavoliūnas on 15.3.11.
 */
public class Pair<T, I> {

    T first;
    I second;

    public Pair(T first, I second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public I getSecond() {
        return second;
    }

    public void setSecond(I second) {
        this.second = second;
    }
}
