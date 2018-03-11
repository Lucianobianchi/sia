%% -*- texinfo -*-
%% @deftypefn {} {} train(@var{net}, @var{input_pattern_set}, @var{expected_set})
%% Adjusts the weights of the network @var{net} given an @var{input_pattern_set}
%% and an @var{expected_set}.
%% 
%% The argument @var{input_pattern_set} corresponds to a matrix in which
%% each row defines an input pattern.
%%
%% The argument @var{expected_set} corresponds to a matrix in which each
%% row defines the expected output for the input pattern of that row.
%%
%% Note that Octave does not implement pass by reference,
%% then the modified net is the return value of the train method:
%%
%% @example
%% net = train(net, input_pattern, expected)
%% @end example
%%
%% @seealso{@@network/network, @@network/refine, @@network/cost}
%% @end deftypefn

function net = train(net, input_pattern_set, expected_set)
    r = rows(input_pattern_set);
    l = rows(expected_set);
    if (r != l)
        error('@network/train: Input pattern set rows (%d) do not match expected set rows (%d)', r, l);
    end

    for i = 1:r
        input_pattern = input_pattern_set(i, :); % ojo con la eficiencia, copia la fila
        expected = expected_set(i, :);
        net = refine(net, input_pattern, expected);
    end
endfunction
