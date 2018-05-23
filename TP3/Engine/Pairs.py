from random import shuffle
from .utils import choices

def _random_pairs(group, k):
    shuffle(group) # warning: changes group order
    pairs = []
    i = 0
    while len(pairs) < k:
        pairs.append((group[i], group[i+1]))
        i += 2
        if i >= len(pairs) - 1:
            shuffle(group)
            i = 0

    return pairs

def _roulette_pairs(group, k):
    fitnesses = [i.fitness for i in group]
    pairs = []
    while len(pairs) < k:
        p = choices(group, fitnesses, 2)
        if p[0] is not p[1]:
            pairs.append(p)
    return pairs

# agarrar 2 diferentes totalmente random con reemplazo

# agarrar los N mejores y cruzarlos con todos

# 

strategies = {
    'random': _random_pairs,
    'roulette': _roulette_pairs
}

def pairs(name):
    return strategies[name]

pairs('roulette')
