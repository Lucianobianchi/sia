from random import random
from random import sample

def _elite_selector(group, select_count):
    ranked = sorted(group, key = lambda i: i.fitness, reverse = True)
    return ranked[:select_count]

def _random_selector(group, select_count):
    return sample(group, select_count)

strategies = {
    'elite': _elite_selector,
    'random': _random_selector,
    'roulette': None,
    'universal': None,
    'boltzmann': None,
    'tournament_deterministic': None,
    'tournament_probabilistic': None,
    'ranking': None
}

def selector(name = 'elite'):
    return strategies[name]
