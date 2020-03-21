package ru.liga.intership.badcode.service;


import ru.liga.intership.badcode.domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonService {


    /**
     * Выводит средний индекс массы тела всех лиц мужского пола старше 18 лет
     */
    public void getAdultMaleUsersAverageBMI() {
        double totalImt = 0.0;
        long countOfPerson = 0;
        try {
            List<Person> adultPersons = getAdultPersonsFromDb();
            totalImt = getTotalImt(adultPersons);
            countOfPerson = adultPersons.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Average imt - " + totalImt / countOfPerson);
    }

    private double getTotalImt(List<Person> adultPersons) {
        double totalImt = 0.0;
        for (Person p : adultPersons) {
            double heightInMeters = p.getHeight() / 100d;
            double imt = p.getWeight() / (Double) (heightInMeters * heightInMeters);
            totalImt += imt;
        }
        return totalImt;
    }

    private List<Person> getAdultPersonsFromDb() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:test", "sa", "");
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM person WHERE sex = 'male' AND age > 18");
        return personsByResultSet(rs);
    }

    private List<Person> personsByResultSet(ResultSet rs) throws SQLException {
        List<Person> adultPersons = new ArrayList<>();
        while (rs.next()) {
            Person p = new Person();
            //Retrieve by column name
            p.setId(rs.getLong("id"));
            p.setSex(rs.getString("sex"));
            p.setName(rs.getString("name"));
            p.setAge(rs.getLong("age"));
            p.setWeight(rs.getLong("weight"));
            p.setHeight(rs.getLong("height"));
            adultPersons.add(p);
        }
        return adultPersons;
    }

}
