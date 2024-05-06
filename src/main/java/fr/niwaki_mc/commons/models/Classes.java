package fr.niwaki_mc.commons.models;
import org.jetbrains.annotations.NotNull;


public class Classes {


    public int id;
    public String name;


    public Classes() {
    }

    public Classes(@NotNull String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }
}
