package ru.liga.intership.badcode.interfaces;

import java.util.ArrayList;
import java.util.List;

public abstract class SQLSelectBuilder {
    protected String tableName;
    protected boolean selectAll;
    protected List<String> where;
    protected List<String> select;

    public SQLSelectBuilder(String tableName) {
        this.tableName = tableName;
        selectAll = false;
        where = new ArrayList<>();
        select = new ArrayList<>();
    }

    public abstract String build();

    public SQLSelectBuilder setSelectAll() {
        selectAll = true;
        return this;
    }

    public SQLSelectBuilder where(String condition) {
        where.add(condition);
        return this;
    }

    public SQLSelectBuilder andWhere(String condition) {
        where.add("AND " + condition);
        return this;
    }

    public SQLSelectBuilder orWhere(String condition) {
        where.add("OR " + condition);
        return this;
    }

}
