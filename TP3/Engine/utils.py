from random import random
from itertools import accumulate
from collections import Counter

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

def intersect_with_repeated(list1, list2):
    list1_counter = Counter(list1)
    list2_counter = Counter(list2)
    intersection = list1_counter & list2_counter
    return list(intersection.elements())
