import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class PreferenceMatrix
{
    private char[][] charPreferenceMatrix;
    private int[][] preferenceMatrix;
    private int[][] transposedPreferenceMatrix;

    private HashMap<Character, Integer> charIndexLookup;
    private HashMap<Integer, Character> indexCharLookup;

    private int candidateCount;
    private int voterCount;

    public PreferenceMatrix(char[][] preferenceList)
    {
        charPreferenceMatrix = preferenceList;
        candidateCount = charPreferenceMatrix.length;
        voterCount = charPreferenceMatrix[0].length;

        // We want to map all characters to a unique index
        // This makes it much easier to work with the matrix, so we assign a index to each char
        charIndexLookup = new HashMap<>();
        indexCharLookup = new HashMap<>();
        for (int i = 0; i < charPreferenceMatrix.length; i++) {
            charIndexLookup.put(charPreferenceMatrix[i][0], i);
            indexCharLookup.put(i, charPreferenceMatrix[i][0]);
        }

        // Convert the char-preference-matrix into an index-preference-matrix
        preferenceMatrix = new int[candidateCount][voterCount];
        transposedPreferenceMatrix = new int[voterCount][candidateCount];
        for (int row = 0; row < candidateCount; row++) {
            for (int col = 0; col < voterCount; col++) {
                char currentChar = charPreferenceMatrix[row][col];
                int index = charIndexLookup.get(currentChar);
                preferenceMatrix[row][col] = index;
                transposedPreferenceMatrix[col][row] = index;
            }
        }
    }

    public int[] calculateOutcome(int[] candidateScores)
    {
        // We now have the scores array in which an entry at index i specifies the score of candidate i
        // We want to return an array which contains the candidate number ordered by the score
        Integer[] candidateIndices = new Integer[candidateCount];
        for (int i = 0; i < candidateCount; i++) {
            candidateIndices[i] = i;
        }

        // Sort our indices
        CandidateComparator comparator = new CandidateComparator(candidateScores);
        Arrays.sort(candidateIndices, comparator);

        // Get rid of Integer[] because Java handles primitives fucking stupid
        return Arrays.stream(candidateIndices).mapToInt(Integer::intValue).toArray();
    }

    public int[] calculateOutcome(VotingScheme scheme) {
        // Calculate the scores according to the given VotingScheme
        int[] scores = calculateCandidateScores(scheme);
        return calculateOutcome(scores);
    }

    public int[] calculateCandidateScores(VotingScheme scheme) {
        int[] votingVector = scheme.GetVotingVector(candidateCount);
        int[] scores = new int[candidateCount];
        for (int col = 0; col < voterCount; col++) {
            for (int row = 0; row < candidateCount; row++) {
                // Each column specifies the preferences of one voter from highest to lowest preference
                // Each row in the voting vector specifies the score that the candidate gets
                // based on his position in the preference list of the voter
                scores[preferenceMatrix[row][col]] += votingVector[row];
            }
        }

        return scores;
    }

    public char[] toCharArray(int[] candidates)
    {
        char[] transformedCandidates = new char[candidates.length];
        for(int i = 0; i < candidates.length; i++)
        {
            transformedCandidates[i] = indexCharLookup.get(candidates[i]);
        }

        return transformedCandidates;
    }

    public int[] getVoterPreferences(int voterIndex)
    {
        return transposedPreferenceMatrix[voterIndex];
    }

    public int getCandidateCount() {
        return candidateCount;
    }

    public int getVoterCount() { return voterCount; }

    // Sorts a array containing candidate-indices based on their score and lexicographical names
    private class CandidateComparator implements Comparator<Integer>
    {
        private int[] candidateScores;

        public CandidateComparator(int[] candidateScores)
        {
            this.candidateScores = candidateScores;
        }

        public int compare(Integer index1, Integer index2)
        {
            int candidate1Score = candidateScores[index1];
            int candidate2Score = candidateScores[index2];

            if(candidate1Score != candidate2Score)
            {
                return candidate2Score - candidate1Score;
            }
            else
            {
                Character candidate1Char = indexCharLookup.get(index1);
                Character candidate2Char = indexCharLookup.get(index2);

                return candidate1Char.compareTo(candidate2Char);
            }
        }
    }
}
