from MetricManager import MetricManager
from math import exp
import random
import json
from Engine.GeneticAlgorithm import search

print('Loading items...')
from Problem.Soldier import generate_population
from Problem.Archer import factory as archer_factory
from Problem.Assassin import factory as assassin_factory
from Problem.Defender import factory as defender_factory
from Problem.Warrior import factory as warrior_factory
print('Items loaded')

factories = {
    'archer1': archer_factory(1),
    'archer2': archer_factory(2),
    'archer3': archer_factory(3),
    'assassin1': assassin_factory(1),
    'assassin2': assassin_factory(2),
    'assassin3': assassin_factory(3),
    'defender1': defender_factory(1),
    'defender2': defender_factory(2),
    'defender3': defender_factory(3),
    'warrior1': warrior_factory(1),
    'warrior2': warrior_factory(2),
    'warrior3': warrior_factory(3)
}

while True:
    print('Searching...')
    config = json.load(open('config.json'))

    if config['increasing_mutation']:
        config['next_mutate_prob'] = lambda t: config['mutate_prob'] * (1 - exp(-0.001 * t))
    else:
        config['next_mutate_prob'] = lambda t: exp(-0.001 * t) * config['mutate_prob']

    config['select_params']['schedule'] = lambda t: max(0.1, 1000 - t * 0.1) 
    config['replace_params']['schedule'] = config['select_params']['schedule']
    config['child_factory'] = factories[config['soldier'].lower()]

    random.seed(config['seed'])

    initial_population = generate_population(config['child_factory'], config['N'])

    metrics = MetricManager(realtime = config['realtime'], refresher = config['refresher']) # realtime indica si se dibuja en tiempo real o no 
                                                                                            # refresher, cada cuantas muestras se refresca (default 100)
    result = search(initial_population, config, metrics)

    print(sorted([i.fitness for i in result], reverse = True))

    t = metrics.generations
    data = metrics.generation_data(t) 
    print(f'Generations: {t}')
    del data['fitnesses']
    print(data)

    metrics.plot() # -- Esto si se seteo realtime = False

    input('Configure config.json, close the plot and press any key to continue')
