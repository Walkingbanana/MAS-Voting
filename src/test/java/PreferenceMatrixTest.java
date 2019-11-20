import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreferenceMatrixTest {

    private PreferenceMatrix preferenceMatrix;

    void setup() throws IOException {
        this.preferenceMatrix = null;
        String preferenceMatrixPath = "./PreferenceFiles/Test_4.txt";
        preferenceMatrix = Main.readPreferenceList(preferenceMatrixPath);
    }


    @Test
    public void testGetPreferenceValues() throws IOException {
        setup();
        char[] prefList = preferenceMatrix.getPreferenceListOfVoter(0);
        assertEquals('A', prefList[0]);
        assertEquals('D', prefList[1]);
        assertEquals('B', prefList[2]);
        assertEquals('C', prefList[3]);
    }

    @Test
    public void testSetPreferenceValues() throws IOException {
        setup();
        char[] newPref = new char[]{'C', 'B', 'A', 'D'};

        preferenceMatrix.setCharPreferenceListOfVoter(0, newPref);

        char[] prefList = preferenceMatrix.getPreferenceListOfVoter(0);

        assertEquals('C', prefList[0]);
        assertEquals('B', prefList[1]);
        assertEquals('A', prefList[2]);
        assertEquals('D', prefList[3]);
    }

}
