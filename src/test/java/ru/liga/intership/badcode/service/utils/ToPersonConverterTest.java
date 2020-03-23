package ru.liga.intership.badcode.service.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.intership.badcode.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToPersonConverterTest {

    @Test
    public void emptyListConvertReturnEmptyList() {
        List<Object> emptyList = new ArrayList<>();
        Assertions.assertThat(ToPersonConverter.objectListConverter(emptyList)).isEmpty();
    }

    @Test
    public void listSized3With2UnconvertableItemsReturnListSized() {
        List<Object> list = new ArrayList<>();
        list.add(42);
        list.add(new Person());
        list.add("text");
        Assertions.assertThat(ToPersonConverter.objectListConverter(list).size()).isEqualTo(1);
    }

    @Test
    public void objectConverter() {
        Person person = new Person();
        person.setName("Nameless");
        Object o = person;
        Assertions.assertThat(
                Objects.requireNonNull(
                        ToPersonConverter.objectConverter(o)).getName()).isEqualTo("Nameless");
        Assertions.assertThat(ToPersonConverter.objectConverter(122)).isEqualTo(null);
    }
}