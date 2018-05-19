from Selectors import selector

def _only_children(parents, children, count):
    return children[:count]

strategies = {
    'only_children': _only_children
}

def replacement(selection, replace = 'only_children', count):
    return lambda p, c: selector(selection)(strategies[replace](p, c, count))
