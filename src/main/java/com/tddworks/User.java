package com.tddworks;

import java.util.UUID;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

import org.springframework.data.repository.CrudRepository;

/**
 * @author hanrw
 * @date 10/3/24 4:20 PM
 */
public class User implements AggregateRoot<User, User.UserIdentifier> {

    private UserIdentifier id;
    private String name;

    @Override
    public UserIdentifier getId() {
        return id;
    }

    public record UserIdentifier(UUID id) implements Identifier {}
}

interface UserRepository extends CrudRepository<User, User.UserIdentifier> {}