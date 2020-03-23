package ru.liga.intership.badcode.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.interfaces.DatabaseExtractor;
import ru.liga.intership.badcode.service.utils.ToPersonConverter;

import java.util.List;

/**
 * Класс <b>PersonService</b> предназначен для работы с обработки объектов класса Person,
 * извлекаемых из SQL-DB
 */
public class PersonService {
    Logger logger = LoggerFactory.getLogger(PersonService.class);
    DatabaseExtractor personExtractor;

    public PersonService() {
        String conString = "jdbc:hsqldb:mem:test";
        String user = "sa";
        String password = "";
        personExtractor = new PersonDatabaseExtractor(conString, user, password);

    }

    public PersonService(String conString, String user, String password) {
        personExtractor = new PersonDatabaseExtractor(conString, user, password);
    }

    /**
     * Получить средний вес взрослых мужчин
     *
     * @return средний вес данной группы.
     */
    public double getAdultMaleUsersAverageBMI() {
        String tableName = "persons";
        return getAdultMaleUsersAverageBMI(tableName);
    }

    /**
     * Получить средний вес взрослых мужчин
     *
     * @param tableName таблица из которой будет производиться выборка
     * @return средний вес данной группы.
     */
    public double getAdultMaleUsersAverageBMI(String tableName) {
        double totalBMI;
        long countOfPerson;

        String query = new PersonSQLSelectBuilder(tableName).setSelectAll()
                .where("sex = 'male'").andWhere("age > 18").build();

        List<Person> adultMaleUsers = ToPersonConverter.objectListConverter(personExtractor.extractByQuery(query));
        logger.debug("Extracted " + adultMaleUsers.size() + " adult males");

        totalBMI = getTotalBMI(adultMaleUsers);
        logger.debug("Total BMI : {}", totalBMI);

        countOfPerson = adultMaleUsers.size();
        double averageBMI = totalBMI / countOfPerson;
        logger.info("Average BMI - {}", averageBMI);
        return averageBMI;
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

    double getBMI(Person person) {
        double heightInMeters = person.getHeight() / 100d;
        return person.getWeight() / (Double) (heightInMeters * heightInMeters);
    }

}

