package database;

import lombok.extern.java.Log;
import org.hibernate.service.spi.ServiceException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;

/**
 * The type Connection singleton.
 */
@Log
public class ConnectionSingleton {

    private ConnectionSingleton() {
    }

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManagerFactory buildEntityManagerFactory() {
        EntityManagerFactory eMF = null;
        try {
            eMF = Persistence.createEntityManagerFactory("ztpPU");
        } catch (ServiceException ex) {
            log.severe("Problem with connection to DB: " + ex.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), "Problem with database", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
        }
        return eMF;
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
