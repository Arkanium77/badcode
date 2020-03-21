package ru.liga.intership.badcode.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ToPersonConverter {
    private static Logger logger = LoggerFactory.getLogger(ToPersonConverter.class);

    public static List<Person> objectListConverter(List<Object> objectList) {
        return objectList.stream()
                .map(ToPersonConverter::objectConverter)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static Person objectConverter(Object o) {
        try {
            return (Person) o;
        } catch (Throwable throwable) {
            logger.debug("unconvertable object {}", o);
            return null;
        }
    }
}
