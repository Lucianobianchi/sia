import unittest
from Selectors import selector
from random import Random
from unittest.mock import patch, call

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


class TestSelectors(unittest.TestCase):
    def setUp(self):
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

    # Random(1).random: [0.1343, 0.8474, 0.7637, 0.2550, 0.4954, 0.4494]
    @patch('Selectors.choices', Random(1).choices)
    def test_roulette(self):
        selected = selector('roulette')(self.group, 3)
        self.assertIn(self.B, selected) # 0.005 < 0.1353 < 0.655
        self.assertIn(self.C, selected) # 0.655 < 0.8474 < 0.895
        self.assertIn(self.C, selected) # 0.655 < 0.7637 < 0.895
        self.assertNotIn(self.A, selected)
        self.assertNotIn(self.D, selected)
        self.assertEqual(3, len(selected))

    @patch('Selectors.random')
    def test_universal(self, random):
        random.return_value = 0.1343
        selected = selector('universal')(self.group, 3)
        self.assertIn(self.B, selected) # 0.005 < 0.04476 < 0.655
        self.assertIn(self.B, selected) # 0.005 < 0.3781 < 0.655
        self.assertIn(self.C, selected) # 0.655 < 0.7114 < 0.895
        self.assertNotIn(self.A, selected)
        self.assertNotIn(self.D, selected)
        self.assertEqual(3, len(selected))

    @patch('Selectors.sample')
    def test_tournament_det(self, sample):
        sample.side_effect = [[self.A, self.D], [self.B, self.C], [self.C, self.B]]
        selected = selector('tournament_det')(self.group, 3)
        self.assertIn(self.D, selected)
        self.assertIn(self.B, selected)
        self.assertNotIn(self.A, selected)
        self.assertNotIn(self.C, selected)
        self.assertEqual(3, len(selected))
        self.assertEqual(3, sample.call_count)
        sample.assert_called_with(self.group, 2)

    @patch('Selectors.sample')
    @patch('Selectors.random')
    def test_tournament_prob(self, random, sample):
        random.side_effect = [0.9, 0.66, 0.5]
        sample.side_effect = [[self.A, self.D], [self.B, self.C], [self.C, self.B]]
        selected = selector('tournament_prob')(self.group, 3)
        self.assertIn(self.A, selected)
        self.assertIn(self.B, selected)
        self.assertNotIn(self.C, selected)
        self.assertNotIn(self.D, selected)
        self.assertEqual(3, len(selected))
        self.assertEqual(3, sample.call_count)
        self.assertEqual(3, random.call_count)


if __name__ == '__main__':
    unittest.main()
    