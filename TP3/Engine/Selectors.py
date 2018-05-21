from random import random, sample
from utils import choices, between_index
from itertools import accumulate
from math import exp

fit_getter = lambda i: i.fitness

def _elite_selector(group, select_count):
    ranked = sorted(group, key = fit_getter, reverse = True)
    return ranked[:select_count]

def _random_selector(group, select_count):
    return sample(group, select_count)

def _roulette_selector(group, select_count):
    fitnesses = [i.fitness for i in group]
    return choices(group, fitnesses, select_count)

def _universal_selector(group, select_count):
    acum = _acum_rel_fitness(group)
    k = select_count
    r = random()
    return [group[between_index(acum, (r + j) / k)] for j in range(0, k)]

def _acum_rel_fitness(group):
    total_fitness = sum(i.fitness for i in group)
    acum_rel_fitness = list(accumulate([i.fitness / total_fitness for i in group]))
    return acum_rel_fitness

# TODO: revisar lo del avg. 
# Revisar también si se lo usa tanto como para selección como reemplazo porq t aumenta siempre
def _boltzmann_generator(schedule):
    t = 0
    def _boltzmann_selector(group, select_count):
        temp = schedule(t)
        exps = [exp(i.fitness / temp) for i in group]
        avg = sum(exps) / len(group)
        for i in range(len(group)):
            exps[i] /= avg
        t += 1
        return choices(group, pressure, select_count)
    return _boltzmann_selector

# TODO: considerar tener algo que sea como 'load_selectors(config)' que settee parámetros extras como la m de tournament
def _tournament_det_generator(m = 2):
    def _tournament_det_selector(group, select_count):
        return [max(sample(group, m), key = fit_getter) for _ in range(select_count)]
    return _tournament_det_selector

def _tournament_prob_selector(group, select_count):
    return [_pick_winner(sample(group, 2)) for _ in range(select_count)]

def _pick_winner(contenders):
    r = random()
    return max(contenders, key = fit_getter) if r < 0.75 else min(contenders, key = fit_getter)

def _ranking_selector(group, select_count):
    ranked = sorted(group, key = fit_getter)
    weights = range(1, len(ranked) + 1)
    return choices(ranked, weights, select_count)

strategies = {
    'elite': _elite_selector,
    'random': _random_selector,
    'roulette': _roulette_selector,
    'universal': _universal_selector,
    'boltzmann': _boltzmann_generator(lambda t: 100 - t), # TODO
    'tournament_det': _tournament_det_generator(2), # TODO
    'tournament_prob': _tournament_prob_selector,
    'ranking': _ranking_selector
}

def selector(name = 'elite'):
    return strategies[name]
