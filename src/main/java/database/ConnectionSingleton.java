package database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionSingleton {
    private static EntityManagerFactory entityManagerFactory;

    private static EntityManagerFactory buildEntityManagerFactory() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ztpPU");
        return entityManagerFactory;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = buildEntityManagerFactory();
        }
        return entityManagerFactory;
    }
}
