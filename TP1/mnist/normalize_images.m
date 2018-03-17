function images = normalize_images(X)
    images = double(X) / 255 * 0.99;
endfunction
