package stategy;

public class NoOpStrategy implements Strategy {
    public int[] getVoting(int[] preferences) {
        return preferences;
    }
}
