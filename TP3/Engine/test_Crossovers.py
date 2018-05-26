import unittest
from unittest.mock import patch, call
from .Crossovers import crossover

class Individual:
    def __init__(self, genes):
        self._genes = genes

    @property
    def genes(self):
        return self._genes

    @property
    def genes_count(self):
        return len(self._genes)


class TestCrossovers(unittest.TestCase):
    def setUp(self):
        self.a = Individual([0, 1, 2, 3, 4, 5, 6])
        self.b = Individual([10, 11, 12, 13, 14, 15, 16])

    @patch('Crossovers.randint')
    def test_one_point(self, randint):
        randint.return_value = 3
        c, d = crossover('one_point')((self.a, self.b))
        randint.assert_called_once_with(0, self.a.genes_count)
        self.assertEqual([0, 1, 2, 13, 14, 15, 16], c)
        self.assertEqual([10, 11, 12, 3, 4, 5, 6], d)
    
    @patch('Crossovers.randint')
    def test_two_point(self, randint):
        randint.side_effect = [2, 5]
        c, d = crossover('two_point')((self.a, self.b))
        actual_calls = randint.call_args_list
        expected_calls = [call(1, self.a.genes_count - 2), call(3, self.a.genes_count - 1)]
        self.assertEqual(expected_calls, actual_calls)
        self.assertEqual([0, 1, 12, 13, 14, 5, 6], c)
        self.assertEqual([10, 11, 2, 3, 4, 15, 16], d)

    @patch('Crossovers.randint')
    def test_anular_straight(self, randint):
        randint.side_effect = [3, 2]
        c, d = crossover('anular')((self.a, self.b))
        actual_calls = randint.call_args_list
        expected_calls = [call(0, self.a.genes_count - 1), call(1, self.a.genes_count // 2)]
        self.assertEqual(actual_calls, expected_calls)
        self.assertEqual([0, 1, 2, 13, 14, 5, 6], c)
        self.assertEqual([10, 11, 12, 3, 4, 15, 16], d)

    @patch('Crossovers.randint')
    def test_anular_round(self, randint):
        randint.side_effect = [5, 3]
        c, d = crossover('anular')((self.a, self.b))
        actual_calls = randint.call_args_list
        expected_calls = [call(0, self.a.genes_count - 1), call(1, self.a.genes_count // 2)]
        self.assertEqual(actual_calls, expected_calls)
        self.assertEqual([10, 1, 2, 3, 4, 15, 16], c)
        self.assertEqual([0, 11, 12, 13, 14, 5, 6], d)

    @patch('Crossovers.random')
    def test_uniform(self, random):
        random.side_effect = [0.1, 0.9, 0.1, 0.1, 0.9, 0.9, 0.1]
        c, d = crossover('uniform')((self.a, self.b))
        self.assertEqual(self.a.genes_count, random.call_count)
        self.assertEqual([10, 1, 12, 13, 4, 5, 16], c)
        self.assertEqual([0, 11, 2, 3, 14, 15, 6], d)

if __name__ == '__main__':
    unittest.main()
