package game.snake.utility;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ObjectCache<E> implements Collection<E> {
    public static int initialSize = 20;
    public static int created = 0;

    private final Stack<E> IN_USE = new Stack<>();
    private final Queue<E> IN_CACHE = new LinkedList<>();
    private final Supplier<E> CONSTRUCTOR;
    private int expansionSize = 10;

    private Consumer<E> onCreation = (object) -> {created++;};
    private Consumer<E> onRetrieve = (object) -> {};
    private Consumer<E> onReturn = (object) -> {};

    public ObjectCache(Supplier<E> constructor) {
        CONSTRUCTOR = constructor;
        expand(initialSize);
    }
    public ObjectCache(Supplier<E> constructor, int initialSize) {
        CONSTRUCTOR = constructor;
        expand(initialSize);
    }
    public ObjectCache(Supplier<E> constructor, int initialSize, int expansionSize) {
        this.expansionSize = expansionSize;
        CONSTRUCTOR = constructor;
        expand(initialSize);
    }

    private void addObject() {
        E object = CONSTRUCTOR.get();
        onCreation.accept(object);
        IN_CACHE.add(object);
    }

    public void expand() {
        for (int i=0; i<expansionSize; i++) {addObject();}
    }
    public void expand(int amount) {
        for (int i=0; i<amount; i++) {addObject();}
    }

    public E getObject() {
        if (IN_CACHE.isEmpty()) {expand();}
        E object = IN_CACHE.remove();

        onRetrieve.accept(object);
        IN_USE.add(object);
        return object;
    }

    public void returnObject(E object) {
        if (IN_USE.contains(object)) {
            IN_USE.remove(object);

            onReturn.accept(object);
            IN_CACHE.add(object);
        }
    }

    public void setOnCreation(Consumer<E> onCreation) {
        this.onCreation = onCreation;
        for (E object : IN_CACHE) {onCreation.accept(object);}
    }

    public void setOnRetrieve(Consumer<E> onRetrieve) {
        this.onRetrieve = onRetrieve;
    }

    public void setOnReturn(Consumer<E> onReturn) {
        this.onReturn = onReturn;
    }

    @Override public void clear() {
        IN_USE.clear();
        IN_CACHE.clear();
    }

    @Override public int size() {
        return IN_CACHE.size() + IN_USE.size();
    }

    @Override public boolean isEmpty() {
        return false;
    }

    @Override public boolean contains(Object o) {
        return IN_CACHE.contains(o) || IN_USE.contains(o);
    }

    @Override public Iterator iterator() {
        return IN_USE.iterator();
    }

    @Override
    public Object[] toArray() {
        return IN_USE.toArray();
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
