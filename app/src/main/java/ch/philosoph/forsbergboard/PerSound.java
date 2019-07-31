package ch.philosoph.forsbergboard;

/**
 * Contains the sound Object and the name, which should be
 * displayed in the application. We need this, because the name of
 * the sound shouldn't be the same as the name which is displayed.
 */
public class PerSound {

    private Sound sound;
    private String displayName;

    public PerSound(Sound sound, String displayName) {
        this.sound = sound;
        this.displayName = displayName;
    }

    public Sound getSound() {
        return sound;
    }

    public String getDisplayName() {
        return displayName;
    }
}
