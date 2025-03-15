package telran.citizens.test;

import telran.citizens.dao.Citizens;
import telran.citizens.dao.CitizensImpl;
import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CitizensTest {

    private Citizens citizens;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        citizens = new CitizensImpl();
        citizens.add(new Person(1, "Alice", "Smith", LocalDate.of(1990, 5, 15)));
        citizens.add(new Person(2, "Bob", "Johnson", LocalDate.of(1985, 10, 20)));
        citizens.add(new Person(3, "Charlie", "Brown", LocalDate.of(1995, 3, 8)));
        citizens.add(new Person(4, "David", "Williams", LocalDate.of(2000, 7, 25)));
        citizens.add(new Person(5, "Eve", "Davis", LocalDate.of(1988, 12, 1)));
    }

    @org.junit.jupiter.api.Test
    void testAdd() {
        Person person = new Person(6, "Frank", "Miller", LocalDate.of(1992, 2, 11));
        assertTrue(citizens.add(person));
        assertEquals(6, citizens.size());
        assertFalse(citizens.add(person));
        assertFalse(citizens.add(null));
        assertEquals(6, citizens.size());

    }

    @org.junit.jupiter.api.Test
    void testRemove() {
        assertTrue(citizens.remove(1));
        assertEquals(4, citizens.size());
        assertFalse(citizens.remove(1));
        assertEquals(4, citizens.size());
    }

    @org.junit.jupiter.api.Test
    void testFindById() {
        assertEquals(citizens.find(1),
                new Person(1, "Alice", "Smith", LocalDate.of(1990, 5, 15)));
        assertNull(citizens.find(1111));
    }

    @org.junit.jupiter.api.Test
    void testFindByMinAndMaxAge() {
        Iterable<Person> actual = citizens.find(20, 35);
        List<Person> expected = List.of(
                new Person(4, "David", "Williams", LocalDate.of(2000, 7, 25)),
                new Person(3, "Charlie", "Brown", LocalDate.of(1995, 3, 8)),
                new Person(1, "Alice", "Smith", LocalDate.of(1990, 5, 15))
        );
        assertIterableEquals(expected, actual);
        actual = citizens.find(10, 20);
        assertFalse(actual.iterator().hasNext());
    }

    @org.junit.jupiter.api.Test
    void testFindByLastName() {
        Iterable<Person> actual = citizens.find("Johnson");
        Iterable<Person> expected = List.of(
                new Person(2, "Bob", "Johnson", LocalDate.of(1985, 10, 20)));
        assertIterableEquals(expected, actual);
        actual = citizens.find("Doe");
        assertFalse(actual.iterator().hasNext());
    }

    @org.junit.jupiter.api.Test
    void testGetAllPersonsSortedById() {
        List<Person> expected = List.of(
                new Person(1, "Alice", "Smith", LocalDate.of(1990, 5, 15)),
                new Person(2, "Bob", "Johnson", LocalDate.of(1985, 10, 20)),
                new Person(3, "Charlie", "Brown", LocalDate.of(1995, 3, 8)),
                new Person(4, "David", "Williams", LocalDate.of(2000, 7, 25)),
                new Person(5, "Eve", "Davis", LocalDate.of(1988, 12, 1))
        );
        Iterable<Person> actual = citizens.getAllPersonsSortedById();
        assertIterableEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testGetAllPersonsSortedByAge() {
        List<Person> expected = List.of(
                new Person(4, "David", "Williams", LocalDate.of(2000, 7, 25)),
                new Person(3, "Charlie", "Brown", LocalDate.of(1995, 3, 8)),
                new Person(1, "Alice", "Smith", LocalDate.of(1990, 5, 15)),
                new Person(5, "Eve", "Davis", LocalDate.of(1988, 12, 1)),
                new Person(2, "Bob", "Johnson", LocalDate.of(1985, 10, 20))
        );
        Iterable<Person> actual = citizens.getAllPersonsSortedByAge();
        assertIterableEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testGetAllPersonsSortedByLastName() {
        List<Person> expected = List.of(
                new Person(3, "Charlie", "Brown", LocalDate.of(1995, 3, 8)),
                new Person(5, "Eve", "Davis", LocalDate.of(1988, 12, 1)),
                new Person(2, "Bob", "Johnson", LocalDate.of(1985, 10, 20)),
                new Person(1, "Alice", "Smith", LocalDate.of(1990, 5, 15)),
                new Person(4, "David", "Williams", LocalDate.of(2000, 7, 25))
        );
        Iterable<Person> actual = citizens.getAllPersonsSortedByLastName();
        assertIterableEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void testSize() {
        assertEquals(5, citizens.size());
    }
}