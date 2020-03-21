package ru.liga.intership.badcode.interfaces;

import java.util.List;

public interface DatabaseExtractor {
    List<Object> extractByQuery(String sqlQuery);
}
