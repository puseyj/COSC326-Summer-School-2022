import unittest
from carpet import *


class CarpetTest(unittest.TestCase):

    def test_true(self):
        self.assertEqual(6, 6)
    
    def test_false(self):
        self.assertNotEqual(5, 6)
    
    def test_constructor_empty(self):
        self.assertEqual([], Carpet().strips)
    
    # def test_constructor_strip(self):
    #     self.assertEqual([5], Carpet(strip=5).strips)
    
    def test_constructor_list(self):
        self.assertEqual([1, 2, 3, 4], Carpet(strips=[1, 2, 3, 4]).strips)
    
    # def test_constructor_list_and_strip(self):
    #     self.assertEqual([1, 2, 3, 4, 5], Carpet(strip=5, strips=[1, 2, 3, 4]).strips)
    
    def test_add_single_strip(self):
        carpet = Carpet(strips=[1, 2, 3, 4])
        carpet.add_strip(5)
        self.assertEqual([1, 2, 3, 4, 5], carpet.strips)
    
    def test_carpet_length(self):
        self.assertEqual(5, Carpet(strips=[1, 2, 3, 4, 5]).get_length())
    
    # def test_get_children_basic(self):

    #     stock_list = ["RRR","BBB","BBB","GGR","BRB"]

    #     stock = list()
    #     for stock_string in stock_list:
    #         stock.append(Strip(stock_string))

    #     strips = [Strip("BRB"), Strip("GGR"), Strip("BBB")]
    #     carpet = Carpet(strips)
    #     children = carpet.get_children(stock)
    #     self.assertEqual([], children)
    
    def test_get_children_string(self):
        carpet = Carpet(strips=["AA", "CC"])
        stock = ["AA", "AB", "CC", "CD"]
        children = carpet.get_children(stock)
        list = [child.strips for child in children]
        self.assertEqual([["AA", "CC", "AB"], ["AA", "CC", "CD"]], list)

if __name__ == '__main__':
    unittest.main()