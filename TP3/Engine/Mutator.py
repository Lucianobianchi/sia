from random import random

class Mutator:
    def __init__(self, initial_probability, strategy = lambda p,t,x: x):
        self._mutate_prob = initial_probability
        self._probability_updater = strategy

    def mutate_genes(self, genes):
        for i, gene in enumerate(genes):
            if random() < self.mutate_prob:
                genes[i] = gene.mutate()
        return genes

    def update_probability(self, population, t):
        self._mutate_prob = self._probability_updater(population, t, self.mutate_prob)
        
    @property
    def mutate_prob(self):
        return self._mutate_prob
