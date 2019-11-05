import java.util.HashMap;

public class PreferenceMatrix
{
    private char[][] charPreferenceMatrix;
    private int[][] intPreferenceMatrix;

    private HashMap<Character, Integer> charIndexLookup;

    public PreferenceMatrix(char[][] preferenceList)
    {
        charPreferenceMatrix = preferenceList;

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

    public int[][] GetIntPreferenceMatrix()
    {
        // Make sure that nobody alters our private array
        return intPreferenceMatrix.clone();
    }

    public int GetCandidateCount()
    {
        return charIndexLookup.size();
    }
}
