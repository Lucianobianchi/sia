%% -*- texinfo -*-
%% @deftypefn {} {@var{net} =} train (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{epochs}, @var{cb})
%% @deftypefnx {} {[@var{net}, @var{costs}] =} train (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{epochs}, @var{cb})
%% Skeleton for training the net. Traning algorithms are supplied by the callback @var{cb}.
%%
%% The callback receives the net and the backpropagation for a given iteration and expects
%% the resulting trained net as output.
%%
%% @seealso{@@network/network, @@network/backpropagation, @@network/cost}
%% @end deftypefn

function [net, costs] = train_skeleton(net, input_pattern_set, expected_set, epochs, cb)
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

            net = cb(net, backprop, input_pattern, expected);

            if (costs_required)
                costs(++costs_len) = cost(net, input_pattern_set, expected_set);
            end
        end
    end
endfunction