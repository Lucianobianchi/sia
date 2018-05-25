import unittest
from unittest.mock import patch, call, PropertyMock
from CutConditionController import CutConditionController
from random import randint, choice, shuffle

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

    def mutate(self):
        new_genes = list(self._genes)
        new_genes[randint(0, len(new_genes)-1)] += 1
        return Individual(new_genes)

    def __eq__(self, other):
        if not isinstance(self, other.__class__):
            return False
        for pair in zip(self.genes, other.genes):
            if not pair[0] == pair[1]:
                return False
        return True

    def __hash__(self):
        return hash(tuple(self._genes))

    def __repr__(self):
        return str(self._genes)

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

    # structure is not changed when order of individuals change
    def test_structure_order_change(self):
        max_steps = 5
        controller = CutConditionController('structure',
            { 'population_change': 1, 'max_structure_steps': max_steps })
        for i in range(0, max_steps):
            self.assertTrue(controller.should_continue(self.test_pop))
            shuffle(self.test_pop)
        self.assertFalse(controller.should_continue(self.test_pop))

    def test_structure_counter(self):
        max_steps = 5
        pop_change = 2
        controller = CutConditionController('structure',
            { 'population_change': pop_change, 'max_structure_steps': max_steps })

        # change structure of first 'pop_change' individuals
        self.assertTrue(controller.should_continue(self.test_pop))
        for i in range(0, pop_change):
            self.test_pop[i] = self.test_pop[i].mutate()
            self.assertTrue(controller.should_continue(self.test_pop))
        # counter goes back to 0
        self.assertEqual(0, controller._structure_steps)

    def test_structure_stop(self):
        max_steps = 5
        pop_change = 3
        controller = CutConditionController('structure',
            { 'population_change': pop_change, 'max_structure_steps': max_steps })

        # change structure of first 'pop_change' individuals
        self.assertTrue(controller.should_continue(self.test_pop))
        for i in range(0, pop_change - 1):
            self.test_pop[i] = self.test_pop[i].mutate()
            self.assertTrue(controller.should_continue(self.test_pop))
        # only change a few individuals, less than the required population_change
        for i in range(pop_change, max_steps):
            self.assertTrue(controller.should_continue(self.test_pop))
        self.assertFalse(controller.should_continue(self.test_pop))

if __name__ == '__main__':
    unittest.main()
