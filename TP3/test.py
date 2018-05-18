from Problem.Individual import Individual
from Engine.Selectors import selector
from Engine.Crossovers import crossover

N = 200
k = 10
G = [Individual(None) for _ in range(N)]

G = selector('universal')(G, k)
for i in G:
    print(i.fitness)

# Select from set of individuals -> selection strategies
# Make descendants -> crossover strategies
# Mutate -> mutation strategies
# Select from parents + descendants -> selection strategies

print(crossover('one_point')((Individual([1, 2, 3]), Individual([4, 5, 6]))))
