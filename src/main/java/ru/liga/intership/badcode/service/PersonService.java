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
        String tableName = "person";
        return getAdultMaleUsersAverageBMI(tableName);
    }

    /**
     * Получить средний вес взрослых мужчин
     *
     * @param tableName таблица из которой будет производиться выборка
     * @return средний вес данной группы.
     */
    public double getAdultMaleUsersAverageBMI(String tableName) {
        String query = new PersonSQLSelectBuilder(tableName).setSelectAll()
                .where("sex = 'male'").andWhere("age >= 18").build();

        List<Person> adultMaleUsers = ToPersonConverter.objectListConverter(personExtractor.extractByQuery(query));
        BodyMassIndexCalculator calculator = new BodyMassIndexCalculator();
        double averageBMI = calculator.getAverageBMI(adultMaleUsers);
        logger.debug("Average BMI - {}", averageBMI);
        return averageBMI;
    }

}

