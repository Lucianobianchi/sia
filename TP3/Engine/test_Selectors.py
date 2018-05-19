import unittest
from Selectors import selector
from random import Random
from unittest.mock import patch

class Individual:
    def __init__(self, fitness, name):
        self._fitness = fitness
        self._name = name

    @property
    def fitness(self):
        return self._fitness

    @property
    def name(self):
        return self._name

    def __eq__(self, other):
        if isinstance(self, other.__class__):
            return self._name == other._name
        return False     

# Random(1).random: [0.1343, 0.8474, 0.7637, 0.2550, 0.4954, 0.4494]

class TestSelectors(unittest.TestCase):        
    def setUp(self):
        self.i = 0
        self.A = Individual(0.005, 'A')
        self.B = Individual(0.65, 'B')
        self.C = Individual(0.24, 'C')
        self.D = Individual(0.105, 'D')
        self.group = [self.A, self.B, self.C, self.D] # notar que los fitness suman 1

    def test_elite(self):
        selected = selector('elite')(self.group, 2)
        self.assertIn(self.B, selected)
        self.assertIn(self.C, selected)
        self.assertEqual(2, len(selected))

    @patch('Selectors.random', Random(1))
    def test_roulette(self):
        selected = selector('roulette')(self.group, 3)
        self.assertIn(self.B, selected) # 0.005 < 0.1353 < 0.655
        self.assertIn(self.C, selected) # 0.655 < 0.8474 < 0.895
        self.assertIn(self.C, selected) # 0.655 < 0.7637 < 0.895
        self.assertEqual(3, len(selected))

    @patch('Selectors.random', Random(1))
    def test_universal(self):
        # r = 0.1343
        selected = selector('universal')(self.group, 3)
        self.assertIn(self.B, selected) # 0.005 < 0.04476 < 0.655
        self.assertIn(self.B, selected) # 0.005 < 0.3781 < 0.655
        self.assertIn(self.C, selected) # 0.655 < 0.7114 < 0.895
        self.assertEqual(3, len(selected))

if __name__ == '__main__':
    unittest.main()
    