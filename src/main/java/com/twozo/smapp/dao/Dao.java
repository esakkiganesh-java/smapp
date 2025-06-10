package com.twozo.smapp.dao;

public interface Dao<T> {

    String add(final T type);

    String delete(final T type);

    String update(final T type, final String updateType);

}
