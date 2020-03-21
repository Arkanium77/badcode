package ru.liga.intership.badcode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabasePersonExtractor {
    private Logger logger = LoggerFactory.getLogger(DatabasePersonExtractor.class);

    private String conString;
    private String user;
    private String password;

    public DatabasePersonExtractor() {
        conString = "jdbc:hsqldb:mem:test";
        user = "sa";
        password = "";
    }

    public DatabasePersonExtractor(String conString, String user, String password) {
        this.conString = conString;
        this.user = user;
        this.password = password;
    }

    public List<Person> getPersonsByQuery(String sqlQuery) {
        List<Person> people = new ArrayList<>();
        Connection conn = getConnection();
        if (conn == null) return people;

        Statement statement = getStatement(conn);
        if (statement == null) return people;

        ResultSet rs = getResultSet(sqlQuery, statement);
        people = personsByResultSet(rs);
        return people;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(conString, user, password);
        } catch (SQLException sql) {
            logger.error("Error when trying connect with db " + sql.getSQLState());
        }
        return null;
    }

    private Statement getStatement(Connection conn) {
        try {
            return conn.createStatement();
        } catch (SQLException sql) {
            logger.error("Error when trying get statement " + sql.getSQLState());
        }
        return null;
    }

    private ResultSet getResultSet(String sqlQuery, Statement statement) {
        try {
            return statement.executeQuery(sqlQuery);
        } catch (SQLException sql) {
            logger.error("Error when trying perform query " + sql.getSQLState());
        }
        return null;
    }

    private List<Person> personsByResultSet(ResultSet rs) {
        List<Person> people = new ArrayList<>();
        if (rs == null) return people;

        try {
            while (rs.next()) {
                Person p = new Person();
                //Retrieve by column name
                p.setId(rs.getLong("id"));
                p.setSex(rs.getString("sex"));
                p.setName(rs.getString("name"));
                p.setAge(rs.getLong("age"));
                p.setWeight(rs.getLong("weight"));
                p.setHeight(rs.getLong("height"));
                people.add(p);
            }
        } catch (SQLException sql) {
            logger.error("Error when trying read RS " + sql.getSQLState());
        }
        return people;
    }

}