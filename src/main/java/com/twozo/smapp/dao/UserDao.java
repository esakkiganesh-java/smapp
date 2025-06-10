package com.twozo.smapp.dao;

import java.util.Collection;
import com.twozo.smapp.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends Dao<User> {

    User getUser(final String phone);

    Collection<User> getAllUser();

    int getUserId(final String phone);

    boolean addToFavourites(final int userId, final int otherUserId);

    boolean removeFromFavourites(final int userId, final int otherUserId);

}
