from random import randint

def _one_point(pair):
    locus = randint(0, pair[0].genes_count)
    new_genes = pair[0].genes[:locus] + pair[1].genes[locus:]
    return new_genes

strategies = {
    'one_point': _one_point,
    'two_point': None,
    'anular': None,
    'uniform_cross': None
}

def crossover(name = 'one_point'):
    return strategies[name]
