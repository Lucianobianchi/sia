# sia

# TP2

## How to run

```
java -jar TP2/search/search.jar algorithm [heuristic]
```

Possible values for algorithm:

* **DFS**
* **BFS**
* **Uniform**
* **IDFS**
* **AStar**
* **Greedy**

In case AStar or Greedy was chosen an heuristic must be specified:

* **Ha** Counts the remaining pegs left minus one
* **Hb** Counts the number of isolated pegs
* **Hc** Assigns a value of removal difficulty to each occupied position
* **Hd** Sums the distance between each peg
* **He** Sums the distance of each peg to the center of the board

## Example usage

In order to run DFS:

```
java -jar TP2/search/search.jar DFS
```

In order to run AStar with Hc heuristic:

```
java -jar TP2/search/search.jar Astar Hc
```

## Code organization

All the classes related to the search engine may be found inside the *engine* package.

All the classes related to the senku problem may be found inside the *problem* package.

## Experimenting
If you wish to experiment with the source code an example of a main class may be found inside the *main* package under the name **GenericMain**. Different board configurations may be loaded and found inside the *board* directory. Keep in mind that the *PegsDifficulty* heuristic is designed to work only under the *board4* configuration.
