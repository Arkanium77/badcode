package ru.liga.intership.badcode.service;

import ru.liga.intership.badcode.interfaces.SQLSelectBuilder;

import java.util.List;

public class PersonSQLSelectBuilder extends SQLSelectBuilder {
//    private Long id;
//    private String sex;
//    private String name;
//    private Long age;
//    private Long weight;
//    private Long height;

    public PersonSQLSelectBuilder(String tableName) {
        super(tableName);
    }

    private static String getConditionsByBase(List<String> base) {
        StringBuilder condition = new StringBuilder();
        base.forEach(s -> condition.append(s).append(", "));
        return condition.substring(0, condition.length() - 2);
    }

    @Override
    public String build() {
        StringBuilder query = new StringBuilder("SELECT ");
        if (select.size() == 0 && !selectAll) throw new RuntimeException("Nothing to select!");
        query.append(SelectCondition());
        query.append("FROM ").append(tableName).append(" ");
        if (where.size() != 0) {
            query.append(WhereCondition());
        }
        return query.toString();
    }

    private String WhereCondition() {
        return "WHERE " + getConditionsByBase(where).replace(",", "");
    }

    private String SelectCondition() {
        if (selectAll) {
            return "* ";
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
