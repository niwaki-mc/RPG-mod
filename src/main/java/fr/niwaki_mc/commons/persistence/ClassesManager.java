package fr.niwaki_mc.commons.persistence;

import fr.niwaki_mc.commons.models.Classes;
import fr.niwaki_mc.mod.NiwakiMod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClassesManager {
    private ExecutorService executorService = null;

    // Singleton instance
    private static final ClassesManager instance = new ClassesManager();

    // Private constructor for Singleton
    private ClassesManager() {}

    // Method to get the singleton instance of ClassesManager
    public static ClassesManager getInstance() {
        return instance;
    }

    public void start() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertDefaultClasses() {
        if (executorService.isShutdown()) {
            throw new IllegalStateException("Executor service is not running. Call start() method to start it.");
        }
        executorService.submit(() -> {
            String[] defaultClassNames = {"Mage", "Guerrier", "Clerc", "Gardien"};
            Connection connection = null;
            try {
                connection = MySqlHelper.getConnection();
                connection.setAutoCommit(false);
                for (String className : defaultClassNames) {
                    if (!doesClassExists(className, connection)) {
                        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO Classes (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
                            ps.setString(1, className);
                            ps.executeUpdate();
                        }
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                NiwakiMod.LOGGER.error("Transaction failed. Performing rollback", e);
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException e2) {
                        NiwakiMod.LOGGER.error("Failed to perform rollback", e2);
                    }
                }
            } finally {
                if (connection != null) {
                    try {
                        connection.setAutoCommit(true);
                        connection.close();
                    } catch (SQLException e2) {
                        NiwakiMod.LOGGER.error(e2.getMessage(), e2);
                    }
                }
            }
        });
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public boolean doesClassExists(String className, Connection connection) {
        boolean exists = false;
        try (PreparedStatement ps = connection.prepareStatement("SELECT id FROM Classes WHERE name = ?")) {
            ps.setString(1, className);
            try (ResultSet rs = ps.executeQuery()) {
                exists = rs.next();
            }
        } catch (SQLException e) {
            NiwakiMod.LOGGER.error(e.getMessage(), e);
        }
        return exists;
    }

    public List<Classes> getAllClasses() {
        List<Classes> resultList = new ArrayList<>();
        try (Connection connection = MySqlHelper.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM Classes");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Classes classes = new Classes();
                classes.setId(rs.getInt("id"));
                classes.setName(rs.getString("name"));
                resultList.add(classes);
            }
        } catch (SQLException e) {
            NiwakiMod.LOGGER.error(e.getMessage(), e);
        }
        return resultList;
    }
}
