%% -*- texinfo -*-
%% @deftypefn {} {@var{net} =} train_n_epochs(@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{n})
%% @deftypefnx {} {[@var{net}, @var{costs}] =} train_n_epochs(@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{n})
%% Adjusts the weights of the network @var{net} given an @var{input_pattern_set}
%% and an @var{expected_set} @var{n} times.
%%
%% The @var{costs}(@var{i}) element corresponds to the cost after @var{i-1} epochs.
%%
%% The argument @var{input_pattern_set} corresponds to a matrix in which
%% each row defines an input pattern.
%%
%% The argument @var{expected_set} corresponds to a matrix in which each
%% row defines the expected output for the input pattern of that row.
%%
%% Note that Octave does not implement pass by reference,
%% then the modified net is the return value of the train_n_epochs method:
%%
%% @example
%% [net, costs] = train_n_epochs(net, input_pattern, expected)
%% @end example
%%
%% @seealso{@@network/network, @@network/train, @@network/cost}
%% @end deftypefn

function [net costs] = train_n_epochs(net, input_training_set, expected_set, n)
    costs = zeros(1, n + 1);
    for i = 1:n
        costs(i) = cost(net, input_training_set, expected_set);
        net = train(net, input_training_set, expected_set);
    end
    costs(end) = cost(net, input_training_set, expected_set);
endfunction
