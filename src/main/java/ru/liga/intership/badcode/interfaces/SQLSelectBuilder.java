package ru.liga.intership.badcode.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Построитель SQL запросов типа Select
 */
public abstract class SQLSelectBuilder {
    protected String tableName;
    protected boolean selectAll;
    protected List<String> where;
    protected List<String> select;

    /**
     * Конструктор
     *
     * @param tableName имя таблицы из которой будем делать выборку.
     */
    public SQLSelectBuilder(String tableName) {
        this.tableName = tableName;
        selectAll = false;
        where = new ArrayList<>();
        select = new ArrayList<>();
    }

    public abstract String build();

    /**
     * Выбрать все
     *
     * @return SQLSelectBuilder с установленным условием выборки всех полей.
     */
    public SQLSelectBuilder setSelectAll() {
        selectAll = true;
        return this;
    }

    /**
     * Добавление условий выборки (where)
     *
     * @param condition условие выборки.
     * @return SQLSelectBuilder с этим условием.
     */
    public SQLSelectBuilder where(String condition) {
        where.add(condition);
        return this;
    }

    /**
     * Добавление дополнительного условия выборки (через опреатор И).
     *
     * @param condition дополнительное условие выборки
     * @return SQLSelectBuilder с этим условием.
     */
    public SQLSelectBuilder andWhere(String condition) {
        where.add("AND " + condition);
        return this;
    }

    /**
     * Добавление дополнительного условия выборки (через опреатор ИЛИ).
     *
     * @param condition дополнительное условие выборки
     * @return SQLSelectBuilder с этим условием.
     */
    public SQLSelectBuilder orWhere(String condition) {
        where.add("OR " + condition);
        return this;
    }

}
