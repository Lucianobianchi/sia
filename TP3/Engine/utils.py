from random import random
from itertools import accumulate

def choices(population, weights, k):
    if len(weights) != len(population):
        raise ValueError('The number of weights does not match the population')
  
    acum_weights = list(accumulate(weights))
    total = acum_weights[-1]
    
    return [population[between_index(acum_weights, random() * total)] for _ in range(k)]

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
