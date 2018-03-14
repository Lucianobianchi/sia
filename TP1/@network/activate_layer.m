function output = activate_layer(net, layer, layer_input)
    weights = net.weights{layer};
    lil = columns(layer_input);
    inl = columns(weights) - 1;
    if (lil != inl)
        error('@network/activate_layer: Layer input length (%d) and input neurons length (%d) do not match', lil, inl);
    end

    layer_input = [ones(rows(layer_input), 1) * -1, layer_input]';
    output = net.f_activation(weights * layer_input)';
endfunction
