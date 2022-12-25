package com.rest.webservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static int userCount = 0;

    static {
        users.add(new User(++userCount, "John", LocalDate.now().minusYears(34)));
        users.add(new User(++userCount, "Justin", LocalDate.now().minusYears(40)));
        users.add(new User(++userCount, "Zayn", LocalDate.now().minusYears(54)));
    }

    public List<User> findAll() {
        return users;
    }

    public User findUserById(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream()
                .filter(predicate).findFirst().orElse(null);
    }

    public User createUser(User user) {
        user.setId(++userCount);
        users.add(user);
        return user;
    }
}
