%% -*- texinfo -*-
%% @deftypefn {} {} network (@var{n_inputs}, @var{n_outputs}, @var{lr}, @var{f_activation}, @var{df_activation})
%% @deftypefnx {} {} network (@var{n_inputs}, @var{n_outputs}, @var{lr}, @var{f_activation})
%% Builds a neural network.
%%
%% @var{lr} corresponds to the network learning rate.
%%
%% No argument @var{df_activation} is required for linear or 
%% non continuous functions. 
%%
%% For example, for a network with 3 inputs, 2 outputs, a learning
%% rate of 0.1 and 'tanh' as its activation function:
%%
%% @example
%% @group
%% network(3, 2, 0.1, @@tanh, @@(gh) (1 - gh .^ 2))
%%      @result{} net
%% @end group
%% @end example
%%
%% @seealso{@@network/activate, @@network/refine, @@network/train, @@network/cost}
%% @end deftypefn

function net = network(n_inputs, n_outputs, lr, f_activation, df_activation)
    if (n_inputs < 1)
        error('@network/network: Number of input neurons must be higher than 0. Received %d', n_inputs);
    end

    if (n_outputs < 1)
        error('@network/network: Number of output neurons must be higher than 0. Received %d', n_outputs);
    end

    if (lr <= 0 )
        error('@network/network: Learning rate must be positive. Received %d', lr);
    end

    if (!exist('df_activation', 'var'))
        df_activation = @(gh) 1;
    end

    net.f_activation = f_activation;
    net.df_activation = df_activation;
    net.weights = rand(n_outputs, n_inputs + 1) * 2 - 1;
    net.lr = lr;
    net = class(net, 'network');
endfunction

%!test
%!  net = network(3, 2, 0.5, @sign);
%!  input_pattern_set = [1 1 1; 1 -1 1; -1 1 1; 1 1 -1; 1 -1 -1; -1 1 -1; -1 -1 1; -1 -1 -1];
%!  expected_set = [1 1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; 1 -1; -1 -1];
%!  for i = 1:20
%!      net = train(net, input_pattern_set, expected_set);
%!  end
%!  for i = 1:length(expected_set)
%!      assert(activate(net, input_pattern_set(i, :)), expected_set(i, :));
%!  end
%!  assert(cost(net, input_pattern_set, expected_set), 0);

%!error network();
%!error network(1);
%!error network(0, 0.1);
%!error network(2, 2);
