from carpet import *
from collections import deque
import time

GREEDY_TIME = 0
STACK_INIT_TIME = 0
GET_CHILD_TIME = 0
PROCESS_CHILD_TIME = 0

def greedy(stock, size):
    """
    Simple greedy algorithm to get a lower bound for a maximal carpet.
    ---
    PARAMS
    stock: [String]
        a list of strings(strips) representing the stock
    size: int
        the number of strips needed in the carpet
    ---
    RETURN
    Carpet
        returns a Carpet
    """
    temp_stock = stock.copy() #remove strips from temp_stock when we add to the greedy carpet
    strips = list() #Carpet takes a list of strips
    strips.append(temp_stock[0])
    greedyCarpet = Carpet(strips)
    temp_stock.pop(0)
    #Build the carpet strip by strip, picking the most matches each time
    while greedyCarpet.get_length() < size:
        temp_strip = temp_stock[0]
        for strip in temp_stock:
            if (greedyCarpet.potential_matches(strip) > greedyCarpet.potential_matches(temp_strip)):
                temp_strip = strip
        greedyCarpet.add_strip(temp_strip)
        temp_stock.remove(temp_strip)
    return greedyCarpet

def dfs_pruning(stock, size):
    global GREEDY_TIME, STACK_INIT_TIME, GET_CHILD_TIME, PROCESS_CHILD_TIME, FULL_MAKE_CHILDREN_TIME
    """
    Depth first search with pruning to get a maximal match carpet.
    We use a greedy carpet as our lower bound.
    ---
    PARAMS
    stock: [String]
        a list of strings representing the stock of carpet strips
    size: int
        the number of strips needed for the final carpet
    ---
    RETURN
    Carpet
        Returns the maximal match carpet
    """
    #get a lower bound
    try:
        greedy_time_start = time.time()
        max_carpet = greedy(stock, size) 
        GREEDY_TIME += time.time() - greedy_time_start
        max_matches = max_carpet.get_total_matches()
        #If we don't have any possible matches within our stock, just return our greedy carpet
        if max_matches == 0:
            return max_carpet 
        print(f"Max Greedy Matches: {max_matches}")

        stack_init_time_start = time.time()
        stack = deque()
        for strip in stock:
            stack.append(Carpet([strip]))
        STACK_INIT_TIME += time.time() - stack_init_time_start
        current_carpet = []
        strip_length = len(stock[0]) #all strips should be the same size

        while stack:
            current_carpet = stack.pop()
            total = current_carpet.get_total_matches()
            #print(f"Current:\n{str(current_carpet)}")
            #Is it better than our current max carpet?
            if total > max_matches:
                    max_carpet = current_carpet
                    max_matches = total
            if current_carpet.get_length() >= size:
                continue
            #if we had perfect matches for the rest of the carpet, could it give us more matches?
            if perfect_matches(current_carpet,size,strip_length,total) <= max_matches:
                continue

            get_child_time_start = time.time()
            x = current_carpet.get_children(stock)
            len(x)
            GET_CHILD_TIME += time.time() - get_child_time_start

            process_child_time_start = time.time()
            for child in current_carpet.get_children(stock):
                stack.append(child)
            PROCESS_CHILD_TIME = time.time() - process_child_time_start
    except KeyboardInterrupt:
        pass

    print(f"""
    {GREEDY_TIME=},
    {STACK_INIT_TIME=},
    {GET_CHILD_TIME=},
    {PROCESS_CHILD_TIME=},

    ---

    {Carpet.STOCK_COPY_TIME=},
    {Carpet.MAKE_CHILDREN_TIME=},
    {Carpet.REVERSE_STOCK_TIME=},

    {Carpet.MAKE_CHILDREN_STRIPS_COPY=},
    {Carpet.MAKE_CHILDREN_NEW_CARPET=}
    """)
    return max_carpet

def perfect_matches(carpet,size, strip_length, total):
    """
    Computes the amount of matches the carpet could get if all of the remaining strips were fully matching.
    ---
     PARAMS
    carpet: Carpet
        the Carpet object to find the perfect matches of
    size: int
        the length the carpet should be at the end
    strip_length: int
        the length of the strips
    ---
    RETURN
    int
        the potential number of matches for a perfect carpet
    """
    remaining_strips = size - carpet.get_length()
    return total + strip_length * remaining_strips
    

if __name__ == '__main__':
    stock = ["AAB", "BBB", "ABB", "CBA", "CCC", "CBC", "CBB", "BAB", "CCA", "CCB", "CCC", "CCC"]
    found = greedy(stock, 5)
    found = dfs_pruning(stock, 5)
    print("---")
    print(found)
    print(found.get_total_matches())
    print("---")
    print(found)
    print(found.get_total_matches())
