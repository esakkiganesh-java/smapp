package com.twozo.smapp.dao;

public interface Dao<Type> {

    String add(final Type type);

    String delete(final Type type);

    String update(final Type type, final String updateType);

}
