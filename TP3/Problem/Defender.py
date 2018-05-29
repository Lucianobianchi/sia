from .Soldier import Soldier

class Defender(Soldier):
    def __init__(self, genes, attribute_multipliers):
        attribute_multipliers['atk'] = 0.1
        attribute_multipliers['def'] = 0.9
        super().__init__(genes, attribute_multipliers)

defenders = [
    lambda genes: Defender(genes, {'str': 1.0, 'agi': 0.9, 'dex': 0.7, 'res': 1.2, 'vit': 1.1}),
    lambda genes: Defender(genes, {'str': 1.1, 'agi': 0.8, 'dex': 0.8, 'res': 1.1, 'vit': 1.1}),
    lambda genes: Defender(genes, {'str': 0.9, 'agi': 0.9, 'dex': 0.9, 'res': 1.0, 'vit': 1.3})
]

def factory(id):
    return defenders[id - 1]
