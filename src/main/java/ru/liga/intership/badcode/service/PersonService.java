package ru.liga.intership.badcode.service;


import ru.liga.intership.badcode.domain.Person;

import java.util.List;

public class PersonService {


    /**
     * Выводит средний индекс массы тела всех лиц мужского пола старше 18 лет
     */
    public void getAdultMaleUsersAverageBMI() {
        double totalImt = 0.0;
        long countOfPerson = 0;

        PersonDbWorker works = new PersonDbWorker();
        List<Person> adultPersons = works.getPersonsByQuery("SELECT * FROM person WHERE sex = 'male' AND age > 18");
        totalImt = getTotalImt(adultPersons);
        countOfPerson = adultPersons.size();

        System.out.println("Average imt - " + totalImt / countOfPerson);
    }

    private double getTotalImt(List<Person> adultPersons) {
        double totalImt = 0.0;
        for (Person p : adultPersons) {
            totalImt += getImt(p);
        }
        return totalImt;
    }

    private double getImt(Person p) {
        double heightInMeters = p.getHeight() / 100d;
        return p.getWeight() / (Double) (heightInMeters * heightInMeters);
    }

}

