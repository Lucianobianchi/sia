import unittest
from unittest.mock import patch, call
from CutConditionController import CutConditionController

class Individual:
    def __init__(self, genes):
        self._genes = genes

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


if __name__ == '__main__':
    unittest.main()
