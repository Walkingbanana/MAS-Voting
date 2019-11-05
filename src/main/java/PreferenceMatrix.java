import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class PreferenceMatrix
{
    private char[][] charPreferenceMatrix;
    private int[][] intPreferenceMatrix;

    private HashMap<Character, Integer> charIndexLookup;

    private int candidateCount;

    public PreferenceMatrix(char[][] preferenceList)
    {
        charPreferenceMatrix = preferenceList;
        candidateCount = charPreferenceMatrix.length;

        // We want to map all characters to a unique index
        // This makes it much easier to work with the matrix
        charIndexLookup = new HashMap<>();
        for(int i = 0; i < charPreferenceMatrix.length; i++)
        {
            charIndexLookup.put(charPreferenceMatrix[i][0], i);
        }

        // Convert the char-preference-matrix into an index-preference-matrix
        intPreferenceMatrix = new int[charPreferenceMatrix.length][charPreferenceMatrix.length];
        for(int row = 0; row < intPreferenceMatrix.length; row++)
        {
            for(int col = 0; col < intPreferenceMatrix.length; col++)
            {
                char currentChar = charPreferenceMatrix[row][col];
                intPreferenceMatrix[row][col] = charIndexLookup.get(currentChar);
            }
        }
    }

    // ToDo Get rid of Integer[] because Java handles primitives fucking stupid
    // ToDo Handle lexicographic order for candidates with the same score
    public Integer[] CalculateOutcome(VotingScheme scheme)
    {
        int[] scores = CalculateCandidateScores(scheme);

        // We now have the scores array in which an entry at index i specifies the score of candidate i
        // We want to return an array which contains the candidate number ordered by the score
        Integer[] candidateIndices = new Integer[candidateCount];
        for(int i = 0; i < candidateCount; i++)
        {
            candidateIndices[i] = i;
        }

        // Sort our indices based on the scores
        Arrays.sort(candidateIndices, Comparator.comparingInt(x -> -scores[x]));
        return candidateIndices;
    }

    public int[] CalculateCandidateScores(VotingScheme scheme)
    {
        int[] votingVector = scheme.GetVotingVector(candidateCount);
        int[] scores = new int[candidateCount];
        for(int col = 0; col < candidateCount; col++)
        {
            for(int row = 0; row < candidateCount; row++)
            {
                // Each column specifies the preferences of one voter from highest to lowest preference
                // Each row in the voting vector specifies the score that the candidate gets
                // based on his position in the preference list of the voter
                scores[intPreferenceMatrix[row][col]] += votingVector[row];
            }
        }

        return scores;
    }

    public int[][] GetIntPreferenceMatrix()
    {
        // Make sure that nobody alters our private array
        return intPreferenceMatrix.clone();
    }

    public int GetCandidateCount()
    {
        return candidateCount;
    }
}
