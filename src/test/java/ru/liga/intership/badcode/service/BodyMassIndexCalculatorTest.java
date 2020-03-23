package ru.liga.intership.badcode.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.liga.intership.badcode.domain.Person;

import java.util.ArrayList;
import java.util.List;

public class BodyMassIndexCalculatorTest {
    static List<Person> people;
    BodyMassIndexCalculator calculator;

    private static Person genPerson(int weight, int height) {
        Person person = new Person();
        person.setWeight((long) weight);
        person.setHeight((long) height);
        return person;
    }

    @BeforeClass
    public static void beforeClass() {
        people = new ArrayList<>();
        people.add(genPerson(50, 150));
        people.add(genPerson(90, 190));
        people.add(genPerson(80, 180));
    }

    @Before
    public void before() {
        calculator = new BodyMassIndexCalculator();
    }

    @Test
    public void getAverageBMI() {
        Assertions.assertThat(calculator.getAverageBMI(people))
                .isCloseTo(23.95, Percentage.withPercentage(0.1));
    }

    @Test
    public void getTotalBMI() {
        Assertions.assertThat(calculator.getTotalBMI(people))
                .isCloseTo(71.9, Percentage.withPercentage(0.1));
    }

    @Test
    public void getBMI() {
        Assertions.assertThat(calculator.getBMI(people.get(0)))
                .isCloseTo(22.22, Percentage.withPercentage(0.1));

        Assertions.assertThat(calculator.getBMI(people.get(1)))
                .isCloseTo(24.94, Percentage.withPercentage(0.1));

        Assertions.assertThat(calculator.getBMI(people.get(2)))
                .isCloseTo(24.7, Percentage.withPercentage(0.1));
    }
}