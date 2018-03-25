%% Network config
n_inputs = 2;
n_outputs = 1;
hidden_layers = [30];
lr = 0.01;
f_activation_name = 'logistic';
slope = 1;
initialization = 'uniform_one';
input_lower_bound = -1;
input_upper_bound = 1;

%% Train config
batch_size = 5;
epochs = 1;
algorithm = 'vanilla';      % 'momentum' 'adaptive' 'vanilla'
alfa = 0.9;                  % momentum config
cost_interval = 5;          % adaptive config
inc_steps = 3;               % adaptive config
lr_increase = 0.005;        % adaptive config
lr_decrease_factor = 0.1;   % adaptive config

%% Plot terrain config
pts = rand(20000, 2) * 2 - 1;  % points to plot

%% Error config
tolerance = 0.1;
n_toperrors = 10;

%% Set config
filename = 'terrain02.data';
seed = 42;  % use time() for a different seed each config
test_ratio = 0.35;

%Train Test config
max_tries = 100;
test_error = 3;     % error percentage to achieve
min_cost_change = 1e-6;
