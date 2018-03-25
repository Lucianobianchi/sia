config
init_script

epoch_count = 0;
max_epoch = 200;
line_interval = 100000;

activations = cell(1, 0);
i = 1;
while (epoch_count < max_epoch)
    [net costs] = training(algorithm, net, train_set, train_expected, batch_size, 1, alfa, lr_increase, lr_decrease_factor); % 1 epoch

    if (mod(epoch_count, line_interval) == 0)
        % hago un epoch primero
        c = costs(end)
        activations{i} = hidden_activations(net, train_set(1:3, :));
        i
        i++;
    end

    epoch_count++;
    epoch_count
    c = costs(end)
    fflush(stdout);
endwhile

means = []
stds = []
activations
for inter = 1:(i-1)
    for lay = 1:length(hidden_layers)
        activations{inter}{lay}
        stds(lay, inter) = mean(std(activations{inter}{lay}, 0, 2)) % TODO: sacar std de toda la matriz?
        means(lay, inter) = mean(mean(activations{inter}{lay}))
    end
end

hold on
% print_layer = 1;
% plot(means(print_layer, :))
%
% for inter = 1:(i-1)
%     epoch_mean = means(print_layer, inter)
%     dispersion = stds(print_layer, inter)
%     plot([inter inter], [epoch_mean-dispersion epoch_mean+dispersion], "r")
% end
%
%
% print_layer = 2;
% plot(means(print_layer, :))
%
% for inter = 1:(i-1)
%     epoch_mean = means(print_layer, inter)
%     dispersion = stds(print_layer, inter)
%     plot([inter inter], [epoch_mean-dispersion epoch_mean+dispersion], "b")
% end

hold off
