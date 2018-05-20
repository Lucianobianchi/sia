from random import Random

random = Random() # TODO: seed

# Considerar si realmente queremos que vaya de 0 a genes_count
# porque abre la posibilidad de que no se crucen
# cuando da 0 o genes_count
def _one_point_crossover(pair):
    locus = random.randint(0, pair[0].genes_count)
    p0 = pair[0].genes
    p1 = pair[1].genes
    a = p0[:locus] + p1[locus:]
    b = p1[:locus] + p0[locus:]
    return (a, b)

def _two_point_crossover(pair):
    gen_count = pair[0].genes_count
    p0 = pair[0].genes
    p1 = pair[1].genes
    r1 = random.randint(1, gen_count - 2)
    r2 = random.randint(r1 + 1, gen_count - 1)
    a = p0[:r1] + p1[r1:r2] + p0[r2:]
    b = p1[:r1] + p0[r1:r2] + p1[r2:]
    return (a, b)

def _anular_crossover(pair):
    gen_count = pair[0].genes_count
    p0 = pair[0].genes
    p1 = pair[1].genes
    r = random.randint(0, gen_count - 1)
    l = random.randint(1, gen_count // 2)
    to = (r + l) % gen_count
    if to > r:
        a = p0[:r] + p1[r:to] + p0[to:]
        b = p1[:r] + p0[r:to] + p1[to:]
    else:
        a = p1[:to] + p0[to:r] + p1[r:]
        b = p0[:to] + p1[to:r] + p0[r:]
    return (a, b)

def _uniform_crossover(pair, p = 0.5):
    gen_count = pair[0].genes_count
    p0 = pair[0].genes
    p1 = pair[1].genes
    a = []
    b = []
    for i in range(gen_count):
        should_cross = random.random() < p
        if should_cross:
            a.append(p1[i])
            b.append(p0[i])
        else:
            a.append(p0[i])
            b.append(p1[i])
    return (a, b)

strategies = {
    'one_point': _one_point_crossover,
    'two_point': _two_point_crossover,
    'anular': _anular_crossover,
    'uniform': _uniform_crossover
}

def crossover(name = 'one_point'):
    return strategies[name]
