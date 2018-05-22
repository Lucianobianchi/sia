class ContinueController:
    controllers = {
        'generations': _generations
        'max_fitness': _max_fitness
        # 'content:' _content
        # 'structure': _structure
    }

    def __init__(self, name, **kwargs):
        self.generations = -1
        # self.last_population = []
        # self.last_max_fittest = -1
        # self.content_compare = 0
        self.ctrl_params = kwargs
        self._cut_condition = controllers[name]

    def _generations(self, population):
        self.generations += 1
        return self.generations < self.ctrl_params['max_generations']

    # max fitness does not change significantly in k subsequent generations
    # def _content(self, population):

    # population does not change significantly in k subsequent generations
    # def _structure(self, population):

    def _max_fitness(self, population):
        max_fit = max([ i.fitness for i in population ])
        return max_fit > self.ctrl_params['target_fitness']

    def should_continue(self, population):
        return self._cut_condition(population)
