package com.twozo.smapp.dao;

public interface Dao<Type> {

    boolean add(final Type type);

    boolean delete(final Type type);

    boolean update(final Type type, final String updateType);

}
