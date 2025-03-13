package telran.citizens.dao;

import telran.citizens.model.Person;

import java.util.*;
import java.util.function.Predicate;

public class CitizensImpl implements Citizens {

    public Collection<Person> idCollection;

    private static final Comparator<Person> lastNameComparator = (p1, p2) -> {
        int res = p1.getLastName().compareToIgnoreCase(p2.getLastName());
        return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
    };
    private static final Comparator<Person> ageComparator = (p1, p2) -> {
        int res = Integer.compare(p1.getAge(), p2.getAge());
        return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
    };

    public CitizensImpl() {
        this.idCollection = new ArrayList<>();
    }

    public CitizensImpl(List<Person> citizens) {
        this();
        this.idCollection.addAll(citizens);
    }

    //O(n)
    @Override
    public boolean add(Person person) {
        if (person == null || idCollection.contains(person)) {
            return false;
        }
        idCollection.add(person);
        return true;
    }

    //O(n)
    @Override
    public boolean remove(int id) {
        Person person = find(id);
        if (person == null) {
            return false;
        }
        idCollection.remove(person);
        return true;
    }

    //O(n)
    @Override
    public Person find(int id) {
        for (Person person : idCollection) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    //O(n)
    @Override
    public Iterable<Person> find(int minAge, int maxAge) {
        return findByPredicate(p -> p.getAge() >= minAge && p.getAge() <= maxAge);
    }

    //O(n)
    @Override
    public Iterable<Person> find(String lastName) {
        return findByPredicate(p -> p.getLastName().equals(lastName));
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonsSortedById() {
        ArrayList<Person> res = (ArrayList<Person>) idCollection;
        res.sort((p1, p2) -> p1.compareTo(p2));
        return res;
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonsSortedByAge() {
        ArrayList<Person> res = (ArrayList<Person>) idCollection;
        res.sort(ageComparator);
        return res;
    }

    //O(n*log(n))
    @Override
    public Iterable<Person> getAllPersonsSortedByLastName() {
        ArrayList<Person> res = (ArrayList<Person>) idCollection;
        res.sort(lastNameComparator);
        return res;
    }

    private Iterable<Person> findByPredicate(Predicate<Person> predicate) {
        Collection<Person> res = new ArrayList<>();
        for (Person person : idCollection) {
            if (predicate.test(person)) {
                res.add(person);
            }
        }
        return res;
    }

    //O(1)
    @Override
    public int size() {
        return idCollection.size();
    }
}
