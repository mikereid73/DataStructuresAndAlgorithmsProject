package itcarlow.c00112726.vote.entity;

/**
 * Created by Mike on 20/02/2015.
 */
public class Party {

    private String name;

    public Party(String name) {
        this.name = name.trim();
    }

    @Override
    public String toString() {
        return name;
    }
}
