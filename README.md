# sia

# TP1

1. Set configuration variables in `config.m`
2. Run bash script: `./executable.sh`

## Code organization
All neural network related code is inside the @network directory, commented and organized in compliance with 
Octave classes architecture. For more information about see [Octave Classes](https://octave.org/doc/v4.0.3/Creating-a-Class.html).

Usual sequence of function calls are:

```
net = network(<params>);
[net costs] = train(net, input_pattern_set, expected_set, batch_size, epochs);
results = activate(net, test_set);
```

Note that Octave does not implement pass by reference, so the net variable must be set in each call to train
or any other call to a function that modifies the net.

If any parameter of the net is required, such as the weights, run `get(net, '<param_name>')`. For example,
`get(net, 'weights')`, would return the cell_array corresponding to the values of the weights. 

It is strongly recommended to run the help command in order to understand the interface of each function inside
@network. Note that since the scripts are inside a directory, the help command must be run like so:
`help '@network/<script_name>`. Help yourself with TAB completion.

Finally, although the `build_network.m` function is not part of the net itself, it provides a very handy way for
building a neural network (as you may have guessed). See `help build_network` for further information.

## Experimenting
If you wish to experiment with other scripts rather than `executable.sh`, the following steps should be description
enough for the purpose of the other executable scripts.

1. Set configuration variables in `config.m`
2. Initialize variables: `config`
3. Initialize net and terrain data: `init_script`
4. Run desired trainig script:

  * Choose `train_script` if you wish to train for `epochs` times as defined in `config.m`
  * Choose `train_until_error` if you wish to train until a certain error or net saturation criteria is met 
as defined in the last section `Train Test config` of `config.m`

5. (Optional) Plot the terrain: `plot_script`

## Final remarks
The code was designed with the ambition to parametrize the higher amount of variables as possible.
Future implementations should allow further parametrizations such as the activation function used in each layer.
