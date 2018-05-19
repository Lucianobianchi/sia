from random import Random
from bisect import bisect
from itertools import accumulate

fit_getter = lambda i: i.fitness
random = Random()

def _elite_selector(group, select_count):
    ranked = sorted(group, key = fit_getter, reverse = True)
    return ranked[:select_count]

def _random_selector(group, select_count):
    return random.sample(group, select_count)

def _roulette_selector(group, select_count):
    fitnesses = [i.fitness for i in group]
    return random.choices(group, weights = fitnesses, k = select_count)

def _universal_selector(group, select_count):
    acum = _acum_rel_fitness(group)
    k = select_count
    r = random.random()
    return [group[bisect(acum, (r + j) / k)] for j in range(0, k)] # Could be more efficient without calling bisect each time

def _acum_rel_fitness(group):
    total_fitness = sum(i.fitness for i in group)
    acum_rel_fitness = list(accumulate([i.fitness / total_fitness for i in group]))
    return acum_rel_fitness

# TODO: considerar tener algo que sea como 'load_selectors(config)' que settee parámetros extras como la m de tournament
def _tournament_det_selector(group, select_count, m = 2):
    return [max(random.sample(group, select_count), key = fit_getter) for _ in range(select_count)]

def _tournament_prob_selector(group, select_count):
    return [_pick_winner(random.sample(group, 2)) for _ in range(select_count)]

def _pick_winner(contenders):
    r = random.random()
    print(str(r))
    print(str([i.fitness for i in contenders]))
    return max(contenders, key = fit_getter) if r < 0.75 else min(contenders, key = fit_getter)

strategies = {
    'elite': _elite_selector,
    'random': _random_selector,
    'roulette': _roulette_selector,
    'universal': _universal_selector,
    'boltzmann': None,
    'tournament_det': _tournament_det_selector,
    'tournament_prob': _tournament_prob_selector,
    'ranking': None
}

def selector(name = 'elite'):
    return strategies[name]
