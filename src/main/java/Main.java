import java.io.*;
import java.util.Iterator;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        //args = new String[] {"BordaVoting", "./PreferenceFiles/Test.txt"};


        //Todo check input to make it robust
        // Parse the voting scheme
        VotingScheme scheme = VotingScheme.valueOf(args[0]);

        int voterIndex = Integer.parseInt(args[2]);

        // Read the file containing the preference matrix
        PreferenceMatrix preferenceMatrix = null;
        try {
            String preferenceMatrixPath = args[1];
            preferenceMatrix = readPreferenceList(preferenceMatrixPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        int[] outcome = preferenceMatrix.calculateOutcome(scheme);
        double[] happiness = MASHappinessMetric.calculateHappinessList(preferenceMatrix, outcome);
        for (int i = 0; i < happiness.length; i++) {
            System.out.println("Voter " + i + ": " + happiness[i]);
        }

//        System.out.println((new MASHappinessMetric().calculateHappiness(new int[]{0, 1, 2, 3}, new int[]{1, 0, 2, 3})));
        // Example from the assignment.pdf, result should be 0.25
//        System.out.println((new MASHappinessMetric().calculateHappiness(new int[]{0, 1, 2, 3}, new int[]{2, 0, 1, 3})));

        Set<VotingOption> votingOptions = Permutator.permutate(preferenceMatrix.getPreferenceListOfVoter(voterIndex).length, voterIndex, preferenceMatrix, scheme);


        //not sure what to do with the list so just log it
        Iterator<VotingOption> it = votingOptions.iterator();
        while(it.hasNext()){
            VotingOption option = it.next();
            System.out.println(option);
        }
    }


    public static PreferenceMatrix readPreferenceList(String filePath)
            throws IOException // Todo I hate java's forced exception handling
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();

            // The length of the first line tells us how many candidates we have
            char[][] preferenceList = new char[line.length()][];
            preferenceList[0] = line.toCharArray();

            // Read the remaining lines
            for (int i = 1; i < preferenceList.length; i++) {
                preferenceList[i] = reader.readLine().toCharArray();
            }

            return new PreferenceMatrix(preferenceList);
        }
    }
}
