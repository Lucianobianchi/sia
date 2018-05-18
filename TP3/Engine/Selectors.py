from random import random
from random import sample
from random import choices
from bisect import bisect
from itertools import accumulate

def _elite_selector(group, select_count):
    ranked = sorted(group, key = lambda i: i.fitness, reverse = True)
    return ranked[:select_count]

def _random_selector(group, select_count):
    return sample(group, select_count)

def _roulette_selector(group, select_count):
    fitnesses = [i.fitness for i in group]
    return choices(group, weights = fitnesses, k = select_count)

def _universal_selector(group, select_count):
    acum = _acum_rel_fitness(group)
    k = select_count
    r = random()
    return [group[bisect(acum, (r + j) / k)] for j in range(0, k)] # Could be more efficient without calling bisect each time

def _acum_rel_fitness(group):
    total_fitness = sum(i.fitness for i in group) # More efficient to use generator than map()
    acum_rel_fitness = list(accumulate([i.fitness / total_fitness for i in group]))
    acum_rel_fitness.insert(0, 0)
    return acum_rel_fitness    

strategies = {
    'elite': _elite_selector,
    'random': _random_selector,
    'roulette': _roulette_selector,
    'universal': _universal_selector,
    'boltzmann': None,
    'tournament_deterministic': None,
    'tournament_probabilistic': None,
    'ranking': None
}

def selector(name = 'elite'):
    return strategies[name]
