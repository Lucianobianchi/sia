%% -*- texinfo -*-
%% @deftypefn {} {} build_network(@var{n_inputs}, @var{n_outputs}, @var{lr}, @var{f_activation_name}, @var{slope})
%% @deftypefnx {} {} build_network(@var{n_inputs}, @var{n_outputs}, @var{lr}, 'step')
%% Builds a neural network.
%%
%% @var{lr} corresponds to the network learning rate.
%%
%% Supported @var{f_activation_name} are: 'step', 'linear', 'tanh' and 'logistic'.
%%
%% No @var{slope} is required for the 'step' function.
%% 
%% For example, for a network with 3 inputs, 2 outputs, a learning
%% rate of 0.1 and 'tanh' as its activation function with slope 0.5:
%%
%% @example
%% @group
%% build_network(3, 2, 0.1, 'tanh', 0.5)
%%      @result{} net
%% @end group
%% @end example
%%
%% @seealso{@@network/network}
%% @end deftypefn

function net = build_network(n_inputs, n_outputs, lr, f_activation_name, slope)
    if (f_activation_name != 'step' && !exist('slope', 'var'))
        error('build_network: Missing slope input variable for the activation function "%s"', f_activation_name);
    end

    switch (f_activation_name)
    case 'step'
        net = network(n_inputs, n_outputs, lr, @sign);
    case 'linear'
        net = network(n_inputs, n_outputs, lr, @(h) slope * h);
    case 'tanh'
        net = network(n_inputs, n_outputs, lr, @(h) tanh(slope * h), @(gh) slope * (1 - gh .^ 2));
    case 'logistic'
        net = network(n_inputs, n_outputs, lr, @(h) 1 ./ (1 + exp(-2 * slope * h)), @(gh) 2 * slope * gh .* (1 - gh));
    otherwise
        error('build_network: Unsupported activation function %s', f_activation_name);
    endswitch
endfunction

%!test
%!  net = build_network(3, 2, 0.5, 'tanh', 1);
%!  input_pattern_set = [1 1 1; 1 -1 1; -1 1 1; 1 1 -1; 1 -1 -1; -1 1 -1; -1 -1 1; -1 -1 -1];
%!  expected_set = [1 1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; -1 -1];
%!  for i = 1:300
%!      net = train(net, input_pattern_set, expected_set);
%!  end
%!  for i = 1:length(expected_set)
%!      assert(activate(net, input_pattern_set(i, :)), expected_set(i, :), 0.05);
%!  end

%!test
%!  net = build_network(3, 2, 0.5, 'step');
%!  input_pattern_set = [1 1 1; 1 -1 1; -1 1 1; 1 1 -1; 1 -1 -1; -1 1 -1; -1 -1 1; -1 -1 -1];
%!  expected_set = [1 1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; -1 -1];
%!  for i = 1:15
%!      net = train(net, input_pattern_set, expected_set);
%!  end
%!  for i = 1:length(expected_set)
%!      assert(activate(net, input_pattern_set(i, :)), expected_set(i, :));
%!  end
%!  assert(cost(net, input_pattern_set, expected_set), 0);

%!error build_network();
%!error build_network(1);
%!error build_network(0, 0.1);
%!error build_network(2, 2);
%!error build_network(2, 2, 0.5, 'tanh');
%!error build_network(2, 2, 0.5, 'does not exist', 2);
