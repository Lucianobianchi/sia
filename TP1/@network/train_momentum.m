%% -*- texinfo -*-
%% @deftypefn {} {@var{net} =} train_momentum (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{epochs}, @var{alfa})
%% @deftypefnx {} {[@var{net}, @var{costs}] =} train_momentum (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{epochs}, @var{alfa})
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
%% The argument @var{cb} corresponds to an optional callback which is 
%% invoked after each iteration with the @var{net} as argument. 
%%
%% The argument @var{epochs} corresponds to the number of epochs to train.
%%
%% Note that Octave does not implement pass by reference,
%% then the modified net is the return value of the train_momentum method:
%%
%% @example
%% [net, costs] = train_momentum(net, input_pattern, expected)
%% @end example
%%
%%
%% @seealso{@@network/network, @@network/train_skeleton, @@network/backpropagation, @@network/cost}
%% @end deftypefn

function [net, costs] = train_momentum(net, input_pattern_set, expected_set, epochs, alfa)
    % global variable since nested function handles are not yet supported
    % desired behaviour would be a closure like approach
    global prev_delta = cell(1, length(net.weights));
    global global_alfa = alfa;

    for i = 1:length(net.weights)
        prev_delta{i} = zeros(size(net.weights{i}));
    end

    [net, costs] = train_skeleton(net, input_pattern_set, expected_set, epochs, @train_callback);
endfunction

function net = train_callback(net, backprop)
    global prev_delta;
    global global_alfa;
    for k = 1:length(net.weights)
        delta = net.lr * backprop{k};
        net.weights{k} = net.weights{k} + delta + prev_delta{k} * global_alfa;
        prev_delta{k} = net.lr * backprop{k};
    end
endfunction

%!test
%!  net = network(2, 1, [3], 0.1, @tanh, @(gh) (1 - gh .^ 2));
%!  input_pattern_set = [1 1; 1 -1; -1 1; -1 -1];
%!  expected_set = [-1; 1; 1; -1];
%!  net = train_momentum(net, input_pattern_set, expected_set, 500, 0.9);
%!  for i = 1:length(expected_set)
%!      assert(activate(net, input_pattern_set(i, :)), expected_set(i, :), 0.05);
%!  end
%!  assert(cost(net, input_pattern_set, expected_set), 0, 0.001);
