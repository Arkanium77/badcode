package ru.liga.intership.badcode.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class PersonSQLSelectBuilderTest {

    @Test
    public void selectAllFromTablePerson() {
        String trueQuery = "SELECT * FROM person";
        String query = new PersonSQLSelectBuilder("person")
                .setSelectAll()
                .build();
        Assertions.assertThat(query).isEqualTo(trueQuery);
    }

    @Test
    public void selectIdAndNameFromTablePersonWherePersonIsAdultAndMoreThan150Height() {
        String trueQuery = "SELECT id, name FROM person WHERE age>=18 AND height>150";
        String query = new PersonSQLSelectBuilder("person")
                .selectId()
                .selectName()
                .where("age>=18")
                .andWhere("height>150")
                .build();

        Assertions.assertThat(query).isEqualTo(trueQuery);
    }

    @Test
    public void nothingToSelect() {

        try {
            new PersonSQLSelectBuilder("person").build();
            Assertions.assertThat(false).isEqualTo(true);
        } catch (RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Nothing to select!");
        }
    }

}