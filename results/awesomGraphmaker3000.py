import pandas as pd
from matplotlib import pyplot as plt



def plotTestResults(data, testName):
    testData = data[data['test'] == testName]
    bordaData = testData[testData['scheme'] == 'BordaVoting']
    pluralityData = testData[testData['scheme'] == 'PluralityVoting']
    antiPluralityData = testData[testData['scheme'] == 'AntiPluralityVoting']
    twoVoting = testData[testData['scheme'] == 'TwoVoting']

    fig1, ax1 = plt.subplots()
    plt.plot(bordaData['nVoters'], bordaData['risk'], label='Borda Voting')
    plt.plot(pluralityData['nVoters'], pluralityData['risk'], label='Plurality Voting', color='pink')
    plt.plot(antiPluralityData['nVoters'], antiPluralityData['risk'], label='Anti-Plurality Voting')
    plt.plot(twoVoting['nVoters'], twoVoting['risk'], label='Two Voting')
    ax1.set_yscale('log')
    plt.xlabel("Number of voters")
    plt.ylabel("Risk of tactical voting")
    plt.legend()
    plt.savefig(testName + '.pdf')


data = pd.read_csv("results_all.csv", sep=",")
plotTestResults(data, 'diff')
plotTestResults(data, 'odd_4')
plotTestResults(data, 'half')
plotTestResults(data, 'Test')


