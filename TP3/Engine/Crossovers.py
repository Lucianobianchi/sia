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

def _anular_crossover(pair):
    gen_count = pair[0].genes_count
    p0 = pair[0]
    p1 = pair[1]
    r = random.randint(0, gen_count - 1)
    l = random.randint(1, gen_count // 2)
    to = (r + l) % gen_count
    if to > r:
        a = p0.genes[:r] + p1.genes[r:to] + p0.genes[to:]
        b = p1.genes[:r] + p0.genes[r:to] + p1.genes[to:]
    else:
        a = p1.genes[:to] + p0.genes[to:r] + p1.genes[r:]
        b = p0.genes[:to] + p1.genes[to:r] + p0.genes[r:]
    return [a, b]

strategies = {
    'one_point': _one_point_crossover,
    'two_point': _two_point_crossover,
    'anular': _anular_crossover,
    'uniform_cross': None
}

def crossover(name = 'one_point'):
    return strategies[name]
