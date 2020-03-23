package ru.liga.intership.badcode.service;

import ru.liga.intership.badcode.interfaces.SQLSelectBuilder;

import java.util.List;

public class PersonSQLSelectBuilder extends SQLSelectBuilder {

    public PersonSQLSelectBuilder(String tableName) {
        super(tableName);
    }

    /**
     * Построение условия по списку
     *
     * @param base список условий
     * @return строка, содержащая все условия списка через ", "
     */
    private static String getConditionsByBase(List<String> base) {
        StringBuilder condition = new StringBuilder();
        base.forEach(s -> condition.append(s).append(", "));
        return condition.substring(0, condition.length() - 2);
    }

    /**
     * <b>Метод, собирающий итоговый запрос</b>
     *
     * @return строку, содержащую sql запрос, собранный по заданным данным.
     */
    @Override
    public String build() {
        StringBuilder query = new StringBuilder("SELECT ");
        if (select.size() == 0 && !selectAll) throw new RuntimeException("Nothing to select!");
        query.append(SelectCondition());
        query.append(" FROM ").append(tableName);
        if (where.size() != 0) {
            query.append(" ").append(WhereCondition());
        }
        return query.toString();
    }

    private String WhereCondition() {
        return "WHERE " + getConditionsByBase(where).replace(",", "");
    }

    private String SelectCondition() {
        if (selectAll) {
            return "*";
        }
        return getConditionsByBase(select);
    }


    public PersonSQLSelectBuilder selectId() {
        select.add("id");
        return this;
    }

    public PersonSQLSelectBuilder selectSex() {
        select.add("sex");
        return this;
    }

    public PersonSQLSelectBuilder selectName() {
        select.add("name");
        return this;
    }

    public PersonSQLSelectBuilder selectAge() {
        select.add("age");
        return this;
    }

    public PersonSQLSelectBuilder selectWeight() {
        select.add("weight");
        return this;
    }

    public PersonSQLSelectBuilder selectHeight() {
        select.add("height");
        return this;
    }
}
