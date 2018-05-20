from Selectors import selector
from Crossovers import crossover
from Mutator import mutate_genes
from Replacers import replacer

def search(population, config):
    t = 0
    should_continue = config['should_continue']
    selector_alg = selector(config['selector'])
    select_count = config['select_count']
    crossover_alg = crossover(config['crossover'])
    mutate_prob = config['mutate_prob']
    next_mutate_prob = config['next_mutate_prob']
    child_factory = config['child_factory']

    while should_continue(population, t):
        selected = selector_alg(population, select_count)
        # TODO: pick pairs
        children = [child_factory(mutate_genes(c, mutate_prob)) for c in crossover_alg(p) for p in pairs]
        mutate_prob = next_mutate_prob(population, t, mutate_prob)
        population = replacer_alg(population, children)

    return population
