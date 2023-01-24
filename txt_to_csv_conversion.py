import pandas as pd

fin = open("flights.txt", "rt")
fout = open("flights1.txt", "wt")
for line in fin:
  line = line.rstrip() + '\n'
  line = ",".join(line.split())
  line = line+'\n'
  fout.write(line)
fin.close()
fout.close()

df = pd.read_csv('flights1.txt', header=None)
df.to_csv('data.csv', index=None, header=None)