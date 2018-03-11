%% -*- texinfo -*-
%% @deftypefn {} {} activate (@var{net}, @var{input_pattern})
%% Calculates the output of the network @var{net} given an @var{input_pattern}
%%
%% For example, if calculating the OR function:
%%
%% @example
%% @group
%% activate(net, [1 -1])
%%      @result{} 1
%% @end group
%% @end example
%% @seealso{@@network/network}
%% @end deftypefn

function output = activate(net, input_pattern)
    ipl = length(input_pattern);
    inl = length(net.weights) - 1;
    if (ipl != inl)
        error('@network/get: Input pattern length (%d) and input neurons length (%d) do not match', ipl, inl);
    end

    input_pattern = [-1 input_pattern]';
    output = net.f_activation(net.weights * input_pattern)';
endfunction
