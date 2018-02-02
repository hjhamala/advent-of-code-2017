import numpy as np
from functools import reduce


def map_as_sorted(ls):
    return list(map(lambda x: np.sort(x), ls))


def parse_list(file_name):
    return np.loadtxt(file_name, delimiter="\t")


def map_first_and_last(ls):
    return list(map(lambda x: [x[0], x[-1]], ls))


def difference_last_and_first(ls):
    return list(map(lambda x: x[-1] - x[0], ls))


def calculate_list(ls):
    return reduce(lambda x, y: x + y, ls)


def solve():
    parsed = parse_list("input.txt")
    sorted = map_as_sorted(parsed)
    first_and_last = map_first_and_last(sorted)
    difference = difference_last_and_first(first_and_last)
    return calculate_list(difference)


def even_or_zero(x,y):
    if x == y:
        return 0
    if (x/y).is_integer():
      return x/y
    else:
      return 0


def find_whole(x, ls):
    even_list = list(map(lambda div: even_or_zero(x, div), ls))
    return calculate_list(even_list)


def map_row_with_whole(ls):
    return list(map(lambda r: list(map(lambda e: find_whole(e,r), r)), ls))


def solve_2():
    parsed = parse_list("input.txt")
    sorted = map_as_sorted(parsed)
    wholed = map_row_with_whole(sorted)
    return calculate_list(list(map(calculate_list, wholed)))