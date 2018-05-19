from HeightGen import HeightGen
from ItemGen import ItemGen
from random import randint, uniform

def generate_population(size):
    population = [ Archer3([
        ItemGen('helmet', randint(0, 10)),
        ItemGen('sword', randint(0, 10)),
        ItemGen('glove', randint(0, 10)),
        ItemGen('shield', randint(0, 10)),
        ItemGen('gem', randint(0, 10)),
        HeightGen(uniform(1.3, 2.0))
    ]) for i in range(0, size) ]
    return population

class Archer3:
    gene_length = 6
    items_length = 5

    def __init__(self, genes):
        if len(genes) != self.gene_length:
            raise ValueError('Genes length should be {0}, received {1}', self.gene_length, len(genes))
        # TODO: DUDA: si hacés los asserts estáticos no puedo acceder a las constantes de la clase
        # Es necesario hacer los assert o en Python no es adecuado y hay que dejarlo de palabra?
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
        return '{{fit: {0}, {1}}}'.format(self.fitness, self.genes)

# TODO: tests
pop = generate_population(10)
print(pop)
