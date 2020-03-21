package ru.liga.intership.badcode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.interfaces.DatabaseExtractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDatabaseExtractor implements DatabaseExtractor {
    private Logger logger = LoggerFactory.getLogger(PersonDatabaseExtractor.class);

    private String conString;
    private String user;
    private String password;

    public PersonDatabaseExtractor() {
        conString = "jdbc:hsqldb:mem:test";
        user = "sa";
        password = "";
    }

    public PersonDatabaseExtractor(String conString, String user, String password) {
        this.conString = conString;
        this.user = user;
        this.password = password;
    }

    public List<Object> extractByQuery(String sqlQuery) {
        List<Object> people = new ArrayList<>();
        Connection conn = getConnection();
        if (conn == null) return people;

        Statement statement = getStatement(conn);
        if (statement == null) return people;

        ResultSet rs = getResultSet(sqlQuery, statement);
        people = parseResultSet(rs);
        return people;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(conString, user, password);
        } catch (SQLException sql) {
            logger.error("Error when trying connect with db: " + sql.getMessage());
        }
        return null;
    }

    private Statement getStatement(Connection conn) {
        try {
            return conn.createStatement();
        } catch (SQLException sql) {
            logger.error("Error when trying get statement: " + sql.getMessage());
        }
        return null;
    }

    private ResultSet getResultSet(String sqlQuery, Statement statement) {
        try {
            return statement.executeQuery(sqlQuery);
        } catch (SQLException sql) {
            logger.error("Error when trying perform query: " + sql.getMessage());
        }
        return null;
    }

    private List<Object> parseResultSet(ResultSet rs) {
        List<Object> people = new ArrayList<>();
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
            logger.error("Error when trying read RS: " + sql.getMessage());
        }
        return people;
    }

}