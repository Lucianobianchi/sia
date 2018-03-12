function show_num(X, i)
    img = reshape(X(i,:), 28, 28)';
    imshow(img);
endfunction
