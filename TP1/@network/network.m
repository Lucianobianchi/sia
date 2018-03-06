function net = network(n_inputs, lr)
    % Builds a neural network given the number of inputs, learning rate, 
    % activation function and the derivative of the activation function.
    if (n_inputs < 1)
        error('@network/network: Number of input neurons must be higher than 0. Received %d', n_inputs);
    end

    if (lr < 0 || lr > 1)
        error('@network/network: Learning rate must be between 0 and 1. Received %d', lr);        
    end

    net.f_activation = @sign;
    net.df_activation = @(gh) 1;
    net.weights = rand(1, n_inputs + 1) * 2 - 1;
    net.lr = lr;
    net = class(net, 'network');
endfunction

%!test
%!  net = network(2, 0.5);
%!  input_pattern_set = [1 1; 1 -1; -1 1; -1 -1];
%!  expected_set = [1 1 1 -1];
%!  for i = 1:10
%!      net = train(net, input_pattern_set, expected_set);
%!  end
%!  for i = 1:length(expected_set)
%!      assert(activate(net, input_pattern_set(i, :)), expected_set(i));
%!  end

%!error network();
%!error network(1);
%!error network(0, 0.1);
%!error network(2, 2);