from utils import intersect

class CutConditionController:
    @staticmethod
    def get_fittest(population):
        return max(population, key = lambda p: p.fitness)

    def _generations(self, population):
        self.generations += 1
        return self.generations < self.ctrl_params['max_generations']

    # max fitness does not change significantly (according to content_pct_difference)
    # in max_content_steps subsequent generations
    def _content(self, population):
        max_fit = get_fittest(population)
        if self._last_fittest * (1 + self.ctrl_params['content_pct_difference']) < max_fit:
            self._content_steps = 0
            self._last_fittest = max_fit
        else:
            self._content_steps += 1
            return self._content_steps != self.ctrl_params['max_content_steps']
        return True

    # population does not change significantly (i.e. more than population_change
    # individuals change their genetic structure) in max_structure_steps subsequent generations
    def _structure(self, population):
        intersection = intersect(self._last_population, population)
        if abs(len(population) - len(intersection)) > self.ctrl_params['population_change']:
            self._structure_steps = 0
            self._last_population = population
        else:
            self._structure_steps += 1
            return self._structure_steps != self.ctrl_params['max_structure_steps']
        return True

    def _max_fitness(self, population):
        return get_fittest(population) > self.ctrl_params['target_fitness']

    def should_continue(self, population):
        return self._cut_condition(self, population)

    controllers = {
        'generations': _generations,
        'max_fitness': _max_fitness,
        'content': _content,
        'structure': _structure
    }

    def __init__(self, name, params):
        self.ctrl_params = params
        # for generations
        self.generations = -1

        # for content
        self._content_steps = 0
        self._last_fittest = 0

        # for structure
        self._structure_steps = 0
        self._last_population = []

        self._cut_condition = self.controllers[name]
