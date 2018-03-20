%% -*- texinfo -*-
%% @deftypefn {} {} build_network (@var{n_inputs}, @var{n_outputs}, @var{hidden_layers}, @var{lr}, @var{f_activation_name}, @var{slope})
%% @deftypefnx {} {} build_network (@var{n_inputs}, @var{n_outputs}, @var{hidden_layers}, @var{lr}, 'step')
%% Builds a neural network.
%%
%% @var{hidden_layers} is represented by a vector in which each
%% value represents the amount of neurons in each layer.
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

function net = build_network(n_inputs, n_outputs, hidden_layers, weight_init, lr, f_activation_name, slope)
    if (strcmp(f_activation_name, 'step') != 1 && !exist('slope', 'var'))
        error('build_network: Missing slope input variable for the activation function "%s"', f_activation_name);
    end

    switch (f_activation_name)
        case 'step'
            f_activation = @sign;
            df_activation = @(gh) 1;
        case 'linear'
            f_activation = @(h) slope * h;
            df_activation = @(gh) 1;
        case 'tanh'
            f_activation = @(h) tanh(slope * h);
            df_activation = @(gh) slope * (1 - gh .^ 2);
        case 'logistic'
            f_activation = @(h) 1 ./ (1 + exp(-2 * slope * h));
            df_activation = @(gh) 2 * slope * gh .* (1 - gh);
        otherwise
            error('build_network: Unsupported activation function %s', f_activation_name);
    endswitch

    switch (weight_init)
        case 'uniform_one'
            w_init = @(n_in, n_out) unifrnd(-1, 1, [n_in n_out]);
        case 'uniform'
            w_init = @(n_in, n_out) unifrnd(-1/sqrt(n_in), 1/sqrt(n_in), [n_in n_out]);
        case 'xavier_uniform'
            w_init = @(n_in, n_out) unifrnd(-sqrt(6)*4/sqrt(n_in + n_out), sqrt(6)*4/sqrt(n_in + n_out), [n_in n_out]);
        case 'xavier'
            w_init = @(n_in, n_out) normrnd(0, 2/n_in + n_out, [n_in n_out]);
        case 'zero'
            w_init = @(n_in, n_out) zeros(n_in, n_out);
        otherwise
            error('build_network: Unsupported initialization function %s', weight_init);
    endswitch

    net = network(n_inputs, n_outputs, hidden_layers, w_init, lr, f_activation, df_activation);
endfunction

%!test
%!  net = build_network(3, 2, [2], 0.5, 'tanh', 1);
%!  input_pattern_set = [1 1 1; 1 -1 1; -1 1 1; 1 1 -1; 1 -1 -1; -1 1 -1; -1 -1 1; -1 -1 -1];
%!  expected_set = [1 1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; -1 -1];
%!  net = train(net, input_pattern_set, expected_set, 2, 300);
%!  for i = 1:length(expected_set)
%!      assert(activate(net, input_pattern_set(i, :)), expected_set(i, :), 0.05);
%!  end

%!test
%!  net = build_network(3, 2, [], 0.5, 'step');
%!  input_pattern_set = [1 1 1; 1 -1 1; -1 1 1; 1 1 -1; 1 -1 -1; -1 1 -1; -1 -1 1; -1 -1 -1];
%!  expected_set = [1 1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; -1 -1];
%!  net = train(net, input_pattern_set, expected_set, 1, 15);
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
