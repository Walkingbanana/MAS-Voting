import java.util.HashSet;
import java.util.Set;

public class VotingSituationAnalyzer
{
    private PreferenceMatrix preferenceMatrix;
    private int[] votingVector;
    private int[] initialCandidateScores;
    private int[] initialOutcome;

    public VotingSituationAnalyzer(PreferenceMatrix preferenceMatrix, VotingScheme scheme)
    {
        this.preferenceMatrix = preferenceMatrix;
        this.votingVector = scheme.GetVotingVector(preferenceMatrix.getCandidateCount());
        this.initialCandidateScores = preferenceMatrix.calculateCandidateScores(scheme);
        this.initialOutcome = preferenceMatrix.calculateOutcome(initialCandidateScores);
    }

    public Set<VotingOption> analyzeVoter(int voterIndex)
    {
        int[] truePreferences = preferenceMatrix.getVoterPreferences(voterIndex);
        int[] candidateScores = new int[initialCandidateScores.length];

        // We subtract the true preferences from the candidate scores
        // This way we know the candidate scores, as if the target voter does not exist
        for(int i = 0; i < candidateScores.length; i++)
        {
            candidateScores[i] = initialCandidateScores[i] - votingVector[truePreferences[i]];
        }

        double initialHappiness = MASHappinessMetric.calculateHappiness(truePreferences, initialOutcome);
        Set<VotingOption> votingOptions = new HashSet<>();
        generateVotingOptions(votingOptions, new boolean[candidateScores.length], new int[candidateScores.length], initialHappiness, 0, truePreferences, candidateScores);

        return votingOptions;
    }

    private void generateVotingOptions(Set<VotingOption> bucket,
                                      boolean[] usedVoting,
                                      int[] fakePreferences,
                                      double initialHappiness,
                                      int candidateIndex,
                                      int[] truePreferences,
                                      int[] candidateScores)
    {
        // We've set the preferences for all candidates, calculate the outcome and check if it is better than the initial situation
        if(candidateIndex == candidateScores.length)
        {
            int[] newOutcome = preferenceMatrix.calculateOutcome(candidateScores);
            double newHappiness = MASHappinessMetric.calculateHappiness(truePreferences, newOutcome);
            if(newHappiness > initialHappiness)
            {
                char[] transformedOutcome = preferenceMatrix.toCharArray(fakePreferences);
                bucket.add(new VotingOption(transformedOutcome, newOutcome, newHappiness, ""));
            }

            return;
        }

        // We pretend there is a new voter and analyze all possible voting options for the current candidate
        for(int i = 0; i < candidateScores.length; i++)
        {
            if(usedVoting[i])
                continue;

            fakePreferences[candidateIndex] = i;
            candidateScores[candidateIndex] += votingVector[i];
            usedVoting[i] = true;

            generateVotingOptions(bucket, usedVoting, fakePreferences, initialHappiness, candidateIndex + 1, truePreferences, candidateScores);

            candidateScores[candidateIndex] -= votingVector[i];
            usedVoting[i] = false;
        }
    }
}
