from random import random

def mutate_genes(genes, mutate_prob):
    for i, gene in enumerate(genes):
        if random() < mutate_prob:
            genes[i] = gene.mutate()
    return genes
