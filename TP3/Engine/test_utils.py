import unittest
from utils import choices, between_index
from unittest.mock import patch

class TestUtils(unittest.TestCase):
    @patch('utils.random')
    def test_choices(self, random):
        group = ['A', 'B', 'C', 'D']
        random.side_effect = [0.1343, 0.8474, 0.7637, 0.2550, 0.4954, 0.4494]
        selected = choices(group, [0.005, 0.65, 0.24, 0.105], 3)
        
        self.assertIn('B', selected) # 0.005 < 0.1353 < 0.655
        self.assertIn('C', selected) # 0.655 < 0.8474 < 0.895
        self.assertIn('C', selected) # 0.655 < 0.7637 < 0.895
        self.assertNotIn('A', selected)
        self.assertNotIn('D', selected)
        self.assertEqual(3, len(selected))
        self.assertEqual(3, random.call_count)

    def test_between_index(self):
        group = [1, 2, 3, 4]
        self.assertEqual(0, between_index(group, 0))
        self.assertEqual(1, between_index(group, 1))
        self.assertEqual(1, between_index(group, 1.5))
        self.assertEqual(2, between_index(group, 2))
        self.assertEqual(2, between_index(group, 2.5))
        self.assertEqual(3, between_index(group, 3))
        self.assertEqual(3, between_index(group, 3.5))
        self.assertEqual(4, between_index(group, 4))
        self.assertEqual(4, between_index(group, 4.5))

if __name__ == '__main__':
    unittest.main()
