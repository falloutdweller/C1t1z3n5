package telran.citizens.dao;

import telran.citizens.model.Person;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class CitizensImpl implements Citizens {

    private List<Person> idCollection;
    private List<Person> lastNameCollection;
    private List<Person> ageCollection;


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
        this.lastNameCollection = new ArrayList<>();
        this.ageCollection = new ArrayList<>();

    }

    public CitizensImpl(List<Person> citizens) {
        this();
        citizens.forEach(p -> add(p));

    }

    //O(n)
    @Override
    public boolean add(Person person) {
        if (person == null || find(person.getId()) != null) {
            return false;
        }
        int index = Collections.binarySearch(idCollection, person);
        index = index >= 0 ? index : -index - 1;
        idCollection.add(index, person);
        index = Collections.binarySearch(lastNameCollection, person, lastNameComparator);
        index = index >= 0 ? index : -index - 1;
        lastNameCollection.add(index, person);
        index = Collections.binarySearch(ageCollection, person, ageComparator);
        index = index >= 0 ? index : -index - 1;
        ageCollection.add(index, person);
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
        lastNameCollection.remove(person);
        ageCollection.remove(person);
        return true;
    }

    //O(log(n))
    @Override
    public Person find(int id) {
        int index = Collections.binarySearch(idCollection, new Person(id, null, null, null));
        if (index >= 0) {
            return idCollection.get(index);
        }
        return null;
    }

    //O(log(n))
    @Override
    public Iterable<Person> find(int minAge, int maxAge) {
        Person person = new Person(Integer.MIN_VALUE, null, null, LocalDate.now().minusYears(minAge));
        int indexFrom = -Collections.binarySearch(ageCollection, person, ageComparator) - 1;
        person = new Person(Integer.MAX_VALUE, null, null, LocalDate.now().minusYears(maxAge));
        int indexTo = -Collections.binarySearch(ageCollection, person, ageComparator) - 1;
        
        return ageCollection.subList(indexFrom, indexTo);
    }

    //O(log(n))
    @Override
    public Iterable<Person> find(String lastName) {
        Person person = new Person(Integer.MIN_VALUE, null, lastName, null);
        int indexFrom = -Collections.binarySearch(lastNameCollection, person, lastNameComparator) -1;
        person = new Person(Integer.MAX_VALUE, null, lastName, null);
        int indexTo = -Collections.binarySearch(lastNameCollection, person, lastNameComparator)-1;

        return lastNameCollection.subList(indexFrom, indexTo);

    }

    //O(1)
    @Override
    public Iterable<Person> getAllPersonsSortedById() {
        return idCollection;
    }

    //O(1)
    @Override
    public Iterable<Person> getAllPersonsSortedByAge() {
        return ageCollection;
    }

    //O(1)
    @Override
    public Iterable<Person> getAllPersonsSortedByLastName() {
        return lastNameCollection;
    }

//    private Iterable<Person> findByPredicate(Predicate<Person> predicate, List<Person> collection) {
//        Collection<Person> res = new ArrayList<>();
//        for (Person person : collection) {
//            if (predicate.test(person)) {
//                res.add(person);
//            }
//        }
//        return res;
//    }

    //O(1)
    @Override
    public int size() {
        return idCollection.size();
    }
}
