# sia

# TP3

Archer3

## Requirements

Python3.6 is required to run the project.
* [Ubuntu installation](https://askubuntu.com/questions/865554/how-do-i-install-python-3-6-using-apt-get)
* [Windows and MAC OS X installation](https://www.python.org/downloads/release/python-365/)

Matplotlib dependency is required as well. Installation:

```
pip3 install -r requirements.txt
```

If pip3 fails try with pip3.6 or `python3.6 -m pip install -r requirements.txt`. Finally, if an error of python3-tk package if missing occurs, execute `python3.6 -m pip install python3.6-tk`.

## Items

Inside the Items directory the test (10 items) items may be found. They may be replaced by the fulldata items data set or any other desired.

## How to run

Set the desired params in `config.json` and:

```
python3 main.py
```

If python3 fails, try with python or python3.6 depending on your installation. When the search finishes it is possible to edit `config.json` and run the search again with the new params, avoiding having to load the items all over again.

## Config

* **soldier**: (warrior1 \| warrior2 \| warrior3 \| archer1 \| ...)
* **N**: amount of individuals per generation
* **selectors**: array which corresponds to selector methods 1 and 2 for the selection phase, and selector method 3 and 4 for the replacement phase.
Selection methods: (elite \| random \| roulette \| universal \| boltzmann \| tournament_det \| tournament_prob \| ranking)
* **A**: percentage (0.0 <= x <= 1.0) of individuals selected by method 1. Method 2 percentage would be (1 - A).
* **B**: percentage of individuals selected by method 3. Method 4 percentage would be (1 - B).
* **select\_params.m**: tournament\_det m parameter for the selection phase.
* **replace\_params.m**: tournament\_det m parameter for the replacement phase.
* **pairs**: (random \| roulette)
* **crossover**: (one_point \| two_point \| anular \| uniform)
* **cross\_prob**: probability of crossing each selected pair
* **replacer**: (select_parents \| select_total). Note select\_parents corresponds to replacement method 2 and select\_total to method 3. If generation gap is 1.0, method 2 would behave as method 1.
* **mutate\_prob**: mutation probability. If the mutation probability is NOT uniform, this corresponds to the initial mutation probability.
* **uniform\_mutate**: (true \| false). If true mutation probability does not change.
* **generation\_gap**: generation gap.
* **should\_continue**: cut condition. (generations \| target_fitness \| content \| structure)
* **cut\_conditions**: params for each cut condition.
* **max\_generations**: amount of generations to execute for cut condition generations.
* **target\_fitness**: fitness to achieve for cut condition target_fitness.
* **content\_pct\_difference**: percentage max fitness that must change before max\_content\_steps generations for cut condition content.
* **max\_content\_steps**: amount of generations max fitness may not change before content cut condition becomes true.
* **population\_pct\_change**: percentage of population that must change for max\_structure\_steps generations for cut condition structure.
* **max\_structure\_steps**: amount of generations population may not change before structure cut condition becomes true.
* **seed**: random seed.
* **realtime**: plot realtime (true \| false)
* **refresher**: period of generations for plotting if plotting realtime true.

## Code Organization

Inside the Engine directory implementations for each stage of the algorithm may be found and the core flow may be found in `Engine/GeneticAlgorithm.py`.

Inside the Problem directory implementations for each soldier may be found. The core class corresponds to `Soldier.py` which receives the multipliers corresponding to each soldier type. Important to notice that the mutate functions from `ItemGen.py` and `HeightGen.py` are immutable.
