package edu.hw4;

import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    // Task 1
    public List<Animal> sortAnimalsByHeight(@Nullable List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingInt(Animal::height))
            .collect(Collectors.toList());
    }

    // Task 2
    public List<Animal> findKFirstAnimalsSortedByWeightDesc(@Nullable List<Animal> list, int k) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .collect(Collectors.toList());
    }

    // Task 3
    public Map<Animal.Type, Integer> countAnimalsOfEachType(@Nullable List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .collect(
                Collectors.groupingBy(
                    Animal::type,
                    Collectors.summingInt(elem -> 1)        // bc it should return int
                )
            );
    }

    //     Task 4
    public Animal findAnimalWithLongestName(@Nullable List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .max(
                Comparator.comparingInt(
                    it -> it.name().length()
                ))
            .get();
    }

    // Task 5
    public Animal.Sex predominantSex(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }

        return list
            .stream()
            .filter(Objects::nonNull)
            .filter(it -> it.sex() != null)
            .collect(
                Collectors.groupingBy(
                    Animal::sex, Collectors.counting()
                )
            )
            .entrySet()
            .stream()
            .max(
                Comparator.comparingLong(
                    Map.Entry::getValue
                )
            )
            .get()
            .getKey();
    }

    // Task 6
    public Map<Animal.Type, Animal> getMapOfHeaviestAnimals(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .collect(
                Collectors.toMap(
                    Animal::type,
                    it -> it,
                    (a, b) -> Comparator.comparingInt(Animal::weight).compare(a, b) > 0 ? a : b
                ));
    }

    // Task 7
    public Animal getOldestAnimal(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .max(
                Comparator.comparingInt(Animal::age)
            )
            .get();
    }

    // Task 8
    public Optional<Animal> getHeaviestAnimalBelowK(List<Animal> list, int k) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return Optional.empty();
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .filter(it -> it.height() < k)
            .max(
                Comparator.comparingInt(Animal::weight)
            );
    }

    // Task 9
    public Integer countPaws(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .filter(it -> it.type() != null)
            .map(Animal::paws)
            .reduce(Integer::sum)
            .get();
    }

    // Task 10
    public List<Animal> getAnimalsWherePawsCountIsNotEqualsAge(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .filter(it -> it.type() != null)
            .filter(it -> it.paws() != it.age())
            .collect(Collectors.toList());
    }

    // Task 11
    public List<Animal> getAnimalsThatCanBiteAndHasHeightMore100(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .filter(Animal::bites)
            .filter(it -> it.height() > 100)
            .collect(Collectors.toList());
    }

    // Task 12
    public Integer countAnimalsWhereWeightGreaterThanHeight(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return (int) list
            .stream()
            .filter(Objects::nonNull)
            .filter(it -> it.weight() > it.height())
            .count();
    }

    // Task 13
    public List<Animal> getAnimalsWhereNameHasMoreThanTwoWords(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .filter(it -> it.name() != null)
            .filter(
                it -> it.name().split(" ").length > 2
            )
            .collect(Collectors.toList());
    }

    // Task 14
    public Boolean listHasDogWithHeightMoreThanK(List<Animal> list, int k) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .anyMatch(it -> it.height() > k);
    }

    // Task 15
    public Integer getWeightSumOfAnimalsWithAgeFromKToL(List<Animal> list, int k, int l) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .filter(it -> (k <= it.age() && it.age() <= l))
            .map(Animal::weight)
            .reduce(Integer::sum)
            .orElse(0);
    }

    // Task 16
    public List<Animal> sortByTypeThanBySexThanByName(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .filter(it -> it.type() != null)
            .filter(it -> it.sex() != null)
            .filter(it -> it.name() != null)
            .sorted(
                Comparator
                    .comparing(Animal::type)
                    .thenComparing(Animal::sex)
                    .thenComparing(Animal::name)
            )
            .collect(Collectors.toList());
    }

    // Task 17
    public Boolean spidersBitesMoreOftenThanDogs(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return false;
        }
        if (list.stream().noneMatch(it -> it.type() != null && it.type().equals(Animal.Type.DOG))
            || list.stream().noneMatch(it -> it.type() != null && it.type().equals(Animal.Type.SPIDER))) {
            return false;
        }   // There isn't enough information

        return list
            .stream()
            .filter(Objects::nonNull)
            .filter(it -> it.type() != null
                && (it.type().equals(Animal.Type.DOG) || it.type().equals(Animal.Type.SPIDER)))
            .collect(
                Collectors.toMap(
                    Animal::type,
                    it -> List.of(1, (it.bites() ? 1 : 0)),  // list of counter and counter of biting animals
                    (a, b) -> List.of(a.get(0) + b.get(0), a.get(1) + b.get(1))     // summ of counters
                )
            )
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                it -> (double) it.getValue().get(1) / it.getValue().get(0)    // getting probability of biting
            ))
            .entrySet()
            .stream()
            .max(Comparator.comparingDouble(Map.Entry::getValue))       // get type with max probability of biting
            .get()
            .getKey()
            .equals(Animal.Type.SPIDER);
    }

    // Task 18
    @SafeVarargs
    public final Animal findHeaviestFish(List<Animal>... arg) {
        if (arg == null
            || Stream.of(arg).allMatch(it -> it == null || it.isEmpty())) {
            return null;
        }
        return Stream.of(arg)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .filter(Objects::nonNull)
            .filter(
                it -> it.type() != null
                && it.type().equals(Animal.Type.FISH)
            )
            .max(Comparator.comparingInt(Animal::weight))
            .orElse(null);
    }

    // Task 19
    public Map<String, Set<ValidationError>> findErrors(List<Animal> list) {
        if (list == null || list.isEmpty() || list.stream().allMatch(Objects::isNull)) {
            return null;
        }
        return list
            .stream()
            .filter(Objects::nonNull)
            .collect(
                Collectors.toMap(
                    Animal::name,
                    it -> ValidationError
                        .getErrors(it)
                        .collect(Collectors.toSet())
                )
            );
    }

    // Task 20
    public Map<String, String> getPrettyErrors(Map<String, Set<ValidationError>> errors) {
        if (errors == null || errors.isEmpty()) {
            return null;
        }
        return errors.entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    it -> it.getValue()
                        .stream()
                        .map(ValidationError::getMessage)
                        .reduce("Errors:", ((a, b) -> a + "\n" + b))
                )
            );

    }

}
