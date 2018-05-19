from random import Random

random = Random() # TODO: seed

def mutate_genes(genes, mutate_prob):
    for i, gene in enumerate(genes):
        if random.random() < mutate_prob:
            genes[i] = gene.mutate()
    return genes
