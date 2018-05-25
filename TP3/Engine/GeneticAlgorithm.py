from math import ceil
from .CutConditionController import CutConditionController
from .Selectors import selector
from .Pairs import pairs
from .Crossovers import crossover
from .Mutator import Mutator
from .Replacers import replacer

# TODO: metricas, qué retorna

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

    mutator = Mutator(mutate_prob, next_mutate_prob)

    while controller.should_continue(population):
        if(metrics is not None):
            metrics.update(population)
            
        selected = do_selection(population, selector1, n_selector1, selector2, n_selector2, selector_params)
        selected_pairs = pairs_alg(selected, ceil(k / 2))

        children = [child_factory(mutator.mutate_genes(c)) for p in selected_pairs for c in crossover_alg(p)]
        children = children_selector(children, k)

        population = replacer_alg(population, children, B, selector3, selector4, **replace_params)

        mutator.update_probability(population, selector_params['t'])
        selector_params['t'] += 1
        replace_params['t'] += 1

    return population


def do_selection(population, s1, n1, s2, n2, params):
    selected1 = s1(population, n1, **params)
    selected2 = s2(population, n2, **params)
    return [*selected1, *selected2]