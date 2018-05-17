class HeightGen:
    upper_bound = 2.0
    lower_bound = 1.3

    def __init__(self, h):
        if h < lower_bound or h > upper_bound:
            raise ValueError(f'Height should be in [1.3, 2.0]. Received {h}')
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
        #TODO