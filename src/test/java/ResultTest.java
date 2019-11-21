import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ResultTest {

    private final String OUTPUT_PATH = "./results/results.csv";

    @Test
    public void testGenerateResults() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH))) {

            VotingScheme[] schemes = VotingScheme.values();
            for (VotingScheme scheme : schemes) {
                for (int i = 4; i < 11; i++) {
                    String path = "./PreferenceFiles/Test_" + i + ".txt";

                    String[] args = new String[]{scheme.toString(), path};
                    double votingRisk = Main.executeStuff(args);


                    StringBuilder line = new StringBuilder();
                    line.append(scheme.toString());
                    line.append(",");
                    line.append(votingRisk);
                    line.append(",");
                    line.append(i);
                    line.append(System.lineSeparator());

                    writer.write(line.toString());
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
