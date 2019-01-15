package database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * The type Connection singleton.
 */
public class ConnectionSingleton {

    private ConnectionSingleton() {
    }

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManagerFactory buildEntityManagerFactory() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ztpPU");
        return entityManagerFactory;
    }

    /**
     * Gets entity manager factory.
     *
     * @return the entity manager factory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = buildEntityManagerFactory();
        }
        return entityManagerFactory;
    }
}
