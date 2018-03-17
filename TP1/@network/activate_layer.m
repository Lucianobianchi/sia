%% -*- texinfo -*-
%% @deftypefn {} {} activate_layer (@var{net}, @var{layer}, @var{layer_input})
%% Calculates the output of the @var{layer} an @var{layer_input}.
%%
%% @var{layer} represents the index of the layer.
%%
%% @seealso{@@network/network, @@network/activate}
%% @end deftypefn

function output = activate_layer(net, layer, layer_input)
    weights = net.weights{layer};
    lil = columns(layer_input);
    inl = columns(weights) - 1;
    if (lil != inl)
        error('@network/activate_layer: Layer input length (%d) and input neurons length (%d) do not match', lil, inl);
    end

    bias_column = ones(rows(layer_input), 1) * -1;
    layer_input = [bias_column, layer_input]';
    output = net.f_activation(weights * layer_input)';
endfunction
