package com.twozo.smapp.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface GenericDao<T,U> {

    boolean add(final T t);

    boolean delete(final U u);

    boolean update(final U u,int updateType);

}
