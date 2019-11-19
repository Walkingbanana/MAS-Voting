import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        args = new String[] {"BordaVoting", "./PreferenceFiles/Test.txt", "1"};

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

        VotingSituationAnalyzer analyzer = new VotingSituationAnalyzer(preferenceMatrix, scheme);
        Set<VotingOption> votingOptions = analyzer.analyzeVoter(voterIndex);

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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            ArrayList<char[]> preferences = new ArrayList<>();

            // Read all lines
            String line = reader.readLine();
            while(line != null)
            {
                preferences.add(line.toCharArray());
                line = reader.readLine();
            }

            return new PreferenceMatrix(preferences.toArray(new char[preferences.size()][]));
        }
    }
}
