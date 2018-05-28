from .ItemReader import read_items
from random import randint

items_collection = {
    'boot': read_items('botas'),
    'weapon': read_items('armas'),
    'helmet': read_items('cascos'),
    'glove': read_items('guantes'),
    'armor': read_items('pecheras')
}

def items(type):
    return items_collection[type]

class ItemGen:
    def __init__(self, type, id):
        item = items(type)[id]
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
        r = self.id
        while r == self.id:
            r = randint(0, len(items(self.type)) - 1)
        return ItemGen(self.type, r)

    def __repr__(self):
        return f'{self.type} [id: {self.id}]'

    def __eq__(self, other):
        if isinstance(self, other.__class__):
            return self._id == other._id and self._type == other._type
        return False

    def __hash__(self):
        return hash((self.type, self.id))
