%% -*- texinfo -*-
%% @deftypefn {} {@var{net} =} train (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{epochs})
%% @deftypefnx {} {[@var{net}, @var{costs}] =} train (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{epochs})
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
%% then the modified net is the return value of the train method:
%%
%% @example
%% [net, costs] = train(net, input_pattern, expected)
%% @end example
%%
%%
%% @seealso{@@network/network, @@network/backpropagation, @@network/cost}
%% @end deftypefn

function [net, costs] = train(net, input_pattern_set, expected_set, epochs)
    r = rows(input_pattern_set);
    l = rows(expected_set);
    if (r != l)
        error('@network/train: Input pattern set rows (%d) do not match expected set rows (%d)', r, l);
    end

    if (!exist('epochs', 'var'))
        epochs = 1;
    end

    if (epochs < 0)
        error('@network/train: Number of epochs (%d) must be non negative', epochs);
    end

    costs_required = nargout == 2;

    if (costs_required)
        costs = zeros(1, epochs * rows(input_pattern_set));
        costs_len = 0;
    end

    for j = 1:epochs
        for i = 1:r
            input_pattern = input_pattern_set(i, :);
            expected = expected_set(i, :);
            backprop = backpropagation(net, input_pattern, expected);

            for j = 1:length(net.weights)
                net.weights{j} = net.weights{j} + net.lr * backprop{j};
            end

            if (costs_required)
                costs(++costs_len) = cost(net, input_pattern_set, expected_set);
            end
        end
    end
endfunction
