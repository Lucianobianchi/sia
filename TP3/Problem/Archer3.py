from .HeightGen import HeightGen
from .ItemGen import ItemGen
from random import randint, uniform
from math import tanh

def generate_population(size):
    population = [ Archer3([
        ItemGen('boot', randint(0, 9)),
        ItemGen('weapon', randint(0, 9)),
        ItemGen('helmet', randint(0, 9)),
        ItemGen('glove', randint(0, 9)),
        ItemGen('armor', randint(0, 9)),
        HeightGen(uniform(1.3, 2.0))
    ]) for i in range(0, size) ]
    return population

class Archer3:
    gene_length = 6
    items_length = 5

    def __init__(self, genes):
        if len(genes) != self.gene_length:
            raise ValueError('Genes length should be {0}, received {1}'.format(self.gene_length, len(genes)))
        # TODO: Assert de los tipos de items tambien?
        self.assert_genes_items(genes)
        self.assert_genes_height(genes)
        self._genes = genes
        self._fitness = self.calculate_fitness()

    def calculate_fitness(self):
        return randint(0, 10)

    def assert_genes_items(self, genes):
        for i in range(0, self.items_length):
            g = genes[i]
            if not isinstance(g, ItemGen):
                raise TypeError('Gene at index {0} should be of type ItemGen. Found {1}.'\
                    .format(i, type(g).__name__))

    def assert_genes_height(self, genes):
        g = genes[self.items_length]
        if not isinstance(g, HeightGen):
            raise TypeError('Gene at index {0} should be of type HeightGen. Found {1}.' \
                .format(self.items_length, type(g).__name__))

    def _sum_item_property(self, property_getter):
        return sum(property_getter(self.genes[i]) for i in range(0, 5))

    @property
    def strength(self):
        s = self._sum_item_property(lambda x: x.str * 0.8)
        return 100 * tanh(0.01 * s)

    @property
    def agility(self):
        s = self._sum_item_property(lambda x: x.agi * 0.8)
        return tanh(0.01 * s)

    @property
    def dexterity(self):
        s = self._sum_item_property(lambda x: x.dex * 0.8)
        return 0.6 * tanh(0.01 * s)

    @property
    def resistance(self):
        s = self._sum_item_property(lambda x: x.res * 1.1)
        return tanh(0.01 * s)

    @property
    def vitality(self):
        s = self._sum_item_property(lambda x: x.vit * 1.2)
        return 100 * tanh(0.01 * s)

    @property
    def genes(self):
        return self._genes

    @property
    def genes_count(self):
        return len(self._genes)

    @property
    def attack(self):
        return (self.agility + self.dexterity) * self.strength * self.genes[-1].atm

    @property
    def defense(self):
        return (self.resistance + self.dexterity) * self.vitality * self.genes[-1].dem

    @property
    def fitness(self):
        return 0.9 * self.attack + 0.1 * self.defense

    def __repr__(self):
        return '{{fit: {0}, {1}}}'.format(self.fitness, self.genes)

    def __eq__(self, other):
        if not isinstance(self, other.__class__):
            return False
        for pair in zip(self.genes, other.genes):
            if not pair[0] == pair[1]
                return False
        return True
