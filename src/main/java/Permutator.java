import java.util.HashSet;
import java.util.Set;

public class Permutator {

    public static Set<VotingOption> permutate(int numberOfOptions, int voterIndex, final PreferenceMatrix preferences, VotingScheme scheme) {


        Set<VotingOption> votingOptions = new HashSet<>();
        int[] initialOutcome = preferences.calculateOutcome(scheme);
        double initialHappiness = MASHappinessMetric.calculateHappiness(preferences, initialOutcome);


        char[] elements = preferences.getPreferenceListOfVoter(voterIndex);
        int[] indexes = new int[numberOfOptions];
        for (int i = 0; i < numberOfOptions; i++) {
            indexes[i] = 0;
        }

        preferences.setCharPreferenceListOfVoter(voterIndex, elements);
        VotingOption votingOption = calculateVotingOption(voterIndex, preferences, scheme);
        if (votingOption.getHappinessLevel() > initialHappiness) {
            votingOptions.add(votingOption);
        }


        int i = 0;
        while (i < numberOfOptions) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ? 0 : indexes[i], i);

                preferences.setCharPreferenceListOfVoter(voterIndex, elements);
                votingOption = calculateVotingOption(voterIndex, preferences, scheme);
                if (votingOption.getHappinessLevel() > initialHappiness) {
                    votingOptions.add(votingOption);
                }

                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
        }

        return votingOptions;
    }

    static void swap(char[] input, int a, int b) {
        char tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    static VotingOption calculateVotingOption(int voterIndex, PreferenceMatrix preferenceMatrix, VotingScheme scheme) {
        char[] v = preferenceMatrix.getPreferenceListOfVoter(voterIndex);
        int[] outcome = preferenceMatrix.calculateOutcome(scheme);
        double happiness = MASHappinessMetric.calculateHappiness(preferenceMatrix, outcome);
        //Todo do something about the reason
        return new VotingOption(v, outcome, happiness, "This is a reason!");
    }
}
