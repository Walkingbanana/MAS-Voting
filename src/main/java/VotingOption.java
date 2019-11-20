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
    private final int voterIndex;

    public VotingOption(char[] preferenceList, int[] outcome, double happinessLevel, int voterIndex) {
        this.preferenceList = preferenceList;
        this.outcome = outcome;
        this.happinessLevel = happinessLevel;
        this.voterIndex = voterIndex;
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

    public int getVoterIndex() { return voterIndex; }

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
        builder.append("################################");
        return builder.toString();
    }
}
