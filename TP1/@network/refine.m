function net = refine(net, input_pattern, expected)
    % Returns a network with the weights adjusted according to 
    % the input_pattern and expected values given
    % input_pattern: vector of values given to the input neurons
    % expected: expected output given the input_pattern
    actual = activate(net, input_pattern);
    delta = (expected - actual) * net.df_activation(actual);
    new_weights = net.weights + net.lr * delta * [-1 input_pattern];
    net.weights = new_weights;
endfunction
