package pic2beat.song;

/**
 * Represents the role of an instrument.
 * Mainly used for 'secondary' instruments to create custom behaviours in generators
 */
public enum InstrumentRole {

    // MAIN ROLES
    LEAD("Lead", ""),
    CHORDS("Chords", ""),
    BASS("Bass", ""),
    DRUMS("Drums", ""),

    // ADDITIONAL ROLES
    ROOTS("Fondamentales", "Double les fondamentales."),
    THIRDS("Tierces", "Double les tierces."),
    FIFTHS("Quintes", "Double les quintes."),
    SEVENTHS("Septièmes", "Doubles les septièmes.");

    private final String name, description;

    InstrumentRole(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
