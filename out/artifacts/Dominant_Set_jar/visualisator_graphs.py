import matplotlib.pyplot as plt
from sys import argv
from matplotlib.ticker import NullFormatter

file_name = 'output.txt'
if (len(argv) == 2):
    file_name = argv[1]

dominant = []
connected = []
disconnected = []
a = -1

with open(file_name) as f:
    for line in f:
        for x in line.split():
            a += 1
            if (a % 3 == 0):
                dominant.append(int(x))
            elif (a % 3 == 1):
                connected.append(int(x))
            else:
                disconnected.append(int(x))



fig, ax = plt.subplots(1, 3)

#ax.set_title("Charts of dominant sets")

ax[0].scatter(dominant, connected, c='#F08080', s=1)
ax[0].set_xlabel('Dominant set')
ax[0].set_ylabel('Connected set')
ax[0].set_facecolor('#FFFACD')


ax[1].scatter(dominant, disconnected, c='#F08080', s=1)
ax[1].set_xlabel('Dominant set')
ax[1].set_ylabel('Disconnected set')
ax[1].set_facecolor('#FFFACD')

ax[2].scatter(connected, disconnected, c='#F08080', s=1)
ax[2].set_xlabel('Connected set')
ax[2].set_ylabel('Disconnected set')
ax[2].set_facecolor('#FFFACD')


plt.show()
