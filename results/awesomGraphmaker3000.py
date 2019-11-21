import pandas as pd
from matplotlib import pyplot as plt

data = pd.read_csv("results.csv", sep=",")

bordaData = data[data['scheme'] == 'BordaVoting']
pluralityData = data[data['scheme'] == 'PluralityVoting']
antiPluralityData = data[data['scheme'] == 'AntiPluralityVoting']
twoVoting = data[data['scheme'] == 'TwoVoting']

plt.plot(bordaData['nVoters'], bordaData['risk'].astype('float32'), label='Borda Voting')
plt.plot(pluralityData['nVoters'], pluralityData['risk'].astype('float32'), label='Plurality Voting')
plt.plot(antiPluralityData['nVoters'], antiPluralityData['risk'].astype('float32'), label='Anti-Plurality Voting')
plt.plot(twoVoting['nVoters'], twoVoting['risk'].astype('float32'), label='Two Voting')
plt.xlabel("Number of voters")
plt.ylabel("Risk of tactical voting")
plt.legend()


plt.show()
plt.savefig('plot.pdf')
