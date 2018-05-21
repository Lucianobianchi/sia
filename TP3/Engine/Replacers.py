from Selectors import selector
from math import ceil, floor

# Method 2
# If len(children) == len(parents) this is analogous to method 1
def _select_parents(parents, children, B, selector_name1, selector_name2):
    N = len(parents)
    k = len(children)
    select_count = N - k

    selected = _select_proportion(parents, select_count, B, selector_name1, selector_name2)
    return [*selected, *children]

# Method 3
def _select_total(parent, children, B, selector_name1, selector_name2):
    N = len(parents)
    k = len(children)
    parent_count = N - k
    total = [*parent, *children]

    selected1 = _select_proportion(parent, parent_count, B, selector_name1, selector_name2)
    selected2 = _select_proportion(total, k, B, selector_name1, selector_name2)
    return [*selected1, *selected2]

def _select_proportion(group, select_count, B, selector_name1, selector_name2):
    if select_count == 0:
        return []

    proportion = round(B * select_count)
    
    selected1 = selector(selector_name1)(group, proportion)
    selected2 = selector(selector_name2)(group, select_count - proportion)
    return [*selected1, *selected2]

strategies = {
    'select_parents': _select_parents,
    'select_total': _select_total
}

def replacers(name = 'select_parents'):
    return strategies[name]
