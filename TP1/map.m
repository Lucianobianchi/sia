function O = map(M, out_lower, out_upper)
    x_max = max(M);
    x_min = min(M);
    O = out_lower + (M - x_min) .* (out_upper - out_lower) ./ (x_max - x_min);
endfunction
