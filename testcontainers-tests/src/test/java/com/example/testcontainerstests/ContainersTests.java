package com.example.testcontainerstests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.example.testcontainerstests.person.Person;
import com.example.testcontainerstests.person.PersonRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class ContainersTests {

    // will be shared between test methods
    // @Container
    // private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer();

    // private static final String INIT_SCRIPT_PATH="db/embedded-postgres-init.sql";

    // will be started before and stopped after each test method
    @Container
    private static JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer<>()
        // .withInitScript(INIT_SCRIPT_PATH)
        .withDatabaseName("foo")
        .withUsername("foo")
        .withPassword("secret");

    @DynamicPropertySource
    static void postgresqlProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Autowired
    PersonRepository personRepository;

    @Test
    void test() {
        // assertTrue(MY_SQL_CONTAINER.isRunning());
        assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    void testPersistence() {

        final Person person = personRepository.save(Person.builder().firstName("Ythalo").lastName("Saldanha").build());

        Assertions.assertThat(person).isNotNull();

        Assertions.assertThat(person.getId()).isNotNull();

    }

    @Test
    void testRetrieving() {

        
        final ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny()
            .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        ;

        Person personExample = Person.builder().firstName("YTHalo").build();

        final Example<Person> example = Example.of(personExample, customExampleMatcher);

        final Optional<Person> person = personRepository.findOne(example);

        Assertions.assertThat(person.get()).isNotNull();

        Assertions.assertThat(person.get().getId()).isNotNull();

    }


}
