import matplotlib.pyplot as plt
from statistics import mean, median

class MetricManager:
    stats = ('Str', 'Agi (x100)', 'Dex(x100)', 'Res(x100)', 'Vit')
    y_pos = range(0, len(stats))

    def __init__(self, realtime, refresher = 500):
        self._fitnesses = []
        self._max = []
        self._avg = []
        self._min = []
        self._median = []
        self._avg_stats = []

        self._realtime = realtime
        self._refresh_counter = 1
        self._refresh_step = refresher

        if realtime:
            plt.ion()
            plt.show(block = True)

    @staticmethod
    def stats_list(population):
        max_ind = max(population, key=lambda i: i.fitness)
        # return [max_ind.strength, 100 * max_ind.agility, 100 * max_ind.dexterity,
        #     100 * max_ind.resistance, max_ind.vitality]
        return [
            mean([i.strength for i in population]),
            100 * mean([i.agility for i in population]),
            100 * mean([i.dexterity for i in population]),
            100 * mean([i.resistance for i in population]),
            mean([i.vitality for i in population])
        ]

    def update(self, population):
        fitnesses = [x.fitness for x in population]
        self._fitnesses.append(fitnesses)
        self._max.append(max(fitnesses))
        self._min.append(min(fitnesses))
        self._avg.append(mean(fitnesses))
        self._median.append(median(fitnesses))
        self._avg_stats = self.stats_list(population)

        if self._realtime and self._refresh_counter % self._refresh_step == 0:
            self.plot()

        self._refresh_counter += 1


    def plot(self, limits = None):
        if limits is None:
            limits = (0 ,len(self._max))
        plt.subplot(221)
        plt.plot(self._max[limits[0]:limits[1]])
        plt.title('Max')

        # plt.subplot(222)
        # plt.plot(self._min[limits[0]:limits[1]])
        # plt.title('Min')

        plt.subplot(222)
        plt.plot(self._avg[limits[0]:limits[1]])
        plt.title('Mean')

        plt.subplot(223)
        plt.plot(self._median[limits[0]:limits[1]])
        plt.title('Median')

        plt.subplot(224)
        plt.cla() # clear old bar plot
        plt.bar(self.y_pos, self._avg_stats, align = 'center', alpha = 0.5)
        plt.xticks(self.y_pos, self.stats)
        plt.title('Mean Stats')

        plt.draw()
        plt.pause(0.000001)

    @property
    def medians(self):
        return list(self._median)

    @property
    def maximums(self):
        return list(self._max)

    @property
    def minimums(self):
        return list(self._min)

    @property
    def means(self):
        return list(self._avg)

    @property
    def generations(self):
        return len(self.means) - 1

    def generation_data(self, generation):
        return {
            'fitnesses': self._fitnesses[generation],
            'max': self._max[generation],
            'min': self._min[generation],
            'median': self._median[generation],
            'mean': self._avg[generation]
        }

# Static functions for metrics
    def boxplots(self, generations):
        _, axs = plt.subplots(1, len(generations), sharey=True)
        print(axs)
        for i in range(len(generations)):
            axs[i].boxplot(self.generation_data(i)['fitnesses'])
            axs[i].set_title(f'{generations[i]}')
        plt.pause(0.0000000001)
        plt.draw()
