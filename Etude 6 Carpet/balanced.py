__author__ = "Suppanut Chaimathikul (6991678)"

import fileinput
from carpet import Carpet
import random
from math import factorial

# This defines the maximum amount of times to loop
GLOBAL_MAX = 100000


# This mathematics formular finds how many combinations are there for {size} items
# in {num_items} pool. This does not take the ordering into consideration.
# so 10 num_items with 10 size will produce only 1 unique combination
def max_comb(num_items, size):
    return factorial(num_items) / (factorial(num_items - size) * factorial(size))


def randomize_carpet(stock, size):
    result_carpet = random.sample(list(stock), size)
    """
    random_or_not = random.choice([True, False])
    # random_or_not = False
    if random_or_not is True:
        random_num_elements = random.sample(range(len(result_carpet)), random.randint(0, len(result_carpet)))
        for x in random_num_elements:
            result_carpet[x] = result_carpet[x][::-1]
        return result_carpet
    else:
        return result_carpet"""
    return result_carpet


def balanced_random(stock, size):
    best_carpet = Carpet(randomize_carpet(stock, size))
    best_carpet_abs = best_carpet.get_total_matches()
    target_match = int((len(stock[0]) * (size - 1)) / 2)
    if max_comb(len(stock), size) == 1:
        max_count = 100000
    elif max_comb(len(stock), size) <= GLOBAL_MAX:
        max_count = int(max_comb(len(stock), size))
    else:
        max_count = int(GLOBAL_MAX)
    # print(f"Max count: {max_count}")
    for x in range(max_count):
        new_carpet = Carpet(randomize_carpet(stock, size))
        new_carpet_matches = new_carpet.get_total_matches()
        if abs(target_match - new_carpet_matches) < abs(target_match - best_carpet_abs):
            best_carpet = new_carpet
            best_carpet_abs = new_carpet_matches
            if abs(best_carpet.get_total_matches() - best_carpet.get_total_non_matches()) == 0:
                return best_carpet.to_list(), abs(best_carpet.get_total_matches() - best_carpet.get_total_non_matches())

    if abs(best_carpet.get_total_matches() - best_carpet.get_total_non_matches()) >= 10:
        best_carpet_list = best_carpet.to_list()
        og_carpet = best_carpet_list
        for x in range(len(best_carpet_list * 10)):
            random_index = random.randint(0, len(best_carpet_list) - 1)
            diff = list(set(stock) - set(og_carpet))
            og_carpet_obj = Carpet(og_carpet)
            copy_carpet = list(og_carpet)
            for y in diff:
                copy_carpet[random_index] = y
                copy_carpet_obj = Carpet(copy_carpet)
                if abs(copy_carpet_obj.get_total_matches() - copy_carpet_obj.get_total_non_matches()) < abs(
                        og_carpet_obj.get_total_matches() - og_carpet_obj.get_total_non_matches()):
                    og_carpet = list(copy_carpet)
        result_carpet = Carpet(og_carpet)
        # duplic = any(og_carpet.count(element) > 1 for element in og_carpet)
        # print(f"Check dupes: {duplic}")
        return result_carpet.to_list(), abs(result_carpet.get_total_matches() - result_carpet.get_total_non_matches())

    return best_carpet.to_list(), abs(best_carpet.get_total_matches() - best_carpet.get_total_non_matches())


if __name__ == '__main__':
    # stock = ["AAB", "BBB", "ABC", "CBA", "CCC", "CBC", "CBB", "BAB", "CCA", "CCB", "BAC", "AAA", "BCC", "CAB", "BCA"]
    f = open("input/large.txt", "r", encoding="utf-16")
    contents = f.read()
    stock = contents.splitlines()
    f.close()
    result = balanced_random(stock, 5)
    # print(f"Best Carpet: {result.to_list()} \nMatches: {result.get_total_matches()}")

    # new_carpet = Carpet(['ABB', 'CCA', 'CBA', 'BBB', 'CCC'])
