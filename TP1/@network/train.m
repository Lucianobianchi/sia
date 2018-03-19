%% -*- texinfo -*-
%% @deftypefn {} {@var{net} =} train (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{batch_size}, @var{epochs})
%% @deftypefnx {} {[@var{net}, @var{costs}] =} train (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{batch_size}, @var{epochs})
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
%% then the modified net is the return value of the train method:
%%
%% @example
%% [net, costs] = train(net, input_pattern, expected)
%% @end example
%%
%%
%% @seealso{@@network/network, @@network/train_skeleton, @@network/backpropagation, @@network/cost}
%% @end deftypefn

function [net, costs] = train(net, input_pattern_set, expected_set, batch_size, epochs)
    [net, costs] = train_skeleton(net, input_pattern_set, expected_set, batch_size, epochs, @train_callback);
endfunction

function net = train_callback(net, backprop)
    for i = 1:length(net.weights)
        net.weights{i} = net.weights{i} + net.lr * backprop{i};
    end
endfunction
