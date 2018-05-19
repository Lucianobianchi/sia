from random import Random

random = Random() # TODO: seed

def _one_point(pair):
    locus = random.randint(0, pair[0].genes_count)
    a = pair[0].genes[:locus] + pair[1].genes[locus:]
    b = pair[1].genes[:locus] + pair[0].genes[locus:]
    return [a, b]

strategies = {
    'one_point': _one_point,
    'two_point': None,
    'anular': None,
    'uniform_cross': None
}

def crossover(name = 'one_point'):
    return strategies[name]
