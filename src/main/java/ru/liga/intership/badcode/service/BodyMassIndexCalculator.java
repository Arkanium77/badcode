package ru.liga.intership.badcode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;

import java.util.List;

public class BodyMassIndexCalculator {
    Logger logger = LoggerFactory.getLogger(BodyMassIndexCalculator.class);

    /**
     * <b>Вычисление среднего индекса массы тела выборки</b>
     *
     * @param people список людей.
     * @return средний индекс массы тела группы.
     */
    public double getAverageBMI(List<Person> people) {
        double totalBMI;
        long countOfPerson;
        totalBMI = getTotalBMI(people);
        logger.debug("Total BMI : {}", totalBMI);

        countOfPerson = people.size();
        logger.debug("Total people: {}", people.size());
        return totalBMI / countOfPerson;
    }

    double getTotalBMI(List<Person> people) {
        double totalBMI = 0.0;
        for (Person person : people) {
            double bmi = getBMI(person);
            logger.trace("Imt of {} is {}", person, bmi);
            totalBMI += bmi;
        }
        return totalBMI;
    }

    /**
     * <b>Вычисление индекса массы тела</b>
     *
     * @param person Person, для которого высчитывается индекс.
     * @return число, являющееся индексом массы тела этого человека.
     */
    public double getBMI(Person person) {
        double heightInMeters = person.getHeight() / 100d;
        return person.getWeight() / (heightInMeters * heightInMeters);
    }

}
