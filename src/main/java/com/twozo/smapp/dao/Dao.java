package com.twozo.smapp.dao;

public interface Dao<T> {

    void add(final T type);

    void delete(final T type);

    void update(final T type, final String updateType);

}
