from Problem.Individual import Individual
from Engine.Selectors import selector
from Engine.Crossovers import crossover

N = 10
k = 3
G = [Individual(None) for _ in range(N)]

for i in G:
    print(i.fitness)

print()

G = selector('tournament_prob')(G, k)
for i in G:
    print(i.fitness)

# Select from set of individuals -> selection strategies
# Make descendants -> crossover strategies
# Mutate -> mutation strategies
# Select from parents + descendants -> selection strategies

print(crossover('uniform')((Individual([0, 1, 2, 3, 4, 5, 6, 7, 8, 9]), Individual([10, 11, 12, 13, 14, 15, 16, 17, 18, 19]))))
