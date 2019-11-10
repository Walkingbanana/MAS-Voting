import java.util.ArrayList;
import java.util.Arrays;

public class MASHappinessMetric
{
    public double CalculateHappiness(int[] preferences, int[] outcome)
    {
        int distanceSum = 0;
        for(int row = 0; row < preferences.length; row++)
        {
            // Okay fuck java it does not even have a indexOf method
            // We propably should switch to Integer[] as it allows us to atleast use the Arrays.toList().indexOf method...
            // Even though they are probably slow as fuck, since we would start boxing all our primitive values...
            int index = 0;
            for(; index < outcome.length; index++)
            {
                if(outcome[index] == preferences[row])
                    break;
            }

            // Note: The formula from the MAS-slides would result in (index - row)
            // The reason why I'm reversing it is because the MAS-slides reverse the index (So index 0 is at the bottom of the array)
            distanceSum += (preferences.length - row) * (row - index);
        }

        return 1. / (1. + Math.abs(distanceSum));
    }

    public double[] CalculateHappiness(PreferenceMatrix matrix, int[] outcome)
    {
        int[][] intMatrix = matrix.GetIntPreferenceMatrix();
        double[] happinessVector = new double[matrix.GetCandidateCount()];
        for(int column = 0; column < matrix.GetCandidateCount(); column++)
        {
            happinessVector[column] = CalculateHappiness(intMatrix[column], outcome);
        }

        return happinessVector;
    }
}
