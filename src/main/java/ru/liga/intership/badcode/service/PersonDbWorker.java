package ru.liga.intership.badcode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDbWorker {
    private Logger logger = LoggerFactory.getLogger(PersonDbWorker.class);

    private String conString;
    private String user;
    private String password;

    public PersonDbWorker() {
        conString = "jdbc:hsqldb:mem:test";
        user = "sa";
        password = "";
    }

    public PersonDbWorker(String conString, String user, String password) {
        this.conString = conString;
        this.user = user;
        this.password = password;
    }

    public List<Person> getPersonsByQuery(String sqlQuery) {
        Connection conn = getConnection().get();
        Statement statement = getStatement(conn).get();
        ResultSet rs = getResultSet(sqlQuery, statement);
        return personsByResultSet(rs);
    }

    private ResultSet getResultSet(String sqlQuery, Statement statement) {
        try {
            return statement.executeQuery(sqlQuery);
        } catch (SQLException sql) {
            logger.error("Error when trying perform query " + sql.getSQLState());
        }
        return null;
    }

    private Optional<Statement> getStatement(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            return Optional.ofNullable(statement);
        } catch (SQLException sql) {
            logger.error("Error when trying get statement " + sql.getSQLState());
        }
        return Optional.empty();
    }

    private Optional<Connection> getConnection() {
        try {
            Connection conn = DriverManager.getConnection(conString, user, password);
            return Optional.ofNullable(conn);
        } catch (SQLException sql) {
            logger.error("Error when trying connect with db " + sql.getSQLState());
        }
        return Optional.empty();
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