%% -*- texinfo -*-
%% @deftypefn {} {} backpropagation (@var{net}, @var{input_pattern}, @var{expected})
%% Adjusts the weights of the network according to the
%% @var{input_pattern} and @var{expected} values given.
%%
%% @example
%% @group
%% backpropagation(net, input_pattern, expected)
%%      @result{} backprop
%% @end group
%% @end example
%%
%% @seealso{@@network/network, @@network/train, @@network/cost}
%% @end deftypefn

function backprop = backpropagation(net, input_pattern, expected)
    l = length(expected);
    r = rows(net.weights{end});

    if (length(expected) != rows(net.weights{end}))
        error('@network/backpropagation: length of expected values vector (%s) does not match the number of output neurons (%s)', l, r);
    end

    outputs = layer_outputs(net, input_pattern);
    deltas = layer_deltas(net, outputs, input_pattern, expected);

    backprop = cell(1, length(net.weights));

    backprop{1} = deltas{1}' * [-1 input_pattern];

    for i = 2:length(deltas)
        backprop{i} = deltas{i}' * [-1 outputs{i-1}];
    end
endfunction

function outputs = layer_outputs(net, input_pattern)
    layer_output = input_pattern;
    outputs = cell(1, length(net.weights));
    for i = 1:length(net.weights)
        layer_output = activate_layer(net, i, layer_output);
        outputs{i} = layer_output;
    end
endfunction

function deltas = layer_deltas(net, outputs, input_pattern, expected)
    actual = outputs{end};
    output_delta = (expected - actual) .* net.df_activation(actual);

    deltas = cell(1, length(net.weights));
    deltas{end} = output_delta;

    for i = length(net.weights):-1:2
        weights = net.weights{i}(:, 2:end); % exclude bias weights
        deltas{i-1} = (deltas{i} * weights) .* net.df_activation(outputs{i-1});
    end
endfunction