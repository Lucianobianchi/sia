from .Soldier import Soldier

class Assassin(Soldier):
    def __init__(self, genes, attribute_multipliers):
        attribute_multipliers['atk'] = 0.7
        attribute_multipliers['def'] = 0.3
        super().__init__(genes, attribute_multipliers)

assassins = [
    lambda genes: Assassin(genes, {'str': 0.8, 'agi': 1.2, 'dex': 1.1, 'res': 1.0, 'vit': 0.8}),
    lambda genes: Assassin(genes, {'str': 0.9, 'agi': 1.0, 'dex': 1.1, 'res': 1.0, 'vit': 0.9}),
    lambda genes: Assassin(genes, {'str': 0.9, 'agi': 0.9, 'dex': 1.0, 'res': 1.1, 'vit': 1.0})
]

def factory(id):
    return assassins[id - 1]
