package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

public class PersistenceUtil {
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            Map<String, String> jpaProperties = new HashMap<>();
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();

            String dbUrl = dotenv.get("DATABASE_URL", "jdbc:mysql://localhost:3306/frameworksTrab");
            String dbUsername = dotenv.get("DATABASE_USERNAME", "root");
            String dbPassword = dotenv.get("DATABASE_PASSWORD", "");

            jpaProperties.put("javax.persistence.jdbc.url", dbUrl);
            jpaProperties.put("javax.persistence.jdbc.user", dbUsername);
            jpaProperties.put("javax.persistence.jdbc.password", dbPassword);

            entityManagerFactory = Persistence.createEntityManagerFactory("persistenceConfig", jpaProperties);
        }

        return entityManagerFactory;
    }

    public static void close(EntityManagerFactory emf) {
        if (emf != null) {
            emf.close();
        }
    }
}
