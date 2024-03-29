function ans = plot_layer_activations(net, patterns)
    hold on
    weights = get(net, 'weights');
    % Do not include output layer, since it depends on the pattern
    activations = cell(1, length(weights)-1);
    names = cell(1, length(weights)-1);
    layer_output = patterns;
    range = (-1.1:0.2:1.1);
    for i = 1:(length(weights)-1)
        layer_output = activate_layer(net, i, layer_output);
        activations{i} = layer_output;

        h = hist(mean(layer_output', 2), range);
        h
        xspline = -1:0.025:1;
        yspline = spline (range, h, xspline);
        plot(xspline, yspline);
        names{i} = sprintf('Capa oculta %d', i);
    end

    legend(names);
    hold off
endfunction
