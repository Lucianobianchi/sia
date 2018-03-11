%% -*- texinfo -*-
%% @deftypefn {} {} cost (@var{net}, @var{input_pattern_set}, @var{expected_set})
%% Calculates the cost of the network @var{net} given an @var{input_pattern_set}
%% and an @var{expected_set}.
%% 
%% The argument @var{input_pattern_set} corresponds to a matrix in which
%% each row defines an input pattern.
%%
%% The argument @var{expected_set} corresponds to a matrix in which each
%% row defines the expected output for the input pattern of that row.
%%
%% @seealso{@@network/network, @@network/train}
%% @end deftypefn

function err = cost(net, input_pattern_set, expected_set)
    r = rows(input_pattern_set);
    l = rows(expected_set);
    if (r != l)
        error('@network/cost: Input pattern set rows (%d) do not match expected set rows (%d)', r, l);
    end

    distance_set = zeros(1, rows(expected_set));

    for i = 1:r
        input_pattern = input_pattern_set(i, :);
        expected = expected_set(i, :);
        actual = activate(net, input_pattern);
        distance_set(i) = norm(expected - actual);
    end

    err = 1/(2*r) * sum(distance_set .^ 2);
endfunction
