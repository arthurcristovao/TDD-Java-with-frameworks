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
            boolean isRunningTests = Boolean.parseBoolean(System.getProperty("runningTests", "false"));

            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();

            String dbUsername = dotenv.get("DATABASE_USERNAME", "root");
            String dbPassword = dotenv.get("DATABASE_PASSWORD", "");
            String dbUrl = dotenv.get("DATABASE_URL", "jdbc:mysql://localhost:3306/frameworksTrab");
            String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);

            /*
             * Here we re-create the database url, renaming the database if we are in a test
             * lifecycle and telling hibernate to create the database if it doesn't exist
             */

            dbUrl = dbUrl.substring(0, dbUrl.lastIndexOf("/") + 1)
                    .concat(!isRunningTests ? dbName : dbName.concat("_tests_db"))
                    .concat("?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8");

            Map<String, String> jpaProperties = new HashMap<>();
            jpaProperties.put("javax.persistence.jdbc.url", dbUrl);
            jpaProperties.put("javax.persistence.jdbc.user", dbUsername);
            jpaProperties.put("javax.persistence.jdbc.password", dbPassword);

            // If we are in a test lifecycle, we re-create the test database's schema.
            jpaProperties.put("hibernate.hbm2ddl.auto", !isRunningTests ? "update" : "create");

            entityManagerFactory = Persistence.createEntityManagerFactory("persistenceConfig", jpaProperties);
        }

        return entityManagerFactory;
    }
}
