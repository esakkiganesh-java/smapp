package com.twozo.app.dao;

import org.springframework.stereotype.Component;

@Component
public interface GeneralDao<T,U> {

    boolean add(final T t);

    boolean delete(final U u);

    boolean update(final U u,int updateType);

}
