package fr.niwaki_mc.mod.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    public final ForgeConfigSpec.EnumValue<DbType> dbType;
    public final ForgeConfigSpec.ConfigValue<String> host;
    public final ForgeConfigSpec.IntValue port;
    public final ForgeConfigSpec.ConfigValue<String> user;
    public final ForgeConfigSpec.ConfigValue<String> password;
    public final ForgeConfigSpec.ConfigValue<String> database;

    ServerConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Database Configuration")
                .push("database");

        dbType = builder
                .comment("Database Type")
                .defineEnum("dbType", DbType.MYSQL);

        host = builder
                .comment("Database Host")
                .define("host", "localhost");

        port = builder
                .comment("Database Port")
                .defineInRange("port", 3306, 1, 65535);

        user = builder
                .comment("Database User")
                .define("user", "root");

        password = builder
                .comment("Database Password")
                .define("password", "root");

        database = builder
                .comment("Database Name")
                .define("database", "tests");

        builder.pop();
        builder.build();
    }
}
