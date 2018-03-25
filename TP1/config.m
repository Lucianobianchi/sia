%% Network config
n_inputs = 2;
n_outputs = 1;
hidden_layers = [90 60];    % array of the amount of neurons in each layer
lr = 0.01;                  % learning rate
f_activation_name = 'tanh'; % 'step', 'linear', logistic', 'tanh',
slope = 1;                  % beta for logistic and tanh
initialization = 'uniform_one';  % 'uniform', 'uniform_one', 'normal', 'xavier_uniform', 'xavier'
should_normalize = false;
input_lower_bound = -1;     % input pattern lower bound after normalization
input_upper_bound = 1;      % input pattern upper bound after normalization

%% Train config
batch_size = 5;
epochs = 1;
algorithm = 'vanilla';      % 'momentum' 'adaptive' 'vanilla'
alfa = 0.9;                  % momentum config
cost_interval = 1;          % adaptive config
inc_steps = 5;               % adaptive config
lr_increase = 0.1;        % adaptive config
lr_decrease_factor = 0.01;   % adaptive config

%% Plot terrain config
pts = rand(20000, 2) * 2 - 1;  % points to plot the terrain

%% Error config
tolerance = 0.1;
n_toperrors = 10;

%% Set config
filename = 'terrain02.data';
seed = 42;  % use time() for a different seed each config
test_ratio = 0.35;

%Train Test config
max_tries = 1000;
test_error = 3;
min_cost_change = 1e-6;
