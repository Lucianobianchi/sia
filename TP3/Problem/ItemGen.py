
class ItemGen:
    def __init__(self, type, id):
        self._type = type
        self._id = id
        # self._str =...
        # self._ag = ...
        # self._ex = ...
        # self._re = ...
        # self._li = ...

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

    # TODO: no puede ser mutable GEN porque se comparten instancias de genes.
    # Mutate debe devolver una nueva instancia del gen.
    def mutate(self):
        ItemGen.__init__(self, self._type, RANDOM)

    def __repr__(self):
        return '{0} [id: {1}]'.format(self.type, self.id)
