from MetricManager import MetricManager
import random
import json
from Engine.GeneticAlgorithm import search

print('Loading items...')
from Problem.Archer3 import generate_population, Archer3
print('Items loaded')

while True:
    input('Configure config.json and press any key to continue')
    config = json.load(open('config.json'))

    # TODO: levantar bien los boltzmann_schedule y next_mutate_prob

    config['next_mutate_prob'] = lambda p, t, prev_prob: prev_prob
    config['select_params']['boltzmann_schedule'] = lambda t: 100 - t
    config['replace_params']['boltzmann_schedule'] = lambda t: 100 - t
    config['child_factory'] = lambda genes: Archer3(genes)

    random.seed(config['seed'])

    initial_population = generate_population(config['N'])

    metrics = MetricManager(realtime = True, refresher = 200) # realtime indica si se dibuja en tiempo real o no 
                                                             # refresher, cada cuantas muestras se refresca (default 100)
    result = search(initial_population, config, metrics)

    # metrics.plot() -- Esto si se seteo realtime = False

    print(sorted([i.fitness for i in result], reverse = True))
