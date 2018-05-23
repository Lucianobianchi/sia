import random
import json
from Problem.Archer3 import generate_population, Archer3
from Engine.GeneticAlgorithm import search

config = json.load(open('config.json'))

# TODO: levantar bien los boltzmann_schedule y next_mutate_prob

config['next_mutate_prob'] = lambda p, t, prev_prob: prev_prob
config['select_params']['boltzmann_schedule'] = lambda t: 100 - t
config['replace_params']['boltzmann_schedule'] = lambda t: 100 - t
config['child_factory'] = lambda genes: Archer3(genes)


random.seed(config['seed'])

initial_population = generate_population(config['N'])
print('items loaded!')

result = search(initial_population, config)

print(sorted([i.fitness for i in result], reverse = True))
