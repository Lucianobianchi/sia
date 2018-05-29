from .Soldier import Soldier

class Warrior(Soldier):
    def __init__(self, genes, attribute_multipliers):
        attribute_multipliers['atk'] = 0.6
        attribute_multipliers['def'] = 0.4
        super().__init__(genes, attribute_multipliers)

warriors = [
    lambda genes: Warrior(genes, {'str': 1.1, 'agi': 0.9, 'dex': 0.8, 'res': 1.0, 'vit': 0.9}),
    lambda genes: Warrior(genes, {'str': 1.2, 'agi': 1.0, 'dex': 0.8, 'res': 0.8, 'vit': 0.8}),
    lambda genes: Warrior(genes, {'str': 0.8, 'agi': 0.9, 'dex': 0.9, 'res': 1.2, 'vit': 1.1})
]

def factory(id):
    return warriors[id - 1]
