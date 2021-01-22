package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static Integer usersCount = 3;

    static {
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Eve", new Date()));
        users.add(new User(3, "Jack", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {

        if (Objects.isNull(user.getId())) {
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        return users
            .stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .orElse(null);
    }

    public User deleteById(int id) {

        User deletedUser = users
            .stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .map(us -> {
                users.remove(us);
                return us;
            })
            .orElse(null);

        return deletedUser;
    }
}
