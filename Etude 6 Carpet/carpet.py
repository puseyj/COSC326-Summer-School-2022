import time


class Carpet:

    STOCK_COPY_TIME = 0
    REVERSE_STOCK_TIME = 0
    MAKE_CHILDREN_TIME = 0
    MAKE_CHILDREN_STRIPS_COPY = 0
    MAKE_CHILDREN_NEW_CARPET = 0

    def __init__(self, strips=[]):
        """
        Creates a new carpet. If a strips array is provided, then we make a new carpet from this.
        ---
        PARAMS
        strips : [Strings]
            The array of Strings to build this carpet from. Default is []
            The list of strings passed will be copied, so new objects are made (to avoid reference issues)
        """
        self.strips = strips.copy()
    
    
    def __str__(self):
        """
        Convert this carpet to a string
        """
        s = ""
        for strip in self.strips:
            s += (str(strip) + "\n")
        return s.strip()
    

    def __repr__(self):
        """What it displayed when printed from a list."""
        return str(self.strips)

   
    def add_strip(self, strip):
        """
        Add a new strip onto the end of the carpet.
        ---
        PARAMS
        strip : String
            The strip to add to this carpet
        """

        self.strips.append(strip)
    
    def to_list(self):
        return self.strips

    def get_children(self, stock):
        """
        Calculate all possible carpets that can be made by appending the strips in stock to this carpet
        Returns these children carpets as a list of Carpet objects
        ---
        PARAMS
        stock : [String]
            All strips given, including those already present in this carpet (these are filtered out before calculating children)
        ---
        RETURNS
        [Carpet]
            A list of all possible Carpet objects deriving from appending the stock strips to this carpet
            These Carpet objects are unique (they share no references) so should be memory safe
        """

        # Remove from stock the current strips in the node
        stock_copy_start = time.time()
        temp_stock = stock.copy()
        for strip in self.strips:
            temp_stock.remove(strip)
        Carpet.STOCK_COPY_TIME += time.time() - stock_copy_start

        reverse_stock_start = time.time()
        normal_and_rev_stock = []
        for normal_strip in temp_stock:
            reverse_strip = rotate_strip(strip)
            # reverse_strip = normal_strip.rotate_strip()
            normal_and_rev_stock.append(normal_strip)
            normal_and_rev_stock.append(reverse_strip)
        Carpet.REVERSE_STOCK_TIME += time.time() - reverse_stock_start

        make_children_start = time.time()
        carpet_children_list = []
        for strip_stock in temp_stock:
            make_children_strips_copy_start = time.time()
            new_strips = self.strips.copy()
            Carpet.MAKE_CHILDREN_STRIPS_COPY += time.time() - make_children_strips_copy_start

            new_strips.append(strip_stock)

            make_children_new_carpet_start = time.time()
            carpet = Carpet(strips=new_strips)
            Carpet.MAKE_CHILDREN_NEW_CARPET += time.time() - make_children_new_carpet_start
            carpet_children_list.append(carpet)
        Carpet.MAKE_CHILDREN_TIME += time.time() - make_children_start

        return carpet_children_list


    def potential_matches(self, strip):
        """
        Returns the number of matches between the end strip and the potential strip.
        ---
        PARAMS
        strip : String
            The strip to consider appending to the end of this carpet
        ---
        RETURN
        int
            The number of matches between the given strip and the strip at the end of this carpet
        """

        # We cannot match with the empty carpet
        if self.get_length == 0: return None

        # Indexing at [-1] returns the last item in the list
        return find_matches(self.strips[-1], strip)
        #return self.strips[-1].find_matches(strip) - old method from when Strip was an object


    def get_length(self):
        """
        Returns the number of strips in the carpet.
        ---
        RETURN
        int
            The number of strips in this carpet
        """

        return len(self.strips)


    def get_total_matches(self):
        """
        Returns the total number of matches within the carpet.
        ---
        RETURN
        int
            The total number of matches in this carpet
        """
        total_matches = 0
        for index in range (len(self.strips)-1):
            total_matches += find_matches(self.strips[index], self.strips[index+1])
            # total_matches += Strip.find_matches(self.strips[index],self.strips[index+1])
        return total_matches

    def get_total_non_matches(self):
        """
        Returns the total number of non-matches within the carpet.
        ---
        RETURN
        int
            The total number of non-matches in this carpet
        """
        total_non_matches = 0
        for index in range (len(self.strips)-1):
            total_non_matches += find_non_matches(self.strips[index], self.strips[index+1])
            # total_matches += Strip.find_matches(self.strips[index],self.strips[index+1])
        return total_non_matches
    

def find_matches(first_strip, second_strip):
    """
    Finds the number of matches between two strips.
    ---
    PARAMS
        first_strip: String
            the first strip to compare
        second_strip: String
            the second strip to compare
    ---
    RETURN
        int 
            the number of matches between the two strips
    """
    matches = 0
    for index in range(len(first_strip)):
        if index >= len(second_strip):
            continue
        if first_strip[index] == second_strip[index]:
            matches = matches + 1
    return matches

def find_non_matches(first_strip, second_strip):
    """
    Finds the number of non-matches between two strips.
    ---
    PARAMS
        first_strip: String
            the first strip to compare
        second_strip: String
            the second strip to compare
    ---
    RETURN
        int
            the number of non-matches between the two strips
    """
    non_matches = 0
    for index in range(len(first_strip)):
        if index >= len(second_strip):
            continue
        if first_strip[index] != second_strip[index]:
            non_matches = non_matches + 1
    return non_matches

def rotate_strip(strip):
    """
    Rotates a strip.
    ---
    PARAMS
        strip: String
            the strip to rotate
    ---
    RETURN
        String
            returns the rotated version of the strip
    """
    return strip[::-1]


# Maybe remove this class later

# class Strip:
#     """
#     Represents a singular strip.
#     """


#     def __init__(self, colours):
#         """
#         Constructs a new strip from a string.
#         ---
#         PARAMS
#         colours : String
#             The string representing the colours of this strip
#         """
#         self.colours = colours

#     def __str__(self):
#         """
#         Represent this strip as a string in a readable manner.
#         """

#         # return " ".join(self.colours)
#         return str(self.colours)

#     def __repr__(self):
#         return str(self.colours)


#     def length(self):
#         return len(self.colours)


#     def find_matches(self, strip):
#         """
#         Finds the amount of matches between this strip and another and returns the result.
#         ---
#         PARAM
#         strip : Strip
#             The strip to compare to this strip
#         ---
#         RETURN
#         int
#             The number of matches between this strip and the given strip
#         """

#         matches = 0
#         for index in range(len(self.colours)):
#             if self.colours[index] == strip.colours[index]:
#                 matches = matches + 1
#         return matches


#     def rotate_strip(self):
#         """
#         Returns a new Strip object that represents this strip but rotated/reversed
#         ---
#         RETURN
#         Strip
#             A new strip object with a reversed colour order
#             This new object has a copied array so reference safety is conserved
#         """

#         # We make a copy of this strips colours, then use a special list comprehension
#         # [::-1] means move over the entire list taking steps of -1 each time
#         # Which reverses the list not-in-place as needed
#         new_strip = Strip(self.colours[::-1])
#         return new_strip
