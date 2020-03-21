package ru.liga.intership.badcode.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.service.utils.ToPersonConverter;

import java.util.List;

public class PersonService {
    Logger logger = LoggerFactory.getLogger(PersonService.class);

    /**
     * Выводит средний индекс массы тела всех лиц мужского пола старше 18 лет
     */
    public void getAdultMaleUsersAverageBMI() {
        double totalBMI;
        long countOfPerson;

        PersonDatabaseExtractor personExtractor = new PersonDatabaseExtractor();
        logger.debug("Create DatabaseExtractor for Persons");
        String query = new PersonSQLSelectBuilder("person").setSelectAll()
                .where("sex = 'male'").andWhere("age > 18").build();

        List<Person> adultPersons = ToPersonConverter.objectListConverter(personExtractor
                .extractByQuery(query));
        logger.debug("Extracted " + adultPersons.size() + " adult males");

        totalBMI = getTotalBMI(adultPersons);
        logger.debug("Total BMI : {}", totalBMI);

        countOfPerson = adultPersons.size();
        logger.info("Average BMI - {}", totalBMI / countOfPerson);
    }

    private double getTotalBMI(List<Person> people) {
        double totalBMI = 0.0;
        for (Person person : people) {
            double bmi = getBMI(person);
            logger.trace("Imt of {} is {}", person, bmi);
            totalBMI += bmi;
        }
        return totalBMI;
    }

    private double getBMI(Person person) {
        double heightInMeters = person.getHeight() / 100d;
        return person.getWeight() / (Double) (heightInMeters * heightInMeters);
    }


}

