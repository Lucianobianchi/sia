from random import randint

class Individual:

    def __init__(self, genes):
        self._genes = genes
        self._fitness = self.calculate_fitness()

    def calculate_fitness(self):
        return randint(0, 10)

    def genes():
        return self._genes

    @property
    def fitness(self):
        return self._fitness
