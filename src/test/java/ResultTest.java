import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ResultTest {

    private final String OUTPUT_PATH = "./results/results_all.csv";

    @Test
    public void testGenerateResults() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH))) {

            StringBuilder header = new StringBuilder();
            header.append("test");
            header.append(",");
            header.append("scheme");
            header.append(",");
            header.append("risk");
            header.append(",");
            header.append("nVoters");
            header.append(System.lineSeparator());

            writer.write(header.toString());


            VotingScheme[] schemes = VotingScheme.values();
            String[] testNames = new String[]{"odd_4","diff", "half", "Test"};

            for (String testName : testNames) {
                for (VotingScheme scheme : schemes) {
                    for (int numberOfVoters = 4; numberOfVoters < 11; numberOfVoters++) {
                        String path = "./PreferenceFiles/" + testName + "_" + numberOfVoters + ".txt";

                        String[] args = new String[]{scheme.toString(), path};
                        double votingRisk = Main.executeStuff(args);


                        StringBuilder line = new StringBuilder();
                        line.append(testName);
                        line.append(",");
                        line.append(scheme.toString());
                        line.append(",");
                        line.append(votingRisk);
                        line.append(",");
                        line.append(numberOfVoters);
                        line.append(System.lineSeparator());

                        writer.write(line.toString());

                        System.out.println("Ran: " + testName + ", " + numberOfVoters + ", " + scheme.toString());
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
