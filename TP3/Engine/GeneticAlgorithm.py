from random import random
from math import ceil
from .CutConditionController import CutConditionController
from .Selectors import selector
from .Pairs import pairs
from .Crossovers import crossover
from .Mutator import Mutator
from .Replacers import replacer

def search(population, config, metrics = None):
    controller = CutConditionController(config['should_continue'], config['cut_conditions'])
    
    selector_params = config['select_params']
    selector_params['t'] = 0

    replace_params = config['replace_params']
    replace_params['t'] = 0

    selector1 = selector(config['selectors'][0])
    selector2 = selector(config['selectors'][1])
    children_selector = selector('elite')
    pairs_alg = pairs(config['pairs'])
    crossover_alg = crossover(config['crossover'])
    cross_prob = config['cross_prob']
    mutate_prob = config['mutate_prob']
    next_mutate_prob = config['next_mutate_prob']
    child_factory = config['child_factory']
    generation_gap = config['generation_gap']
    replacer_alg = replacer(config['replacer'])
    selector3 = selector(config['selectors'][2])
    selector4 = selector(config['selectors'][3])
    B = config['B']

    N = len(population)
    k = round(generation_gap * N)
    n_selector1 = round(k * config['A'])
    n_selector2 = k - n_selector1

    if config['uniform_mutate']:
        mutator = Mutator(mutate_prob)
    else:
        mutator = Mutator(mutate_prob, next_mutate_prob)

    if metrics is not None:
        metrics.update(population)

    while controller.should_continue(population):            
        selected = do_selection(population, selector1, n_selector1, selector2, n_selector2, selector_params)
        selected_pairs = pairs_alg(selected, ceil(k / 2))

        children = []

        children = [child_factory(mutator.mutate_genes(g)) for p in selected_pairs for g in try_cross(p, crossover_alg, cross_prob)]
        children = children_selector(children, k)

        population = replacer_alg(population, children, B, selector3, selector4, **replace_params)

        mutator.update_probability(selector_params['t'])
        selector_params['t'] += 1
        replace_params['t'] += 1

        if metrics is not None:
            metrics.update(population)

    return population

def try_cross(pair, crossover_alg, cross_prob):
    if random() < cross_prob:
        return crossover_alg(pair)
    return (pair[0].genes, pair[1].genes)

def do_selection(population, s1, n1, s2, n2, params):
    selected1 = s1(population, n1, **params)
    selected2 = s2(population, n2, **params)
    return [*selected1, *selected2]
