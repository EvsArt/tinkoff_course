package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MainTest {

    Main main = new Main();

    @Test
    @DisplayName("Test sorting by size with normal data")
    void normalSortAnimalsBySize() {

        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true)
        );

        List<Animal> res = main.sortAnimalsByHeight(list);

        int prevHeight = Integer.MIN_VALUE;
        for (Animal an : res) {
            assertThat(an.height() >= prevHeight).isTrue();
            prevHeight = an.height();
        }

    }

    @Test
    @DisplayName("Test sorting by size with bad data")
    void badSortAnimalsBySize() {

        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 0, 4, true));
        list.add(new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 0, 6, true));
        list.add(new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, Integer.MIN_VALUE, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, Integer.MAX_VALUE, 2, true));
        list.add(null);

        List<Animal> res = main.sortAnimalsByHeight(list);

        int prevHeight = Integer.MIN_VALUE;
        for (Animal an : res) {
            assertThat(an.height() >= prevHeight).isTrue();
            prevHeight = an.height();
        }

        List<Animal> nullRes = main.sortAnimalsByHeight(null);
        assertThat(nullRes).isEqualTo(null);
    }

    @Test
    @DisplayName("Test finding first K animals sorted by weight")
    void normalFindKFirstAnimalsSortedByWeightDesc() {

        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true)
        );
        int k = 3;
        int bigK = k + list.size();

        List<Animal> res = main.findKFirstAnimalsSortedByWeightDesc(list, k);
        List<Animal> resWithBigK = main.findKFirstAnimalsSortedByWeightDesc(list, bigK);

        int prevWeight = Integer.MAX_VALUE;
        for (Animal an : res) {
            assertThat(an.weight() <= prevWeight).isTrue();
            prevWeight = an.weight();
        }
        assertThat(res.size()).isLessThanOrEqualTo(k);

        assertThat(resWithBigK.size()).isLessThanOrEqualTo(bigK);

    }

    @Test
    @DisplayName("Test finding first K animals sorted by weight with bad data")
    void badFindKFirstAnimalsSortedByWeightDesc() {

        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 0, 4, true));
        list.add(new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 0, 0, true));
        list.add(new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, Integer.MAX_VALUE, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, Integer.MAX_VALUE, true));
        list.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 1, Integer.MIN_VALUE, true));
        list.add(null);
        int k = 3;

        List<Animal> res = main.findKFirstAnimalsSortedByWeightDesc(list, k);

        int prevWeight = Integer.MAX_VALUE;
        for (Animal an : res) {
            assertThat(an.weight() <= prevWeight).isTrue();
            prevWeight = an.weight();
        }
        assertThat(res.size()).isLessThanOrEqualTo(k);

        var nullRes = main.findKFirstAnimalsSortedByWeightDesc(null, 0);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test counting animals of each type")
    void countAnimalsOfEachType() {

        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true)
        );

        Map<Animal.Type, Integer> res = main.countAnimalsOfEachType(list);

        assertThat(res.get(Animal.Type.CAT)).isEqualTo(2);
        assertThat(res.get(Animal.Type.DOG)).isEqualTo(1);
        assertThat(res.get(Animal.Type.FISH)).isEqualTo(1);
        assertThat(res.get(Animal.Type.SPIDER)).isEqualTo(1);

    }

    @Test
    @DisplayName("Test counting animals of each type with bad data")
    void badCountAnimalsOfEachType() {

        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 0, 4, true));
        list.add(new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 0, 6, true));
        list.add(new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, Integer.MIN_VALUE, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, Integer.MAX_VALUE, 2, true));
        list.add(null);

        Map<Animal.Type, Integer> res = main.countAnimalsOfEachType(list);

        assertThat(res.get(Animal.Type.CAT)).isEqualTo(2);
        assertThat(res.get(Animal.Type.DOG)).isEqualTo(1);
        assertThat(res.get(Animal.Type.FISH)).isEqualTo(1);
        assertThat(res.get(Animal.Type.SPIDER)).isEqualTo(1);

        var nullRes = main.countAnimalsOfEachType(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test finding animal with longest name")
    void findAnimalWithLongestName() {

        Animal myFavoriteAnimal = new Animal("RealVolodya2004ProTasher", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true);
        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 6, true),
            myFavoriteAnimal,
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true)
        );

        Animal res = main.findAnimalWithLongestName(list);

        assertThat(res).isEqualTo(myFavoriteAnimal);

    }

    @Test
    @DisplayName("Test finding animal with longest name with bad data")
    void badFindAnimalWithLongestName() {

        Animal myFavoriteAnimal = new Animal("RealVolodya2004ProTasher", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 0, 4, true));
        list.add(new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 0, 6, true));
        list.add(myFavoriteAnimal);
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, Integer.MAX_VALUE, 2, true));
        list.add(null);

        Animal res = main.findAnimalWithLongestName(list);

        assertThat(res).isEqualTo(myFavoriteAnimal);

        var nullRes = main.findAnimalWithLongestName(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test finding predominant sex")
    void predominantSex() {

        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 1, 2, 1, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true)
        );

        Animal.Sex res = main.predominantSex(list);

        assertThat(res).isEqualTo(Animal.Sex.M);

    }

    @Test
    @DisplayName("Test finding predominant sex with bad data")
    void badPredominantSex() {

        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, 0, 4, true));
        list.add(new Animal("Sanya", Animal.Type.CAT, null, 1, 0, 6, true));
        list.add(new Animal("Volodya", Animal.Type.FISH, null, 1, Integer.MIN_VALUE, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, Integer.MAX_VALUE, 2, true));
        list.add(null);

        Animal.Sex res = main.predominantSex(list);

        assertThat(res).isEqualTo(Animal.Sex.M);

        var nullRes = main.predominantSex(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test getting map of heaviest animals of each type")
    void getMapOfHeaviestAnimals() {

        Animal sanya = new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 6, true);
        Animal sobaka = new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true);
        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            sanya,
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 1, 2, 1, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 1, 2, 2, true),
            sobaka
        );

        Map<Animal.Type, Animal> res = main.getMapOfHeaviestAnimals(list);

        assertThat(res.get(Animal.Type.CAT)).isEqualTo(sanya);
        assertThat(res.get(Animal.Type.DOG)).isEqualTo(sobaka);

    }

    @Test
    @DisplayName("Test getting map of heaviest animals of each type with bad data")
    void badGetMapOfHeaviestAnimals() {

        Animal sanya = new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 6, true);
        Animal sobaka = new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, 0, 4, true));
        list.add(sanya);
        list.add(new Animal("Volodya", Animal.Type.FISH, null, 1, Integer.MIN_VALUE, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list.add(sobaka);
        list.add(null);

        Map<Animal.Type, Animal> res = main.getMapOfHeaviestAnimals(list);

        assertThat(res.get(Animal.Type.CAT)).isEqualTo(sanya);
        assertThat(res.get(Animal.Type.DOG)).isEqualTo(sobaka);

        var nullRes = main.getMapOfHeaviestAnimals(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test getting oldest animal")
    void getOldestAnimal() {

        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 50, 2, 1, true);
        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 6, true),
            volodya,
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true)
        );

        Animal res = main.getOldestAnimal(list);

        assertThat(res).isEqualTo(volodya);

    }

    @Test
    @DisplayName("Test getting oldest animal with bad data")
    void badGetOldestAnimal() {

        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, Integer.MAX_VALUE, 2, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, Integer.MAX_VALUE - 1, 4, true));
        list.add(volodya);
        list.add(new Animal("Volodya", Animal.Type.FISH, null, Integer.MAX_VALUE - 1, 1, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 0, 1, 1, true));
        list.add(null);

        Animal res = main.getOldestAnimal(list);

        assertThat(res).isEqualTo(volodya);

        var nullRes = main.getOldestAnimal(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test getting heaviest animal below K")
    void getHeaviestAnimalBelowK() {

        int k = 200;
        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 1, k - 1, 50, true);
        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 100, k + 1, 100, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 1, true),
            volodya,
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true)
        );

        Animal res = main.getHeaviestAnimalBelowK(list, k).get();

        assertThat(res).isEqualTo(volodya);

    }

    @Test
    @DisplayName("Test getting heaviest animal below K where all animals upper K")
    void getHeaviestAnimalBelowKWithEmptyResult() {

        int k = 200;
        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 1, k + 1, 50, true);
        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 100, k + 1, 100, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, k + 1, 1, true),
            volodya,
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 1, k + 1, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, k + 1, 2, true)
        );

        Animal res = main.getHeaviestAnimalBelowK(list, k).orElse(null);

        assertThat(res).isNull();

    }

    @Test
    @DisplayName("Test getting heaviest animal below K with bad data")
    void badGetHeaviestAnimalBelowK() {

        int k = 200;
        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, Integer.MAX_VALUE - 1, k - 1, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, Integer.MAX_VALUE - 2, 4, true));
        list.add(volodya);
        list.add(new Animal("Volodya", Animal.Type.FISH, null, Integer.MAX_VALUE, k + 1, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 0, 1, 1, true));
        list.add(null);

        Animal res = main.getHeaviestAnimalBelowK(list, k).get();

        assertThat(res).isEqualTo(volodya);

        var nullRes = main.getHeaviestAnimalBelowK(null, 0);
        assertThat(nullRes.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Test counting paws")
    void countPaws() {

        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 50, 2, 1, true);
        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 6, 6, true),
            volodya,
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true)
        );

        int res = main.countPaws(list);

        assertThat(res).isEqualTo(20);

    }

    @Test
    @DisplayName("Test counting paws with bad data")
    void badCountPaws() {

        Animal volodya = new Animal("Volodya", null, Animal.Sex.F, Integer.MAX_VALUE, 2, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, Integer.MAX_VALUE - 1, 4, true));
        list.add(volodya);
        list.add(new Animal("Volodya", Animal.Type.FISH, null, Integer.MAX_VALUE - 1, 1, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 0, 1, 1, true));
        list.add(null);

        int res = main.countPaws(list);

        assertThat(res).isEqualTo(12);

        var nullRes = main.countPaws(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test getting animals where paws count is not equals age")
    void getAnimalsWherePawsCountIsNotEqualsAge() {

        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 50, 2, 1, true);
        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 4, 6, 6, true),
            volodya,
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 8, 2, 2, true),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true)
        );

        List<Animal> res = main.getAnimalsWherePawsCountIsNotEqualsAge(list);

        assertThat(res.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("Test getting animals where paws count is not equals age with bad data")
    void badGetAnimalsWherePawsCountIsNotEqualsAge() {

        Animal volodya = new Animal("Volodya", null, Animal.Sex.F, Integer.MAX_VALUE, 2, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, Integer.MAX_VALUE - 1, 4, true));
        list.add(volodya);
        list.add(new Animal("Volodya", Animal.Type.FISH, null, Integer.MAX_VALUE - 1, 1, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 0, 1, 1, true));
        list.add(null);

        List<Animal> res = main.getAnimalsWherePawsCountIsNotEqualsAge(list);

        assertThat(res.size()).isEqualTo(3);

        var nullRes = main.getAnimalsWherePawsCountIsNotEqualsAge(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test getting animals that can bite and has height more 100")
    void getAnimalsThatCanBiteAndHasHeightMore100() {

        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true); // Yes
        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 99, 4, true),    // No
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 4, 6, 6, false), // No
            volodya,
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 8, 102, 2, false), // No
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 100, 2, true) // No
        );

        List<Animal> res = main.getAnimalsThatCanBiteAndHasHeightMore100(list);

        assertThat(res.size()).isEqualTo(1);
        assertThat(res.contains(volodya)).isTrue();

    }

    @Test
    @DisplayName("Test getting animals that can bite and has height more 100 with bad data")
    void badGetAnimalsThatCanBiteAndHasHeightMore100() {

        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, Integer.MAX_VALUE - 1, 4, true));
        list.add(volodya);
        list.add(new Animal("Volodya2", Animal.Type.FISH, null, Integer.MAX_VALUE - 1, 1, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 0, 1, 1, true));
        list.add(null);

        List<Animal> res = main.getAnimalsThatCanBiteAndHasHeightMore100(list);

        assertThat(res.size()).isEqualTo(2);
        assertThat(res.contains(volodya)).isTrue();

        var nullRes = main.getAnimalsThatCanBiteAndHasHeightMore100(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test counting animals that has weight greater than height")
    void countAnimalsWhereWeightGreaterThanHeight() {

        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true);
        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 99, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 4, 6, 6, false),
            volodya,
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 8, 102, 102, false),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 100, 103, true)
        );

        int res = main.countAnimalsWhereWeightGreaterThanHeight(list);

        assertThat(res).isEqualTo(1);

    }

    @Test
    @DisplayName("Test counting animals that has weight greater than height with bad data")
    void badCountAnimalsWhereWeightGreaterThanHeight() {

        Animal volodya = new Animal("Volodya", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, Integer.MAX_VALUE - 1, 4, true));
        list.add(volodya);
        list.add(new Animal("Volodya2", Animal.Type.FISH, null, Integer.MAX_VALUE - 1, 102, Integer.MAX_VALUE, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 0, 102, 102, true));
        list.add(null);

        int res = main.countAnimalsWhereWeightGreaterThanHeight(list);

        assertThat(res).isEqualTo(1);

        var nullRes = main.countAnimalsWhereWeightGreaterThanHeight(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test getting animals with more than 2 words names")
    void getAnimalsWhereNameHasMoreThanTwoWords() {

        Animal volodya = new Animal("Volodya Plus Minus", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true);
        List<Animal> list = List.of(
            new Animal("Artur Pirozkov", Animal.Type.CAT, Animal.Sex.M, 1, 99, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 4, 6, 6, false),
            volodya,
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.F, 8, 102, 102, false),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 100, 103, true)
        );

        List<Animal> res = main.getAnimalsWhereNameHasMoreThanTwoWords(list);

        assertThat(res.size()).isEqualTo(1);
        assertThat(res.contains(volodya)).isTrue();

    }

    @Test
    @DisplayName("Test getting animals with more than 2 words names with bad data")
    void badGetAnimalsWhereNameHasMoreThanTwoWords() {

        Animal volodya = new Animal("Volodya aaa oooo", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, Integer.MAX_VALUE - 1, 4, true));
        list.add(volodya);
        list.add(new Animal("Volodya2", Animal.Type.FISH, null, Integer.MAX_VALUE - 1, 102, Integer.MAX_VALUE, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 0, 102, 102, true));
        list.add(new Animal("", Animal.Type.SPIDER, Animal.Sex.M, 0, 102, 102, true));
        list.add(new Animal(null, Animal.Type.SPIDER, Animal.Sex.M, 0, 102, 102, true));
        list.add(null);

        List<Animal> res = main.getAnimalsWhereNameHasMoreThanTwoWords(list);

        assertThat(res.size()).isEqualTo(1);
        assertThat(res.contains(volodya)).isTrue();

        var nullRes = main.getAnimalsWhereNameHasMoreThanTwoWords(null);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test checking that list has dogs with height more than K")
    void listHasDogWithHeightMoreThanK() {

        int k = 100;
        int falseK = Integer.MAX_VALUE;
        Animal volodya = new Animal("Volodya Plus Minus", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true);
        List<Animal> list = List.of(
            new Animal("Artur Pirozkov", Animal.Type.CAT, Animal.Sex.M, 1, 99, 4, true),
            new Animal("Sanya", Animal.Type.DOG, Animal.Sex.M, 4, 6, k - 1, false),
            volodya,
            new Animal("Petr", Animal.Type.DOG, Animal.Sex.F, 8, 102, k, false),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 100, k + 1, true)
        );

        boolean trueRes = main.listHasDogWithHeightMoreThanK(list, k);
        boolean falseRes = main.listHasDogWithHeightMoreThanK(list, falseK);

        assertThat(trueRes).isTrue();
        assertThat(falseRes).isFalse();

    }

    @Test
    @DisplayName("Test checking that list has dogs with height more than K")
    void badListHasDogWithHeightMoreThanK() {

        int k = 100;
        int falseK = Integer.MAX_VALUE;
        Animal volodya = new Animal("Volodya aaa oooo", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, Integer.MAX_VALUE - 1, 4, true));
        list.add(volodya);
        list.add(new Animal("Volodya2", Animal.Type.FISH, null, Integer.MAX_VALUE - 1, 102, Integer.MAX_VALUE, true));
        list.add(new Animal("Petr", Animal.Type.DOG, Animal.Sex.M, 0, 102, k - 1, true));
        list.add(new Animal("", Animal.Type.DOG, Animal.Sex.M, 0, 102, k, true));
        list.add(new Animal(null, Animal.Type.DOG, Animal.Sex.M, 0, 102, k + 1, true));
        list.add(null);

        boolean trueRes = main.listHasDogWithHeightMoreThanK(list, k);
        boolean falseRes = main.listHasDogWithHeightMoreThanK(list, falseK);

        assertThat(trueRes).isTrue();
        assertThat(falseRes).isFalse();

        var nullRes = main.listHasDogWithHeightMoreThanK(null, 0);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test getting sum of weight of animals with age from K to L")
    void getWeightSumOfAnimalsWithAgeFromKToL() {

        int startAge1 = 1;
        int endAge1 = 4;
        int startAge2 = 0;
        int endAge2 = -1;

        Animal volodya = new Animal("Volodya Plus Minus", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true);
        List<Animal> list = List.of(
            new Animal("Artur Pirozkov", Animal.Type.CAT, Animal.Sex.M, 1, 99, 4, true),
            new Animal("Sanya", Animal.Type.DOG, Animal.Sex.M, 4, 6, 1, false),
            volodya,
            new Animal("Petr", Animal.Type.DOG, Animal.Sex.F, 8, 102, 2, false),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, 100, 1, true)
        );

        int res1 = main.getWeightSumOfAnimalsWithAgeFromKToL(list, startAge1, endAge1);
        int res2 = main.getWeightSumOfAnimalsWithAgeFromKToL(list, startAge2, endAge2);

        assertThat(res1).isEqualTo(6);
        assertThat(res2).isEqualTo(0);

    }

    @Test
    @DisplayName("Test getting sum of weight of animals with age from K to L with bad data")
    void badGetWeightSumOfAnimalsWithAgeFromKToL() {

        int startAge1 = 1;
        int endAge1 = 4;
        int startAge2 = 0;
        int endAge2 = -1;
        Animal volodya = new Animal("Volodya aaa oooo", Animal.Type.FISH, Animal.Sex.F, 50, 102, 1, true);
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", Animal.Type.CAT, null, 1, Integer.MAX_VALUE - 1, 4, true));
        list.add(volodya);
        list.add(new Animal("Volodya2", Animal.Type.FISH, null, Integer.MAX_VALUE - 1, 102, Integer.MAX_VALUE, true));
        list.add(new Animal("Petr", Animal.Type.DOG, Animal.Sex.M, 0, 102, 1, true));
        list.add(new Animal("", Animal.Type.DOG, Animal.Sex.M, 0, 102, 2, true));
        list.add(new Animal(null, Animal.Type.DOG, Animal.Sex.M, 0, 102, 1, true));
        list.add(null);

        int res1 = main.getWeightSumOfAnimalsWithAgeFromKToL(list, startAge1, endAge1);
        int res2 = main.getWeightSumOfAnimalsWithAgeFromKToL(list, startAge2, endAge2);

        assertThat(res1).isEqualTo(4);
        assertThat(res2).isEqualTo(0);

        var nullRes = main.getWeightSumOfAnimalsWithAgeFromKToL(null, 0, 0);
        assertThat(nullRes).isEqualTo(null);

    }

    @Test
    @DisplayName("Test sorting by type than by sex than by name")
    void sortByTypeThanBySexThanByName() {

        List<Animal> list = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.F, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.CAT, Animal.Sex.F, 2, 2, 2, true)
        );

        List<Animal> expectedRes = List.of(
            list.get(0),
            list.get(4),
            list.get(1),
            list.get(2),
            list.get(3)
        );

        List<Animal> res = main.sortByTypeThanBySexThanByName(list);

        assertThat(res).isEqualTo(expectedRes);

    }

    @Test
    @DisplayName("Test sorting by type than by sex than by name with bad data")
    void badSortByTypeThanBySexThanByName() {

        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", null, Animal.Sex.M, 1, 0, 4, true));
        list.add(new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 0, 6, true));
        list.add(new Animal("Volodya", Animal.Type.FISH, null, 1, Integer.MIN_VALUE, 1, true));
        list.add(new Animal(null, Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, Integer.MAX_VALUE, 2, true));
        list.add(null);

        List<Animal> expectedRes = List.of(
            list.get(1),
            list.get(4)
        );

        List<Animal> res = main.sortByTypeThanBySexThanByName(list);

        assertThat(res).isEqualTo(expectedRes);

        var nullRes = main.sortByTypeThanBySexThanByName(null);
        assertThat(nullRes).isEqualTo(null);
    }

    @Test
    @DisplayName("Test checking that spiders bites more often than dogs")
    void spidersBitesMoreOftenThanDogs() {

        List<Animal> list1 = List.of(
            new Animal("Artur", Animal.Type.SPIDER, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.DOG, Animal.Sex.F, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 2, false),
            new Animal("Arsen", Animal.Type.CAT, Animal.Sex.F, 2, 2, 2, true)
        );

        List<Animal> list2 = List.of(
            new Animal("Artur", Animal.Type.SPIDER, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.FISH, Animal.Sex.F, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 2, false),
            new Animal("Arsen", Animal.Type.CAT, Animal.Sex.F, 2, 2, 2, true)
        );

        List<Animal> list3 = List.of(
            new Animal("Artur", Animal.Type.SPIDER, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.SPIDER, Animal.Sex.F, 1, 6, 6, false),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
            new Animal("Petr", Animal.Type.DOG, Animal.Sex.M, 1, 2, 2, false),
            new Animal("Arsen", Animal.Type.DOG, Animal.Sex.F, 2, 2, 2, true)
        );

        boolean res1 = main.spidersBitesMoreOftenThanDogs(list1);
        boolean res2 = main.spidersBitesMoreOftenThanDogs(list2);
        boolean res3 = main.spidersBitesMoreOftenThanDogs(list3);

        // Probability of spider's biting is 1/2
        // Probability of dog's biting is 1/1
        assertThat(res1).isFalse();

        // We don't have any dogs so don't have enough info
        assertThat(res2).isFalse();

        // Probabilities are equals
        assertThat(List.of(true, false).contains(res3)).isTrue();

    }

    @Test
    @DisplayName("Test checking that spiders bites more often than dogs with bad data")
    void badSpidersBitesMoreOftenThanDogs() {

        List<Animal> list = new ArrayList<>();
        list.add(new Animal("Artur", null, Animal.Sex.M, 1, 0, 4, true));
        list.add(new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 0, 6, true));
        list.add(new Animal("Volodya", Animal.Type.DOG, null, 1, Integer.MIN_VALUE, 1, false));
        list.add(new Animal(null, Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, Integer.MAX_VALUE, 2, true));
        list.add(null);

        boolean res1 = main.spidersBitesMoreOftenThanDogs(list);

        assertThat(res1).isTrue();

        var nullRes = main.spidersBitesMoreOftenThanDogs(null);
        assertThat(nullRes).isEqualTo(false);
    }

    @Test
    @DisplayName("Test finding the heaviest fish")
    void findHeaviestFish() {

        Animal myBigFish = new Animal("Petr", Animal.Type.FISH, Animal.Sex.M, 1, 2, 900, true);
        List<Animal> list1 = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.F, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 2, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.CAT, Animal.Sex.F, 2, 2, 2, true)
        );
        List<Animal> list2 = List.of(
            new Animal("Artur", Animal.Type.CAT, Animal.Sex.M, 1, 4, 4, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.F, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
            myBigFish,
            new Animal("Arsen", Animal.Type.CAT, Animal.Sex.F, 2, 2, 2, true)
        );
        List<Animal> list3 = List.of(
            new Animal("Artur", Animal.Type.FISH, Animal.Sex.M, 1, 4, 3, true),
            new Animal("Sanya", Animal.Type.CAT, Animal.Sex.F, 1, 6, 6, true),
            new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
            new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, 1, 2, 2, true),
            new Animal("Arsen", Animal.Type.CAT, Animal.Sex.F, 2, 2, 2, true)
        );

        Animal res = main.findHeaviestFish(list1, list2, list3);

        assertThat(res).isEqualTo(myBigFish);

    }

    @Test
    @DisplayName("Test finding the heaviest fish with bad data")
    void badFindHeaviestFish() {

        Animal myBigFish = new Animal("Petr", Animal.Type.FISH, Animal.Sex.M, 1, 2, 900, true);
        List<Animal> list1 = new ArrayList<>();
        list1.add(new Animal("Artur", null, Animal.Sex.M, 1, 0, 4, true));
        list1.add(new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 0, 6, true));
        list1.add(new Animal("Volodya", Animal.Type.FISH, null, 1, Integer.MIN_VALUE, 1, true));
        list1.add(new Animal(null, Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list1.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, Integer.MAX_VALUE, 2, true));
        list1.add(null);

        List<Animal> list2 = new ArrayList<>();
        list2.add(new Animal("Artur", null, Animal.Sex.M, 1, 0, 4, true));
        list2.add(new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 0, 6, true));
        list2.add(myBigFish);
        list2.add(new Animal(null, Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list2.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, Integer.MAX_VALUE, 2, true));
        list2.add(null);

        List<Animal> list3 = new ArrayList<>();
        list3.add(new Animal("Artur", null, Animal.Sex.M, 1, 0, 4, true));
        list3.add(new Animal("Sanya", Animal.Type.CAT, Animal.Sex.M, 1, 0, 6, true));
        list3.add(new Animal("Volodya", Animal.Type.FISH, null, 1, Integer.MIN_VALUE, 1, true));
        list3.add(new Animal(null, Animal.Type.SPIDER, Animal.Sex.M, 1, Integer.MAX_VALUE, 1, true));
        list3.add(new Animal("Arsen", Animal.Type.DOG, Animal.Sex.M, 2, Integer.MAX_VALUE, 2, true));
        list3.add(null);

        Animal res = main.findHeaviestFish(list1, list2, list3);

        assertThat(res).isEqualTo(myBigFish);

        var nullRes = main.findHeaviestFish(null);
        assertThat(nullRes).isEqualTo(null);
    }

    @Test
    @DisplayName("Test finding errors")
    void findErrors() {

        List<Animal> list = new ArrayList<>();
        list.add(new Animal("", Animal.Type.SPIDER, Animal.Sex.M, 1, 4, 4, true));
        list.add(new Animal(null, Animal.Type.DOG, Animal.Sex.F, 1, 6, 6, true));
        list.add(new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, -1, 2, 2, false));
        list.add(new Animal("Arsen", Animal.Type.CAT, Animal.Sex.F, -2, -2, -2, true));
        list.add(null);

        Map<String, Set<ValidationError>> res = main.findErrors(list);

        Map<String, Set<ValidationError>> expectedRes = new HashMap<>();
        expectedRes.put("", Set.of(ValidationError.NAMEISNULLERROR));
        expectedRes.put(null, Set.of(ValidationError.NAMEISNULLERROR));
        expectedRes.put(
            "Arsen",
            Set.of(
                ValidationError.WRONGHEIGHTERROR,
                ValidationError.WRONGAGEERROR,
                ValidationError.WRONGWEIGHTERROR
            )
        );
        expectedRes.put("Petr", Set.of(ValidationError.WRONGAGEERROR));
        expectedRes.put("Volodya", Set.of());

        for (Map.Entry<String, Set<ValidationError>> entry : res.entrySet()) {
            assertThat(entry.getValue()).isEqualTo(expectedRes.get(entry.getKey()));
        }

    }

    @Test
    @DisplayName("Test converting errors to pretty output")
    void getPrettyErrors() {

        List<Animal> list = new ArrayList<>();
        list.add(new Animal("", Animal.Type.SPIDER, Animal.Sex.M, 1, 4, 4, true));
        list.add(new Animal(null, Animal.Type.DOG, Animal.Sex.F, 1, 6, 6, true));
        list.add(new Animal("Volodya", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true));
        list.add(new Animal("Petr", Animal.Type.SPIDER, Animal.Sex.M, -1, 2, 2, false));
        list.add(new Animal("Arsen", Animal.Type.CAT, Animal.Sex.F, -2, -2, -2, true));
        list.add(null);

        Map<String, String> res = main.getPrettyErrors(main.findErrors(list));

        Map<String, String> expectedRes = new HashMap<>();
        expectedRes.put("", "Errors:\n" + ValidationError.NAMEISNULLERROR.getMessage());
        expectedRes.put(null, "Errors:\n" + ValidationError.NAMEISNULLERROR.getMessage());
        expectedRes.put(
            "Arsen",
            "Errors:\n" +
                ValidationError.WRONGHEIGHTERROR.getMessage() + "\n" +
                ValidationError.WRONGWEIGHTERROR.getMessage() + "\n" +
                ValidationError.WRONGAGEERROR.getMessage()
            );
        expectedRes.put("Petr", "Errors:\n" + ValidationError.WRONGAGEERROR.getMessage());
        expectedRes.put("Volodya", "Errors:");

        for (Map.Entry<String, String> entry : res.entrySet()) {
            assertThat(entry.getValue().length()).isEqualTo(expectedRes.get(entry.getKey()).length());
            // .length because we don't have any order
        }

    }

    @Test
    @DisplayName("Test converting errors to pretty output with null input")
    void badGetPrettyErrors() {
        assertThat(main.getPrettyErrors(null)).isNull();
    }

}
