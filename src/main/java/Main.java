import java.io.*;

public class Main
{
    public static void main(String[] args)
            throws IOException // Todo I hate java's forced exception handling
    {
        // Todo Remove this line once I figure out how to pass arguments in IntelliJ
        args = new String[] {"BordaVoting", "./PreferenceFiles/Test.txt"};

        // Parse the voting scheme
        VotingScheme scheme = VotingScheme.valueOf(args[0]);

        // Read the file containing the preference matrix
        String preferenceMatrixPath = args[1];
        PreferenceMatrix preferenceMatrix = ReadPreferenceList(preferenceMatrixPath);

        Integer[] votingResult = preferenceMatrix.CalculateOutcome(scheme);

        //Todo Evaluate scheme

        //Todo return output
    }

    public static PreferenceMatrix ReadPreferenceList(String filePath)
            throws IOException // Todo I hate java's forced exception handling
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line = reader.readLine();

            // The length of the first line tells us how many candidates we have
            char[][] preferenceList = new char[line.length()][];
            preferenceList[0] = line.toCharArray();

            // Read the remaining lines
            for(int i = 1; i < preferenceList.length; i++)
            {
                preferenceList[i] = reader.readLine().toCharArray();
            }

            return new PreferenceMatrix(preferenceList);
        }
    }
}
