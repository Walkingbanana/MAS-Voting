public enum VotingScheme
{
    PluralityVoting,
    AntiPluralityVoting,
    BordaVoting,
    TwoVoting;

    public int[] GetVotingVector(int numCandidates)
    {
        int[] votingVector = new int[numCandidates];

        // Just to fuck with Kai: I'm not going to split this into methods
        if(this == PluralityVoting)
        {
            votingVector[0] = 1;
        }
        else if(this == AntiPluralityVoting)
        {
            for(int i = 0; i < votingVector.length - 1; i++)
            {
                votingVector[i] = 1;
            }
        }
        else if(this == BordaVoting)
        {
            for(int i = 0; i < votingVector.length - 1; i++)
            {
                votingVector[i] = votingVector.length - i - 1;
            }
        }
        else if(this == TwoVoting)
        {
            votingVector[0] = 1;
            votingVector[1] = 1;
        }

        return votingVector;
    }
}
