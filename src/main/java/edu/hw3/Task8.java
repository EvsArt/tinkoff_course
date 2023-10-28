package edu.hw3;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class Task8 {

    private Task8() {
    }

    static class BackwardIterator<T> implements Iterator<T> {

        List<T> collection;
        T curElem;
        int curElemPos;

        BackwardIterator(List<T> collection) {
            this.collection = collection;
            this.curElem = collection.getLast();
            this.curElemPos = collection.size();
        }

        @Override
        public boolean hasNext() {
            return curElemPos > 0;
        }

        @Override
        public T next() {
            if (curElemPos < 1) {
                throw new NoSuchElementException();
            }
            curElemPos--;
            curElem = collection.get(curElemPos);
            return curElem;
        }
    }

}
