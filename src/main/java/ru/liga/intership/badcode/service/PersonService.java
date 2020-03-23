package ru.liga.intership.badcode.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.service.utils.ToPersonConverter;

import java.util.List;

/**
 * Класс <b>PersonService</b> предназначен для работы с обработки объектов класса Person,
 * извлекаемых из SQL-DB
 */
public class PersonService {
    Logger logger = LoggerFactory.getLogger(PersonService.class);

    /**
     * Получить средний вес взрослых мужчин
     *
     * @return средний вес данной группы.
     */
    public double getAdultMaleUsersAverageBMI() {
        String conString = "jdbc:hsqldb:mem:test";
        String user = "sa";
        String password = "";
        String tableName = "persons";
        return getAdultMaleUsersAverageBMI(conString, user, password, tableName);
    }

    /**
     * Получить средний вес взрослых мужчин
     *
     * @param conString строка для подключения к бд.
     * @param user      имя пользователя бд.
     * @param password  пароль пользователя бд.
     * @param tableName таблица из которой будет производиться выборка
     * @return средний вес данной группы.
     */
    public double getAdultMaleUsersAverageBMI(String conString, String user, String password, String tableName) {
        double totalBMI;
        long countOfPerson;

        PersonDatabaseExtractor personExtractor = new PersonDatabaseExtractor(conString, user, password);
        logger.debug("Create DatabaseExtractor for Persons");
        String query = new PersonSQLSelectBuilder(tableName).setSelectAll()
                .where("sex = 'male'").andWhere("age > 18").build();

        List<Person> adultMaleUsers = ToPersonConverter.objectListConverter(personExtractor
                .extractByQuery(query));
        logger.debug("Extracted " + adultMaleUsers.size() + " adult males");

        totalBMI = getTotalBMI(adultMaleUsers);
        logger.debug("Total BMI : {}", totalBMI);

        countOfPerson = adultMaleUsers.size();
        double averageBMI = totalBMI / countOfPerson;
        logger.info("Average BMI - {}", averageBMI);
        return averageBMI;
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

