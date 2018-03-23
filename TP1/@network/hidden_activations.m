%% -*- texinfo -*-
%% @deftypefn {} {} hidden_activations (@var{net}, @var{input_pattern})
%% Calculates the output of each hidden layer of the @var{net} given an @var{input_pattern}.
%%
%% @seealso{@@network/network, @@network/activate_layer}
%% @end deftypefn
function output = hidden_activations(net, input_pattern)
    output = cell(1, length(net.weights) - 1); % only hidden layers
    layer_output = input_pattern;
    for i = 1:(length(net.weights) - 1)
        output{i} = activate_layer(net, i, layer_output);
        layer_output = output{i};
    end
endfunction
