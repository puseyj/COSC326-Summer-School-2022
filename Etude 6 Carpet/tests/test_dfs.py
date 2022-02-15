import unittest
from dfsV2 import dfs
from carpet import *

class DfsTest(unittest.TestCase):

    def test_true(self):
        self.assertEqual(6, 6)
    
    def test_search_with_string(self):
        stock_list = ["RRR","BBB","BBB","GGR","BRB"]
        strips = ["BRB", "GGR", "BBB"]
        search = str(Carpet(strips))
        found = dfs(stock_list, 3)
        self.assertEqual(search, found)
    
    def test_no_match_failure(self):
        stock_list = ["RRRB","YYYY","BRBR","YBBB","BBRY"]
        strips = []
        search = str(strips)
        found = dfs(stock_list, 5)
        self.assertEqual(search, found)

    def test_basic_search(self):
        stock_list = ["RRR","BBB","BBB","GGR","BRB"]
        expected = str(Carpet(["BRB","GGR","BBB"]))
        found = dfs(stock_list, 3)
        self.assertEqual(expected, found)

    def test_basic_search_two(self):
        stock_list = ["RRR","BBB","BBB","GGR","BRB"]
        search = str(Carpet(["BRB", "GGR"]))
        found = dfs(stock_list, 2)
        self.assertEqual(search, found)
    
    def test_small_input(self):
        f = open("input.txt", "r")
        contents = f.read()
        stock_list = contents.splitlines()
        f.close()
        found = dfs(stock_list, 10)
        self.assertEqual('ETU\nSQO\nOIU\nVLS\nAWO\nSFI\nAWO\nQEI\nSBL\nVOW', found)
    
    def test_medium_input_2_length(self):
        f = open("large_input.txt", "r", encoding="utf-16")
        contents = f.read()
        stock_list = contents.splitlines()
        f.close()
        found = dfs(stock_list, 2)
        self.assertEqual('EWACHUHHHNBOAFWIKMLH\nMISVMQBMXDLGYGKTSUJB', found)

    def test_medium_input_50_length(self):
        f = open("large_input.txt", "r", encoding="utf-16")
        contents = f.read()
        stock_list = contents.splitlines()
        f.close()
        found = dfs(stock_list, 50)

        f2 = open("medium_test_50.txt", "r", encoding="utf-16")
        expected = f2.read()
        stock_list = contents.splitlines()
        f2.close()

        self.assertEqual(expected, found)

    def test_medium_input_100_length(self):
        f = open("large_input.txt", "r", encoding="utf-16")
        contents = f.read()
        stock_list = contents.splitlines()
        f.close()
        found = dfs(stock_list, 100)

        f2 = open("medium_test_100.txt", "r", encoding="utf-16")
        expected = f2.read()
        stock_list = contents.splitlines()
        f2.close()

        self.assertEqual(expected, found)
        

if __name__ == '__main__':
    unittest.main()