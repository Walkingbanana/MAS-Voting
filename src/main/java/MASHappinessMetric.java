public class MASHappinessMetric
{
    public static double calculateHappiness(int[] preferences, int[] outcome)
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

    public static double[] calculateHappinessList(PreferenceMatrix matrix, int[] outcome)
    {
        double[] happinessVector = new double[matrix.getVoterCount()];
        for(int i = 0; i < matrix.getVoterCount(); i++)
        {
            happinessVector[i] = calculateHappiness(matrix.getVoterPreferences(i), outcome);
        }

        return happinessVector;
    }

    public static double calculateHappiness(PreferenceMatrix matrix, int[] outcome)
    {
        double[] happinessList = calculateHappinessList(matrix, outcome);
        double happinessLevel = 0d;
        for(int i = 0; i< happinessList.length; i++){
            happinessLevel += happinessList[i];
        }
        return happinessLevel;
    }
}
