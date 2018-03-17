%% -*- texinfo -*-
%% @deftypefn {} {} activate (@var{net}, @var{input_pattern})
%% Calculates the output of the network @var{net} given an @var{input_pattern}
%%
%% An input pattern set, i.e. a matrix which each row represents an input pattern,
%% may also be passed and a set of outputs, i.e. matrix in which each row 
%% represents an output will be returned.
%%
%% For example, if calculating the OR function:
%%
%% @example
%% @group
%% activate(net, [1 -1; -1 -1])
%%      @result{} [1; -1]
%% @end group
%% @end example
%% @seealso{@@network/network}
%% @end deftypefn

function output = activate(net, input_pattern)
    layer_output = input_pattern;
    for i = 1:length(net.weights)
        layer_output = activate_layer(net, i, layer_output);
    end
    output = layer_output;
endfunction
