from random import random
from random import sample
from numpy import cumsum

def _elite_selector(group, select_count):
    ranked = sorted(group, key = lambda i: i.fitness, reverse = True)
    return ranked[:select_count]

def _random_selector(group, select_count):
    return sample(group, select_count)

def _roulette_selector(group, select_count):
    total_fitness = sum(i.fitness for i in group) # More efficient to use generator than map()
    acum_rel_fitness = cumsum([i.fitness / total_fitness for i in group]).tolist()
    acum_rel_fitness.insert(0, 0)
    selected = []
    for _ in range(select_count):
        selected_index = _spin_roulette(acum_rel_fitness)
        selected.append(group[selected_index])
    return selected

def _spin_roulette(acum_rel_fitness):
    slot = random()
    left = 0
    right = len(acum_rel_fitness) - 1
    mid = int((left + right) / 2)
    return _winner_index(acum_rel_fitness, slot, left, mid, right)

def _winner_index(acum_rel_fitness, slot, left, mid, right):
    if slot >= acum_rel_fitness[mid - 1] and slot < acum_rel_fitness[mid]:
        return mid - 1 # - 1 since 0 was prepended
    if slot >= acum_rel_fitness[mid]:
        left = mid + 1
    else:
        right = mid - 1
    mid = int((left + right) / 2)
    return _winner_index(acum_rel_fitness, slot, left, mid, right)

strategies = {
    'elite': _elite_selector,
    'random': _random_selector,
    'roulette': _roulette_selector,
    'universal': None,
    'boltzmann': None,
    'tournament_deterministic': None,
    'tournament_probabilistic': None,
    'ranking': None
}

def selector(name = 'elite'):
    return strategies[name]
