from .Soldier import Soldier

class Archer(Soldier):
    def __init__(self, genes, attribute_multipliers):
        attribute_multipliers['atk'] = 0.9
        attribute_multipliers['def'] = 0.1
        super().__init__(genes, attribute_multipliers)

archers = [
    lambda genes: Archer(genes, {'str': 0.8, 'agi': 1.1, 'dex': 1.1, 'res': 0.9, 'vit': 0.7}),
    lambda genes: Archer(genes, {'str': 0.9, 'agi': 1.1, 'dex': 1.0, 'res': 0.9, 'vit': 0.8}),
    lambda genes: Archer(genes, {'str': 0.8, 'agi': 0.8, 'dex': 0.8, 'res': 1.1, 'vit': 1.2})
]

def factory(id):
    return archers[id - 1]
