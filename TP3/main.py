from MetricManager import MetricManager
from math import exp
import random
import json
from Engine.GeneticAlgorithm import search

print('Loading items...')
from Problem.Archer3 import generate_population, Archer3
print('Items loaded')

while True:
    config = json.load(open('config.json'))

    config['next_mutate_prob'] = lambda t: config['mutate_prob'] * exp(-0.001 * t) 
    config['select_params']['schedule'] = lambda t: max(0.01, 1000 - t * 0.1) 
    config['replace_params']['schedule'] = config['select_params']['schedule']
    config['child_factory'] = lambda genes: Archer3(genes)

    random.seed(config['seed'])

    initial_population = generate_population(config['N'])

    metrics = MetricManager(realtime = True, refresher = 200) # realtime indica si se dibuja en tiempo real o no 
                                                              # refresher, cada cuantas muestras se refresca (default 100)
    result = search(initial_population, config, metrics)

    # metrics.plot() -- Esto si se seteo realtime = False

    print(sorted([i.fitness for i in result], reverse = True))
    input('Configure config.json and press any key to continue')
