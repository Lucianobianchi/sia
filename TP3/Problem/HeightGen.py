from random import uniform

class HeightGen:
    lower_bound = 1.3
    upper_bound = 2.0
    epsilon = 0.01

    def __init__(self, h):
        if h < self.lower_bound or h > self.upper_bound:
            raise ValueError(f'Height should be in [{self.lower_bound}, {self.upper_bound}]. Received {h}')
        self._h = h
        self._atm = 0.5 - (3*h - 5)**4 + (3*h - 5)**2 + h/2
        self._dem = 2 +(3*h - 5)**4 -(3*h -5)**2 - h/2

    @property
    def h(self):
        return self._h

    @property
    def atm(self):
        return self._atm

    @property
    def dem(self):
        return self._dem

    def mutate(self):
        return HeightGen(uniform(self.lower_bound, self.upper_bound))

    def __repr__(self):
        return 'Height: {0}'.format(self.h)

    def __eq__(self, other):
        if isinstance(self, other.__class__):
            return abs(elf._h - other._h) < self.epsilon
        return False

    def __hash__(self):
        return hash(self._h)
