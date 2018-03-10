function net = refine(net, input_pattern, expected)
    % Returns a network with the weights adjusted according to 
    % the input_pattern and expected values given
    % input_pattern: vector of values given to the input neurons
    % expected: expected output given the input_pattern

    if (length(expected) != rows(net.weights))
        error('@network/refine: length of expected values vector does not match the number of output neurons')
    end

    actual = activate(net, input_pattern);
    delta = (expected - actual) .* net.df_activation(actual);
    net.weights = net.weights + net.lr * delta' * [-1 input_pattern];
endfunction
