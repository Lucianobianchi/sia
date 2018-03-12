function ans = say_num(net, X, i)
    output = activate(net, X(i,:));
    ans = find(output == max(output)) - 1;
endfunction
