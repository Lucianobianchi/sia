function net = build_network(n_inputs, lr, f_activation_name, slope)
    slope
    switch (f_activation_name)
    case 'step'
        net = network(n_inputs, lr, @sign, @(gh) 1);
    case 'linear'
        net = network(n_inputs, lr, @(h) slope * h, @(gh) 1);
    case 'tanh'
        net = network(n_inputs, lr, @(h) tanh(slope * h), @(gh) slope * (1 - gh^2));
    case 'sigmoid'
        net = network(n_inputs, lr, @(h) 1 / (1 + exp(-2 * slope * h)), @(gh) 2 * slope * gh * (1 - gh));
    otherwise
        error('build_network: Unsupported activation function %s', f_activation_name);
    endswitch
endfunction

%!test
%!  net = build_network(2, 0.5, 'tanh', 1);
%!  input_pattern_set = [1 1; 1 -1; -1 1; -1 -1];
%!  expected_set = [1 1 1 -1];
%!  for i = 1:120
%!      net = train(net, input_pattern_set, expected_set);
%!  end
%!  for i = 1:length(expected_set)
%!      assert(activate(net, input_pattern_set(i, :)), expected_set(i), 0.05);
%!  end
