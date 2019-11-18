import java.util.Arrays;

/**
 * POJO for output
 */
public class VotingOption {

    /**
     * tactically modified Preference List
     */
    private final char[] preferenceList;
    private final int[] outcome;
    private final double happinessLevel;

    /**
     * Why just why is it better...
     * No clue what to put here
     */
    private final String reason;

    public VotingOption(char[] preferenceList, int[] outcome, double happinessLevel, String reason) {
        this.preferenceList = preferenceList;
        this.outcome = outcome;
        this.happinessLevel = happinessLevel;
        this.reason = reason;
    }

    public char[] getPreferenceList() {
        return preferenceList;
    }

    public int[] getOutcome() {
        return outcome;
    }

    public double getHappinessLevel() {
        return happinessLevel;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("################################");
        builder.append(System.lineSeparator());
        builder.append("Preference list: ");
        builder.append(Arrays.toString(preferenceList));
        builder.append(System.lineSeparator());
        builder.append("Outcome: ");
        builder.append(Arrays.toString(outcome));
        builder.append(System.lineSeparator());
        builder.append("Happiness: ");
        builder.append(happinessLevel);
        builder.append(System.lineSeparator());
        builder.append("Reason: ");
        builder.append(reason);
        builder.append(System.lineSeparator());
        builder.append("################################");
        return builder.toString();
    }
}
