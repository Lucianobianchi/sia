from Problem.Archer3 import generate_population, Archer3
from Engine.GeneticAlgorithm import search

factory = lambda genes: Archer3(genes)

config = {
    'N': 100,
    'selectors': ['tournament_prob', 'roulette', 'tournament_det', 'ranking'],
    'A': 0.2,
    'B': 0.4,
    'tournament_m': 2,
    'boltzmann_schedule': lambda t: t - 100, # TODO
    'pairs': 'random',
    'crossover': 'anular',
    'replacer': 'select_parents',
    'mutate_prob': 0.01,
    'next_mutate_prob': lambda p, t, prev_prob: prev_prob,         # uniform
    'child_factory': factory,
    'generation_gap': 0.8,
    'should_continue': 'generations',
    'cut_conditions': {
        'max_generations': 3000,
        'target_fitness': 200                # TODO
    }
}


initial_population = generate_population(config['N'])
print(search(initial_population, config))
