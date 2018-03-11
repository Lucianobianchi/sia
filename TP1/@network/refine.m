%% -*- texinfo -*-
%% @deftypefn {} {} refine (@var{net}, @var{input_pattern}, @var{expected})
%% Adjusts the weights of the network according to the
%% @var{input_pattern} and @var{expected} values given.
%%
%% Note that Octave does not implement pass by reference,
%% then the modified net is the return value of the refine method:
%%
%% @example
%% net = refine(net, input_pattern, expected)
%% @end example
%%
%% @seealso{@@network/network, @@network/train, @@network/cost}
%% @end deftypefn

function net = refine(net, input_pattern, expected)
    l = length(expected);
    r = rows(net.weights);
    if (length(expected) != rows(net.weights))
        error('@network/refine: length of expected values vector (%s) does not match the number of output neurons (%s)', l, r);
    end

    actual = activate(net, input_pattern);
    delta = (expected - actual) .* net.df_activation(actual);
    net.weights = net.weights + net.lr * delta' * [-1 input_pattern];
endfunction
