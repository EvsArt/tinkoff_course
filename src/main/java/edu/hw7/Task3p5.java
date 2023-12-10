package edu.hw7;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Task3p5 {

    static class MapPersonDatabase implements PersonDatabase {

        private final ReadWriteLock nameLock = new ReentrantReadWriteLock(true);
        private final Map<String, Set<Person>> nameToPersonSet = new HashMap<>();
        private final ReadWriteLock addressLock = new ReentrantReadWriteLock(true);
        private final Map<String, Set<Person>> addressToPersonSet = new HashMap<>();
        private final ReadWriteLock phoneLock = new ReentrantReadWriteLock(true);
        private final Map<String, Set<Person>> phoneToPersonSet = new HashMap<>();
        private final Map<Integer, Person> idToPerson = new HashMap<>();

        private Set<Person> getPersonsByName(String name) {
            nameLock.readLock().lock();
            try {
                return nameToPersonSet.get(name);
            } finally {
                nameLock.readLock().unlock();
            }
        }

        private Set<Person> getPersonsByAddress(String address) {
            addressLock.readLock().lock();
            try {
                return addressToPersonSet.get(address);
            } finally {
                addressLock.readLock().unlock();
            }
        }

        private Set<Person> getPersonsByPhone(String phone) {
            phoneLock.readLock().lock();
            try {
                return phoneToPersonSet.get(phone);
            } finally {
                phoneLock.readLock().unlock();
            }
        }

        private void putPersonByName(Person person) {
            nameLock.writeLock().lock();
            try {
                if (!nameToPersonSet.containsKey(person.name())) {
                    nameToPersonSet.put(person.name(), new HashSet<>());
                }
                nameToPersonSet.get(person.name()).add(person);
            } finally {
                nameLock.writeLock().unlock();
            }
        }

        private void putPersonByAddress(Person person) {
            addressLock.writeLock().lock();
            try {
                if (!addressToPersonSet.containsKey(person.address())) {
                    addressToPersonSet.put(person.address(), new HashSet<>());
                }
                addressToPersonSet.get(person.address()).add(person);
            } finally {
                addressLock.writeLock().unlock();
            }
        }

        private void putPersonByPhone(Person person) {
            phoneLock.writeLock().lock();
            try {
                if (!phoneToPersonSet.containsKey(person.phoneNumber())) {
                    phoneToPersonSet.put(person.phoneNumber(), new HashSet<>());
                }
                phoneToPersonSet.get(person.phoneNumber()).add(person);
            } finally {
                phoneLock.writeLock().unlock();
            }
        }

        private void removePersonName(Person person) {
            nameLock.writeLock().lock();
            try {
                Set<Person> personSet = nameToPersonSet.get(person.name());
                if (personSet != null) {
                    personSet.remove(person);
                }
            } finally {
                nameLock.writeLock().unlock();
            }
        }

        private void removePersonAddress(Person person) {
            addressLock.writeLock().lock();
            try {
                Set<Person> personSet = addressToPersonSet.get(person.address());
                if (personSet != null) {
                    personSet.remove(person);
                }
            } finally {
                addressLock.writeLock().unlock();
            }
        }

        private void removePersonPhone(Person person) {
            phoneLock.writeLock().lock();
            try {
                Set<Person> personSet = phoneToPersonSet.get(person.phoneNumber());
                if (personSet != null) {
                    personSet.remove(person);
                }
            } finally {
                phoneLock.writeLock().unlock();
            }
        }

        @Override
        public void add(Person person) {
            Thread addingNameThread = new Thread(() -> putPersonByName(person));
            Thread addingAddressThread = new Thread(() -> putPersonByAddress(person));
            Thread addingPhoneThread = new Thread(() -> putPersonByPhone(person));
            Thread addingIdThread = new Thread(() -> idToPerson.put(person.id(), person));

            addingNameThread.start();
            addingAddressThread.start();
            addingPhoneThread.start();
            addingIdThread.start();
        }

        @Override
        public void delete(int id) {
            Person person = idToPerson.remove(id);
            Thread deleteNameThread = new Thread(() -> removePersonName(person));
            Thread deleteAddressThread = new Thread(() -> removePersonAddress(person));
            Thread deletePhoneThread = new Thread(() -> removePersonPhone(person));

            deleteNameThread.start();
            deleteAddressThread.start();
            deletePhoneThread.start();
        }

        @Override
        public List<Person> findByName(String name) {

            List<Person> res = null;
            nameLock.readLock().lock();
            try {
                Set<Person> personSet = nameToPersonSet.get(name);
                if (personSet == null) {
                    return null;
                }
                res = personSet.stream()
                    .filter(person -> {
                        Set<Person> personsWithAddress = getPersonsByAddress(person.address());
                        return personsWithAddress != null && personsWithAddress.contains(person);
                    })
                    .filter(person -> {
                        Set<Person> personsWithPhone = getPersonsByPhone(person.phoneNumber());
                        return personsWithPhone != null && personsWithPhone.contains(person);
                    })
                    .toList();
            } finally {
                nameLock.readLock().unlock();
            }
            return res;
        }

        @Override
        public List<Person> findByAddress(String address) {

            List<Person> res = null;
            addressLock.readLock().lock();
            try {
                Set<Person> personSet = addressToPersonSet.get(address);
                if (personSet == null) {
                    return null;
                }
                res = personSet.stream()
                    .filter(person -> {
                        Set<Person> personsWithName = getPersonsByName(person.name());
                        return personsWithName != null && personsWithName.contains(person);
                    })
                    .filter(person -> {
                        Set<Person> personsWithPhone = getPersonsByPhone(person.phoneNumber());
                        return personsWithPhone != null && personsWithPhone.contains(person);
                    })
                    .toList();
            } finally {
                addressLock.readLock().unlock();
            }
            return res;
        }

        @Override
        public List<Person> findByPhone(String phone) {

            List<Person> res = null;
            phoneLock.readLock().lock();
            try {
                Set<Person> personSet = phoneToPersonSet.get(phone);
                if (personSet == null) {
                    return null;
                }
                res = personSet.stream()
                    .filter(person -> {
                        Set<Person> personsWithName = getPersonsByName(person.name());
                        return personsWithName != null && personsWithName.contains(person);
                    })
                    .filter(person -> {
                        Set<Person> personsWithAddress = getPersonsByAddress(person.address());
                        return personsWithAddress != null && personsWithAddress.contains(person);
                    })
                    .toList();
            } finally {
                phoneLock.readLock().unlock();
            }
            return res;

        }
    }

}
