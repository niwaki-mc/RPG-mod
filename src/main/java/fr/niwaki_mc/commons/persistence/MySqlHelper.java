package fr.niwaki_mc.commons.persistence;

import fr.niwaki_mc.mod.NiwakiMod;
import fr.niwaki_mc.mod.configs.ConfigHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlHelper {

    private static Connection connection;

    private MySqlHelper() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            NiwakiMod.LOGGER.error(e.getMessage(), e);
        }
        if(connection == null || connection.isClosed()) {
            String url = String.format("jdbc:%s://%s:%d/%s",
                    ConfigHandler.SERVER.dbType.get().name().toLowerCase(),
                    ConfigHandler.SERVER.host.get(),
                    ConfigHandler.SERVER.port.get(),
                    ConfigHandler.SERVER.database.get());

            connection = DriverManager.getConnection(
                    url,
                    ConfigHandler.SERVER.user.get(),
                    ConfigHandler.SERVER.password.get()
            );
        }
        return connection;
    }
}
