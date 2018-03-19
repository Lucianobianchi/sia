%% Network config
n_inputs = 2
n_outputs = 1
hidden_layers = [60 30]
lr = 0.05
f_activation_name = 'tanh'
slope = 1

%% Set config
filename = 'terrain02.data'
seed = time
test_ratio = 0.75

%% Train config
batch_size = 1
epochs = 25
algorithm = 'momentum'      % 'momentum' 'adaptive' 'vanilla' 
alfa = 0.9                  % momentum config
lr_increase = 0.0025        % adaptive config
lr_decrease_factor = 0.25   % adaptive config

%% Error config
tolerance = 0.1
n_toperrors = 10

%% Plot terrain config
pts = rand(20000, 2) * 2 - 1;  % points to plot
