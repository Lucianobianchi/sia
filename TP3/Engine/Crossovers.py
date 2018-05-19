from random import Random

random = Random() # TODO: seed

def _one_point_crossover(pair):
    locus = random.randint(0, pair[0].genes_count)
    p0 = pair[0]
    p1 = pair[1]
    a = p0.genes[:locus] + p1.genes[locus:]
    b = p1.genes[:locus] + p0.genes[locus:]
    return [a, b]

def _two_point_crossover(pair):
    gen_count = pair[0].genes_count
    p0 = pair[0]
    p1 = pair[1]
    r1 = random.randint(1, gen_count - 2)
    r2 = random.randint(r1 + 1, gen_count - 1)
    a = p0.genes[:r1] + p1.genes[r1:r2] + p0.genes[r2:]
    b = p1.genes[:r1] + p0.genes[r1:r2] + p1.genes[r2:]
    return [a, b]
    

strategies = {
    'one_point': _one_point_crossover,
    'two_point': _two_point_crossover,
    'anular': None,
    'uniform_cross': None
}

def crossover(name = 'one_point'):
    return strategies[name]
