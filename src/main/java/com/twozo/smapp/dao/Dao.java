package com.twozo.smapp.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface Dao<T> {

    boolean add(final T t);

    boolean delete(final T t);

    boolean update(final T t,int updateType);

}
