from .HeightGen import HeightGen
from .ItemGen import ItemGen, items
from random import randint, uniform
from math import tanh

def generate_population(factory, size):
    population = [ factory([
        ItemGen('boot', randint(0, len(items('boot')) - 1)),
        ItemGen('weapon', randint(0, len(items('weapon')) - 1)),
        ItemGen('helmet', randint(0, len(items('helmet')) - 1)),
        ItemGen('glove', randint(0, len(items('glove')) - 1)),
        ItemGen('armor', randint(0, len(items('armor')) - 1)),
        HeightGen(uniform(1.3, 2.0))
    ]) for i in range(0, size) ]
    return population

class Soldier:
    gene_length = 6
    items_length = 5

    def __init__(self, genes, attribute_multipliers):
        if len(genes) != self.gene_length:
            raise ValueError(f'Genes length should be {self.gene_length}, received {genes}')

        self.assert_genes_items(genes)
        self.assert_genes_height(genes)
        self._genes = genes
        self._multipliers = attribute_multipliers
        self._fitness = self.calculate_fitness()

    def assert_genes_items(self, genes):
        for i in range(0, self.items_length):
            g = genes[i]
            if not isinstance(g, ItemGen):
                raise TypeError(f'Gene at index {i} should be of type ItemGen. Found {type(g).__name__}.')

    def assert_genes_height(self, genes):
        g = genes[self.items_length]
        if not isinstance(g, HeightGen):
            raise TypeError(f'Gene at index {self.items_length} should be of type HeightGen. Found {type(g).__name__}.')

    def calculate_fitness(self):
        return self.attack * self._multipliers['atk'] + self.defense * self._multipliers['def']

    def _sum_item_property(self, property_getter):
        return sum(property_getter(self.genes[i]) for i in range(0, 5))

    @property
    def strength(self):
        s = self._sum_item_property(lambda x: x.str * self._multipliers['str'])
        return 100 * tanh(0.01 * s)

    @property
    def agility(self):
        s = self._sum_item_property(lambda x: x.agi * self._multipliers['agi'])
        return tanh(0.01 * s)

    @property
    def dexterity(self):
        s = self._sum_item_property(lambda x: x.dex * self._multipliers['dex'])
        return 0.6 * tanh(0.01 * s)

    @property
    def resistance(self):
        s = self._sum_item_property(lambda x: x.res * self._multipliers['res'])
        return tanh(0.01 * s)

    @property
    def vitality(self):
        s = self._sum_item_property(lambda x: x.vit * self._multipliers['vit'])
        return 100 * tanh(0.01 * s)

    @property
    def attack(self):
        return (self.agility + self.dexterity) * self.strength * self.genes[-1].atm

    @property
    def defense(self):
        return (self.resistance + self.dexterity) * self.vitality * self.genes[-1].dem

    @property
    def genes(self):
        return self._genes

    @property
    def genes_count(self):
        return len(self._genes)

    @property
    def fitness(self):
        return self._fitness

    def __repr__(self):
        return f'{{fit: {self.fitness}, {self.genes}}}'

    def __eq__(self, other):
        if not isinstance(self, other.__class__):
            return False
        for pair in zip(self.genes, other.genes):
            if not pair[0] == pair[1]:
                return False
        return True

    def __hash__(self):
        return hash(tuple(self._genes))
