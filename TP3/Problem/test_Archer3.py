import unittest
from random import randint, uniform
from unittest.mock import patch, call
from .Archer3 import Archer3
from .ItemGen import ItemGen
from .HeightGen import HeightGen

class TestSelectors(unittest.TestCase):
    def setUp(self):
        self.right_genes = [
            ItemGen('boot', randint(0, 9)),
            ItemGen('weapon', randint(0, 9)),
            ItemGen('helmet', randint(0, 9)),
            ItemGen('glove', randint(0, 9)),
            ItemGen('armor', randint(0, 9)),
            HeightGen(uniform(1.3, 2.0))
        ]

    def test_create(self):
        archer = Archer3(self.right_genes)
        self.assertEqual(self.right_genes, archer.genes)

    def test_wrong_length(self):
        long_genes = self.right_genes + [HeightGen(uniform(1.3, 2.0))]
        with self.assertRaises(ValueError):
            archer = Archer3(long_genes)

    def test_all_items_genes(self):
        allItems = [
            ItemGen('boot', randint(0, 9)),
            ItemGen('weapon', randint(0, 9)),
            ItemGen('helmet', randint(0, 9)),
            ItemGen('glove', randint(0, 9)),
            ItemGen('armor', randint(0, 9)),
            ItemGen('boot', randint(0, 9))
        ]

        with self.assertRaises(TypeError):
            archer = Archer3(allItems)

    def test_two_height_genes(self):
        two_heights = [
            ItemGen('boot', randint(0, 9)),
            ItemGen('weapon', randint(0, 9)),
            ItemGen('helmet', randint(0, 9)),
            ItemGen('glove', randint(0, 9)),
            HeightGen(uniform(1.3, 2.0)),
            HeightGen(uniform(1.3, 2.0))
        ]

        with self.assertRaises(TypeError):
            archer = Archer3(two_heights)

    def test_wrong_order_genes(self):
        wrong_order = [
            ItemGen('boot', randint(0, 9)),
            ItemGen('weapon', randint(0, 9)),
            ItemGen('helmet', randint(0, 9)),
            ItemGen('glove', randint(0, 9)),
            HeightGen(uniform(1.3, 2.0)),
            ItemGen('armor', randint(0, 9))
        ]

        with self.assertRaises(TypeError):
            archer = Archer3(wrong_order)

    def test_fitness(self):
        genes1 = [
            ItemGen('boot', 0),
            ItemGen('weapon', 1),
            ItemGen('helmet', 2),
            ItemGen('glove', 3),
            ItemGen('armor', 4),
            HeightGen(1.55)
        ]
        genes2 = [
            ItemGen('boot', 4),
            ItemGen('weapon', 1),
            ItemGen('helmet', 2),
            ItemGen('glove', 3),
            ItemGen('armor', 4),
            HeightGen(1.55)
        ]
        archer1 = Archer3(genes1)
        archer2 = Archer3(genes2)
        self.assertEqual(10.826640354580046, archer1.fitness)
        self.assertEqual(10.90838559690097, archer2.fitness)

if __name__ == '__main__':
    unittest.main()
