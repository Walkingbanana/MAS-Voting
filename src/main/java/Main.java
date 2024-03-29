import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        executeStuff(args);
    }

    static double executeStuff(String[] args) {
//        args = new String[] {"BordaVoting", "./PreferenceFiles/Test_4.txt"};

        //Todo check input to make it robust
        // Parse the voting scheme
        VotingScheme scheme = VotingScheme.valueOf(args[0]);

        // Read the file containing the preference matrix
        PreferenceMatrix preferenceMatrix = null;
        try {
            String preferenceMatrixPath = args[1];
            preferenceMatrix = readPreferenceList(preferenceMatrixPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Analyze what our target voter can do, to manipulate the situation for his own gain
        VotingSituationAnalyzer analyzer = new VotingSituationAnalyzer(preferenceMatrix, scheme);
        ArrayList<VotingOption> options = new ArrayList<>();
        for (int voterIndex = 0; voterIndex < preferenceMatrix.getVoterCount(); voterIndex++) {
            ArrayList<VotingOption> votingOptions = analyzer.analyzeVoter(voterIndex);
            options.addAll(votingOptions);
        }


        // Print the true outcome
        int[] outcome = preferenceMatrix.calculateOutcome(scheme);
        char[] transformedOutcome = preferenceMatrix.toCharArray(outcome);

        if (args.length == 3) {
            writeLog(args[2], preferenceMatrix, options, transformedOutcome);
        }else{
            logConsole(preferenceMatrix, options, outcome, transformedOutcome);
        }



        return (double) options.size() / preferenceMatrix.getVoterCount();
    }

    private static void logConsole(PreferenceMatrix preferenceMatrix, ArrayList<VotingOption> options, int[] outcome, char[] transformedOutcome) {
        System.out.println("--- Initial Outcome: " + Arrays.toString(transformedOutcome) + " ---");

        double[] happiness = MASHappinessMetric.calculateHappinessList(preferenceMatrix, outcome);
        for (int i = 0; i < happiness.length; i++) {
            System.out.println("Happiness Voter " + (i + 1) + ": " + happiness[i]);
        }

        System.out.println("Risk of strategic voting: " + options.size() / (double) preferenceMatrix.getVoterCount());

        //Print all possible manipulations that our target vote can do
        Iterator<VotingOption> it = options.iterator();
        while (it.hasNext()) {
            System.out.println();

            VotingOption option = it.next();
            System.out.println("--- Possible Manipulation for voter " + (option.getVoterIndex() + 1) + " ---");
            System.out.println("New Preference List: " + Arrays.toString(option.getPreferenceList()));
            System.out.println("New Outcome: " + Arrays.toString(preferenceMatrix.toCharArray(option.getOutcome())));
            System.out.println("Summed Happiness: " + option.getHappinessLevel());
        }
    }

    private static void writeLog(String outputFile, PreferenceMatrix preferenceMatrix, ArrayList<VotingOption> options, char[] transformedOutcome) {
        System.out.println("Saving log to " + outputFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("--- Initial Outcome: " + Arrays.toString(transformedOutcome) + " ---");
            writer.newLine();
            writer.write("Risk of strategic voting: " + options.size() / (double) preferenceMatrix.getVoterCount());
            writer.newLine();
            writer.write("Voting Options:");
            writer.newLine();
            for (VotingOption option : options) {
                writer.newLine();
                writer.write("--- Possible Manipulation for voter " + (option.getVoterIndex() + 1) + " ---");
                writer.newLine();
                writer.write("New Preference List: " + Arrays.toString(option.getPreferenceList()));
                writer.newLine();
                writer.write("New Outcome: " + Arrays.toString(preferenceMatrix.toCharArray(option.getOutcome())));
                writer.newLine();
                writer.write("Summed Happiness: " + option.getHappinessLevel());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully wrote logfile");
    }


    public static PreferenceMatrix readPreferenceList(String filePath)
            throws IOException // Todo I hate java's forced exception handling
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            ArrayList<char[]> preferences = new ArrayList<>();

            // Read all lines
            String line = reader.readLine();
            while (line != null) {
                preferences.add(line.toCharArray());
                line = reader.readLine();
            }

            return new PreferenceMatrix(preferences.toArray(new char[preferences.size()][]));
        }
    }
}
