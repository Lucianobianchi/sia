%% -*- texinfo -*-
%% @deftypefn {} {@var{net} =} train_n_epochs (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{n}, @var{cb})
%% @deftypefnx {} {[@var{net}, @var{costs}] =} train_n_epochs (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{n}, @var{cb})
%% Adjusts the weights of the network @var{net} given an @var{input_pattern_set}
%% and an @var{expected_set} @var{n} times.
%%
%% The @var{costs}(@var{i}) element corresponds to the cost after each iteration.
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

function [net costs] = train_n_epochs(net, input_pattern_set, expected_set, n, cb)
    r = rows(input_pattern_set);
    l = rows(expected_set);
    if (r != l)
        error('@network/train: Input pattern set rows (%d) do not match expected set rows (%d)', r, l);
    end

    costs = zeros(1, n * rows(input_pattern_set) + 1);
    costs(1) = cost(net, input_pattern_set, expected_set);

    for j = 1:n
        for i = 1:r
            input_pattern = input_pattern_set(i, :);
            expected = expected_set(i, :);
            net = refine(net, input_pattern, expected);
            cost_len = (j - 1) * r + i + 1;
            costs(cost_len) = cost(net, input_pattern_set, expected_set);

            if (exist('cb', 'var'))
                cb(costs, cost_len);
            end
        end
    end

endfunction
