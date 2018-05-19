from random import randint

class Individual:
    def __init__(self, genes):
        self._genes = genes
        self._fitness = self.calculate_fitness()

    def calculate_fitness(self):
        return randint(0, 10)

    @property
    def genes(self):
        return self._genes

    @property
    def genes_count(self):
        return len(self._genes)

    @property
    def fitness(self):
        return self._fitness
