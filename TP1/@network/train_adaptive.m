%% -*- texinfo -*-
%% @deftypefn {} {@var{net} =} train_adaptive (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{batch_size}, @var{cost_interval}, @var{inc_steps}, @var{epochs}, @var{lr_increase}, @var{lr_decrease_fator})
%% @deftypefnx {} {[@var{net}, @var{costs}] =} train_adaptive (@var{net}, @var{input_pattern_set}, @var{expected_set}, @var{batch_size}, @var{cost_interval}, @var{inc_steps}, @var{epochs}, @var{lr_increase}, @var{lr_decrease_fator})
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
%% The argument @var{batch_size} corresponds to the size of the batch
%% of each training iteration. Use 1 for online training and
%% rows(@var{input_pattern_set}) for batch training. Anything in
%% between correspond to mini-batch training.
%%
%% The argument @var{epochs} corresponds to the number of epochs to train.
%%
%% The argument @var{cost_interval} corresponds to the number of iterations
%% after which the cost will be calculated in order to verify if the learning
%% rate should be incremented or decremented.
%%
%% The argument @var{inc_steps} corresponds to the number of successful
%% iterations needed in order to increment learning rate.
%%
%% The argument @var{lr_increase} corresponds to the additive increase of the learning rate.
%%
%% The argument @var{lr_decrease_factor} corresponds to the multiplicative decrease of the learning rate.
%%
%% Note that Octave does not implement pass by reference,
%% then the modified net is the return value of the train_adaptive method:
%%
%% @example
%% [net, costs] = train_adaptive(net, input_pattern, expected, epochs, lr_increase, lr_decrease_factor)
%% @end example
%%
%%
%% @seealso{@@network/network, @@network/train_skeleton, @@network/backpropagation, @@network/cost}
%% @end deftypefn

function [net, costs] = train_adaptive(net, input_pattern_set, expected_set, batch_size, epochs, cost_interval, inc_steps, lr_increase, lr_decrease_factor)
    %% global variables for shairng variables between function since
    %% nested function handles are not yet supported in Octave
    %% desired behaviour would be a closure like approach
    init_globals(net, epochs, input_pattern_set, expected_set, cost_interval, inc_steps, lr_increase, lr_decrease_factor);
    net = train_skeleton(net, input_pattern_set, expected_set, batch_size, epochs, @train_callback);

    global g_costs;
    costs = g_costs;
endfunction

function net = train_callback(net, backprop, input_pattern, expected)
    global g_costs;
    global g_costs_len;
    global g_prev_cost;
    global g_input_pattern_set;
    global g_expected_set;
    global g_cost_interval;
    global g_cost_step;
    global g_inc_steps;
    global g_step;
    global g_lr_increase;
    global g_lr_decrease_factor;

    prev_weights = net.weights;

    for i = 1:length(net.weights)
        net.weights{i} = net.weights{i} + net.lr * backprop{i};
    end

    c = cost(net, g_input_pattern_set, g_expected_set);
    g_cost_step = g_cost_step + 1;

    if (g_cost_step == g_cost_interval)
        g_cost_step = 0;

        if (c >= g_prev_cost)
            g_step = 0;
            net.lr = net.lr - net.lr * g_lr_decrease_factor;
            net.weights = prev_weights;
            backprop = backpropagation(net, input_pattern, expected);

            for i = 1:length(net.weights)
                net.weights{i} = net.weights{i} + net.lr * backprop{i};
            end

            c = cost(net, g_input_pattern_set, g_expected_set);  
        else
            g_step = g_step + 1;
            if (g_step == g_inc_steps)
                g_step = 0;
                net.lr = net.lr + g_lr_increase;        
            end
        end

        g_prev_cost = c;
    end

    g_costs(++g_costs_len) = c;
endfunction

function init_globals(net, epochs, input_pattern_set, expected_set, cost_interval, inc_steps, lr_increase, lr_decrease_factor)
    global g_costs;
    global g_costs_len;
    global g_prev_cost;
    global g_input_pattern_set;
    global g_expected_set;
    global g_cost_interval;
    global g_cost_step;
    global g_inc_steps;
    global g_step;
    global g_lr_increase;
    global g_lr_decrease_factor;

    g_costs = zeros(1, length(epochs * rows(input_pattern_set)));
    g_costs_len = 0;
    g_prev_cost = cost(net, input_pattern_set, expected_set);
    g_input_pattern_set = input_pattern_set;
    g_expected_set = expected_set;
    g_cost_interval = cost_interval;
    g_cost_step = 0;
    g_inc_steps = inc_steps;
    g_step = 0;
    g_lr_increase = lr_increase;
    g_lr_decrease_factor = lr_decrease_factor;
endfunction
