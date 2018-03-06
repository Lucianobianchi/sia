function net = refine(net, input_pattern, expected)
    actual = activate(net, input_pattern);
    delta = (expected - actual) * net.df_activation(actual);
    new_weights = net.weights + net.lr * delta * [-1 input_pattern];
    net.weights = new_weights;
endfunction
