from random import random
from itertools import accumulate

def choices(population, weights, k):
    if len(weights) != len(population):
        raise ValueError('The number of weights does not match the population')
  
    rel_acum_weights = list(accumulate(weights))
    total = rel_acum_weights[-1]
    
    for i in range(len(rel_acum_weights)):
        rel_acum_weights[i] /= total

    return [population[between_index(rel_acum_weights, random())] for _ in range(k)]

def between_index(population, x):
    low = 0
    high = len(population)

    while low < high:
        mid = (low + high) // 2
        if x < population[mid]: 
            high = mid
        else: 
            low = mid + 1
    
    return low
