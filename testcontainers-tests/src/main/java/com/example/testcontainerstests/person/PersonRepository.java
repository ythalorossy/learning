package com.example.testcontainerstests.person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

    List<Person> findByFirstName(String firstName);

	Optional<Person> findByFirstNameStartingWith(String firstName);
}
