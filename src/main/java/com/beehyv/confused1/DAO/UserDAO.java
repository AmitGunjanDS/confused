package com.beehyv.confused1.DAO;

import com.beehyv.confused1.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, String> {

    boolean existsAllByUsername(String Username);

}
