package ru.liga.intership.badcode.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс предназначен для конвертации объектов класса Object в объекты класса Person.
 */
public class ToPersonConverter {
    private static Logger logger = LoggerFactory.getLogger(ToPersonConverter.class);

    /**
     * <b>Преобразовать список объектов</b>
     *
     * @param objectList список объектов класса Object
     * @return список всех объектов, которые удалось преобразовать в Person
     */
    public static List<Person> objectListConverter(List<Object> objectList) {
        return objectList.stream()
                .map(ToPersonConverter::objectConverter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * <b>Преобразовать Object в Person</b>
     *
     * @param o объект класса Object
     * @return преобразованный в Person объект или null, в случае невозможности преобразования.
     */
    public static Person objectConverter(Object o) {
        try {
            return (Person) o;
        } catch (Throwable throwable) {
            logger.debug("unconvertable object {}", o);
            return null;
        }
    }
}
