from ItemReader import read_items
from random import randint

# TODO: __eq__ y __str__
class ItemGen:
    items = {
        'boot': read_items('botas'),
        'weapon': read_items('armas'),
        'helmet': read_items('cascos'),
        'glove': read_items('guantes'),
        'armor': read_items('pecheras')
    }

    def __init__(self, type, id):
        item = self.items[type][id] # Se podría guardar solo el item en lugar de todos los atributos
        self._type = type
        self._id = id
        self._str = item['str']
        self._agi = item['agi']
        self._dex = item['dex']
        self._res = item['res']
        self._vit = item['vit']

    @property
    def str(self):
        return self._str

    @property
    def agi(self):
        return self._agi

    @property
    def dex(self):
        return self._dex

    @property
    def res(self):
        return self._res

    @property
    def vit(self):
        return self._vit

    @property
    def id(self):
        return self._id

    @property
    def type(self):
        return self._type

    def mutate(self):
<<<<<<< HEAD
        ItemGen.__init__(self, self._type, RANDOM)

    def __repr__(self):
        return '{0} [id: {1}]'.format(self.type, self.id)
=======
        r = self.id
        while r == self.id:
            r = randint(0, len(self.items[self.type]) - 1)
        return ItemGen(self.type, r)
>>>>>>> c4721d1e0c374807806c85c3e0944edb6944c616
