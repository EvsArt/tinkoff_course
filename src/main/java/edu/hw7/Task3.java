package edu.hw7;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Task3 {

    static class MapPersonDatabase implements PersonDatabase {

        private final Map<String, Set<Person>> nameToPersonSet = new HashMap<>();
        private final Map<String, Set<Person>> addressToPersonSet = new HashMap<>();
        private final Map<String, Set<Person>> phoneToPersonSet = new HashMap<>();
        private final Map<Integer, Person> idToPerson = new HashMap<>();

        private Set<Person> getPersonsByName(String name) {
            synchronized (nameToPersonSet) {
                return nameToPersonSet.get(name);
            }
        }

        private Set<Person> getPersonsByAddress(String address) {
            synchronized (addressToPersonSet) {
                return addressToPersonSet.get(address);
            }
        }

        private Set<Person> getPersonsByPhone(String phone) {
            synchronized (phoneToPersonSet) {
                return phoneToPersonSet.get(phone);
            }
        }

        private void putPersonByName(Person person) {
            synchronized (nameToPersonSet) {
                if (!nameToPersonSet.containsKey(person.name())) {
                    nameToPersonSet.put(person.name(), new HashSet<>());
                }
                nameToPersonSet.get(person.name()).add(person);
            }
        }

        private void putPersonByAddress(Person person) {
            synchronized (addressToPersonSet) {
                if (!addressToPersonSet.containsKey(person.address())) {
                    addressToPersonSet.put(person.address(), new HashSet<>());
                }
                addressToPersonSet.get(person.address()).add(person);
            }
        }

        private void putPersonByPhone(Person person) {
            synchronized (phoneToPersonSet) {
                if (!phoneToPersonSet.containsKey(person.phoneNumber())) {
                    phoneToPersonSet.put(person.phoneNumber(), new HashSet<>());
                }
                phoneToPersonSet.get(person.phoneNumber()).add(person);
            }
        }

        private void removePersonName(Person person) {
            synchronized (nameToPersonSet) {
                Set<Person> personSet = nameToPersonSet.get(person.name());
                if (personSet != null) {
                    personSet.remove(person);
                }
            }
        }

        private void removePersonAddress(Person person) {
            synchronized (addressToPersonSet) {
                Set<Person> personSet = addressToPersonSet.get(person.address());
                if (personSet != null) {
                    personSet.remove(person);
                }
            }
        }

        private void removePersonPhone(Person person) {
            synchronized (phoneToPersonSet) {
                Set<Person> personSet = phoneToPersonSet.get(person.phoneNumber());
                if (personSet != null) {
                    personSet.remove(person);
                }
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
            synchronized (nameToPersonSet) {
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
            }
            return res;
        }

        @Override
        public List<Person> findByAddress(String address) {

            List<Person> res = null;
            synchronized (addressToPersonSet) {
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
            }
            return res;
        }

        @Override
        public List<Person> findByPhone(String phone) {

            List<Person> res = null;
            synchronized (phoneToPersonSet) {
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
            }
            return res;

        }

    }

}

