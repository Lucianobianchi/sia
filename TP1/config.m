%% Network config
n_inputs = 2;
n_outputs = 1;
hidden_layers = [60 30];
lr = 0.01;
f_activation_name = 'tanh';
slope = 1;

%% Set config
filename = 'terrain02.data';
seed = 1;  % use time() for a different seed each config
test_ratio = 0.25;

%% Train config
batch_size = 1;
epochs = 10;
algorithm = 'momentum';      % 'momentum' 'adaptive' 'vanilla' 
alfa = 0.9;                  % momentum config
cost_interval = 5;          % adaptive config
inc_steps = 3;               % adaptive config
lr_increase = 0.005;        % adaptive config
lr_decrease_factor = 0.1;   % adaptive config

%% Error config
tolerance = 0.1;
n_toperrors = 10;

%% Plot terrain config
pts = rand(20000, 2) * 2 - 1;  % points to plot
