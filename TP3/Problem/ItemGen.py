
class ItemGen:
    def __init__(self, type, id):
        self._type = type
        self._id = id
        self._str =...
        self._ag = ...
        self._ex = ...
        self._re = ...
        self._li = ...


    @property
    def str(self):
        return self._str

    @property
    def ag(self):
        return self._ag

    @property
    def ex(self):
        return self._ex

    @property
    def re(self):
        return self._re

    @property
    def li(self):
        return self._li

    @property
    def id(self):
        return self._id

    @property
    def type(self):
        return self._type

    def mutate(self):
        ItemGen.__init__(self, self._type, RANDOM)
