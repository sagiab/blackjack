package com.spalah.courses.projects.blackjack;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class EntityManagerFactoryFabric {
    private static EntityManagerFactory getEntityManagerFactory(String pUnit) {
        return Persistence.createEntityManagerFactory(pUnit);
    }
}
