%% -*- texinfo -*-
%% @deftypefn {} {@var{net} =} train_momentum (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{batch_size}, @var{epochs}, @var{alfa})
%% @deftypefnx {} {[@var{net}, @var{costs}] =} train_momentum (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{batch_size}, @var{epochs}, @var{alfa})
%% Adjusts the weights of the network @var{net} given an @var{input_pattern_set}
%% and an @var{expected_set}. Calculates cost after each iteration.
%% 
%% The @var{costs}(@var{i}) element corresponds to the cost after each iteration.
%%
%% The argument @var{input_pattern_set} corresponds to a matrix in which
%% each row defines an input pattern.
%%
%% The argument @var{expected_set} corresponds to a matrix in which each
%% row defines the expected output for the input pattern of that row.
%%
%% The argument @var{batch_size} corresponds to the size of the batch
%% of each training iteration. Use 1 for online training and
%% rows(@var{input_pattern_set}) for batch training. Anything in
%% between correspond to mini-batch training.
%%
%% The argument @var{epochs} corresponds to the number of epochs to train.
%%
%% Note that Octave does not implement pass by reference,
%% then the modified net is the return value of the train_momentum method:
%%
%% @example
%% [net, costs] = train_momentum(net, input_pattern, expected, epochs, alfa)
%% @end example
%%
%%
%% @seealso{@@network/network, @@network/train_skeleton, @@network/backpropagation, @@network/cost}
%% @end deftypefn

function [net, costs] = train_momentum(net, input_pattern_set, expected_set, batch_size, epochs, alfa)
    %% global variables for shairng variables between function since
    %% nested function handles are not yet supported in Octave
    %% desired behaviour would be a closure like approach
    init_globals(net, alfa);
    [net, costs] = train_skeleton(net, input_pattern_set, expected_set, batch_size, epochs, @train_callback);
endfunction

function net = train_callback(net, backprop)
    global prev_delta;
    global g_alfa;
    for k = 1:length(net.weights)
        delta = net.lr * backprop{k};
        net.weights{k} = net.weights{k} + delta + prev_delta{k} * g_alfa;
        prev_delta{k} = net.lr * backprop{k};
    end
endfunction

function init_globals(net, alfa)
    global prev_delta;
    global g_alfa;

    prev_delta = cell(1, length(net.weights));
    g_alfa = alfa;

    for i = 1:length(net.weights)
        prev_delta{i} = zeros(size(net.weights{i}));
    end
endfunction

%!test
%!  net = network(2, 1, [3], 0.1, @tanh, @(gh) (1 - gh .^ 2));
%!  input_pattern_set = [1 1; 1 -1; -1 1; -1 -1];
%!  expected_set = [-1; 1; 1; -1];
%!  net = train_momentum(net, input_pattern_set, expected_set, 1, 500, 0.9);
%!  for i = 1:length(expected_set)
%!      assert(activate(net, input_pattern_set(i, :)), expected_set(i, :), 0.05);
%!  end
%!  assert(cost(net, input_pattern_set, expected_set), 0, 0.001);
