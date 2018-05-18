from random import random
from random import sample
from numpy import cumsum

def _elite_selector(group, select_count):
    ranked = sorted(group, key = lambda i: i.fitness, reverse = True)
    return ranked[:select_count]

def _random_selector(group, select_count):
    return sample(group, select_count)

def _roulette_selector(group, select_count):
    acum = _acum_rel_fitness(group)
    return [group[_spin_roulette(acum)] for _ in range(select_count)]

def _acum_rel_fitness(group):
    total_fitness = sum(i.fitness for i in group) # More efficient to use generator than map()
    acum_rel_fitness = cumsum([i.fitness / total_fitness for i in group]).tolist()
    acum_rel_fitness.insert(0, 0)
    return acum_rel_fitness    

def _spin_roulette(acum_rel_fitness):
    slot = random()
    return _find_between(acum_rel_fitness, slot)

def _find_between(sorted_values, slot):
    left = 0
    right = len(sorted_values) - 1
    mid = int((left + right) / 2)
    return _winner_index(sorted_values, slot, left, mid, right)    

def _winner_index(sorted_values, slot, left, mid, right):
    if slot >= sorted_values[mid - 1] and slot < sorted_values[mid]:
        return mid - 1 # - 1 since 0 was prepended
    if slot >= sorted_values[mid]:
        left = mid + 1
    else:
        right = mid - 1
    mid = int((left + right) / 2)
    return _winner_index(sorted_values, slot, left, mid, right)

def _universal_selector(group, select_count):
    acum = _acum_rel_fitness(group)
    k = select_count
    r = random()
    return [group[_find_between(acum, (r + j) / k)] for j in range(0, k)] # Could be more efficient without calling _find_between

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
