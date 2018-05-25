import unittest
from unittest.mock import patch, call, PropertyMock
from CutConditionController import CutConditionController
from random import randint, choice

class Individual:
    def __init__(self, genes):
        self._genes = genes
        self._fitness = randint(0, 10)

    @property
    def fitness(self):
        return self._fitness

    @fitness.setter
    def fitness(self, val):
        self._fitness = val

    @property
    def genes(self):
        return self._genes

    def __eq__(self, other):
        if not isinstance(self, other.__class__):
            return False
        for pair in zip(self.genes, other.genes):
            if not pair[0] == pair[1]:
                return False
        return True

# TODO: ver como se hace mockeando Individual
def set_population_fitness(population, fit):
    for ind in population:
        ind.fitness = fit

def set_one_fitness(population, fit):
    choice(population).fitness = fit

class TestCutCondition(unittest.TestCase):
    def setUp(self):
        i1 = Individual([0, 1, 2, 3, 4, 5, 6])
        i1_like = Individual([0, 1, 2, 3, 4, 5, 6])
        i2 = Individual([1, 2, 3, 4, 5, 6, 7])
        i3 = Individual([2, 3, 4, 5, 6, 7, 8])
        i4 = Individual([3, 4, 5, 6, 7, 8, 9])
        self.test_pop = [i1, i1_like, i2, i3, i4]

    def test_max_generations(self):
        gens = 5
        controller = CutConditionController('generations', {'max_generations': gens})
        for i in range(0, 5):
            self.assertTrue(controller.should_continue(self.test_pop))
        self.assertFalse(controller.should_continue(self.test_pop))

    def test_max_fitness(self):
        target = 10.0
        controller = CutConditionController('max_fitness', {'target_fitness': target})
        set_population_fitness(self.test_pop, target - 1)
        self.assertTrue(controller.should_continue(self.test_pop))
        set_one_fitness(self.test_pop, target) # change any to target value
        self.assertFalse(controller.should_continue(self.test_pop))

    def test_content(self):
        pct_dif = 0.1
        max_steps = 5
        fit = 10
        controller = CutConditionController('content',
            { 'content_pct_difference': pct_dif, 'max_content_steps': max_steps })
        set_population_fitness(self.test_pop, fit)

        for i in range(0, max_steps):
            fit = (1 + (pct_dif / max_steps)) * fit
            set_one_fitness(self.test_pop, fit)
            self.assertTrue(controller.should_continue(self.test_pop))
        self.assertFalse(controller.should_continue(self.test_pop))

        fit = 10
        controller = CutConditionController('content',
            { 'content_pct_difference': pct_dif, 'max_content_steps': max_steps })
        for i in range(0, max_steps):
            fit = (1 + (pct_dif / max_steps) * 2) * fit
            set_one_fitness(self.test_pop, fit)
            self.assertTrue(controller.should_continue(self.test_pop))
        self.assertTrue(controller.should_continue(self.test_pop))



if __name__ == '__main__':
    unittest.main()
