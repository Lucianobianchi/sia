# TODO: esto va en Engine no en Problem pero no se importar cosas en Python :(
from Individual import Individual
from random import random

class Selectors:
    @staticmethod
    def elite_selector(group, select_count):
        ranked = sorted(group, key = lambda i: i.fitness, reverse = True)
        return ranked[:select_count]

    @staticmethod
    def random_selector(group, select_count):
        shuffled = sorted(group, key = lambda x: random())
        return shuffled[:select_count]

    @classmethod
    def selector(cls, name = 'elite'):
        # mmmmm
        strategies = {
            'elite': cls.elite_selector,
            'random': cls.random_selector
        }
        return strategies[name]

G = [Individual(None), Individual(None), Individual(None), Individual(None), Individual(None)]
G = Selectors.selector('elite')(G, 3)
for i in G:
    print(i.fitness)

# Select from set of individuals -> selection strategies
# Make descendants -> crossover strategies
# Mutate -> mutation strategies
# Select from parents + descendants -> selection strategies
