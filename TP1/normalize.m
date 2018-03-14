function [normalized factor] = normalize(vector)
    factor = norm(vector, Inf);
    normalize = double(vector) / factor;
endfunction
