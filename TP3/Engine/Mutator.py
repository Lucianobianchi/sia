from random import random

class Mutator:
    def __init__(self, initial_probability, strategy = None):
        if strategy is None:
            strategy = lambda t: initial_probability

        self._mutate_prob = initial_probability
        self._probability_updater = strategy

    def mutate_genes(self, genes):
        for i, gene in enumerate(genes):
            if random() < self.mutate_prob:
                genes[i] = gene.mutate()
        return genes

    def update_probability(self, t):
        self._mutate_prob = self._probability_updater(t)
        
    @property
    def mutate_prob(self):
        return self._mutate_prob
