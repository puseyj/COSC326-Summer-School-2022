import unittest
from maximal import *

class maxTests(unittest.TestCase):

    def test_true(self):
        self.assertEqual(6, 6)
    
    def test_greedy_basic(self):
        stock_list = ["RRR","BBB","BBB","GGR","BRB", "GRR"]
        found = greedy(stock_list, 5)
        expected = ["RRR", "GRR", "GGR", "BBB", "BBB"]
        matches = total_matches(found)
        self.assertEqual(expected, found)
        self.assertEqual(7, matches)
    
    def test_maximal_basic_5(self):
        stock_list = ["RRR","BBB","BBB","GGR","BRB", "GRR"]
        found = maximal_carpet(stock_list, 5)
        expected = ["GRR", "RRR", "BRB", "BBB", "BBB"]
        matches = total_matches(found)
        self.assertEqual(8, matches)
        self.assertEqual(expected, found)

    def test_maximal_basic_5_duplicates(self):
        stock_list = stock = ["AAB", "BBB", "ABB", "CBA", "CCC", "CBC", "CBB", "BAB", "ACC", "CCB", "CCC", "CCC"]
        found = maximal_carpet(stock_list,5)
        expected = ["CCC", "CCC", "CCC", "BCC", "ACC"]
        matches = total_matches(found)
        self.assertEqual(expected, found)
        self.assertEqual(10, matches)

    def test_maximal_basic_10(self):
        stock_list = ["RRR","BBB","GGR","BRB", "GRR", "GRJ", "JRB", "BJR", "GJG", "BGR"]
        found = maximal_carpet(stock_list, 5)
        expected = ['RRR', 'GRR', 'GGR', 'BGR', 'BJR']
        matches = total_matches(found)
        self.assertEqual(expected, found)
        self.assertEqual(8, matches)
    
    def test_maximal_basic_20(self):
        stock_list = ["RSR","BBB","GGR","BRB", "GRR", "GRJ", "JRB", "BJR", "GJG", "BGR", "RRB" "BYO", "OYO", "GRO", "OOL", "LLR" "LRO" "LPQ", "BRQ", "BOS"]
        found = maximal_carpet(stock_list, 5)
        expected = ['BRQ', 'BRJ', 'GRJ', 'GRO', 'GRR']
        matches = total_matches(found)
        self.assertEqual(expected, found)
        self.assertEqual(8, matches)
    
    def test_maximal_medium_5(self):
        stock_list = ["RRRTEV","BBRGEB","BFRYBB","GGTUGR","BROILB", "GLOPRR"]
        found = maximal_carpet(stock_list, 5)
        expected = ['GLOPRR', 'BLIORB', 'BBYRFB', 'BEGRBB', 'VETRRR']
        matches = total_matches(found)
        self.assertEqual(expected, found)
        self.assertEqual(9, matches)
    
    def test_maximal_medium_10(self):
        stock_list = ["RRRTEV","BBRGEB","BFRYBB","GGTUGR","BROILB", "GLOPRR", "FEWFRO" "LPQGOP", "BRLLLQ", "SEQBOS"]
        found = maximal_carpet(stock_list, 5)
        expected = ['RRRTEV', 'BBRGEB', 'BFRYBB', 'BROILB', 'BRLLLQ']
        matches = total_matches(found)
        self.assertEqual(expected, found)
        self.assertEqual(10, matches)
    
    def test_maximal_medium_20(self):
        stock_list = ["RRRTEV","BBRGEB","BFRYBB","GGTUGR","BROILB", "GLOPRR", "FEWFRO" "LPQGOP", "BRLLLQ", "SEQBOS", "JRBFEW", "BFRTJR", "GJHOLG", "BPLSGR", "RFKLRB", "BYDLG", "WRLOYO", "GRGRLO", "WLMOOL", "LEERFJ"]
        found = maximal_carpet(stock_list, 5)
        expected = ['WLMOOL', 'BLIORB', 'QLLLRB', 'RFKLRB', 'RGSLPB']
        matches = total_matches(found)
        self.assertEqual(expected, found)
        self.assertEqual(11, matches)
    
    def test_perfect_matches_basic(self):
        stock_list = ["RRR","BBB","BBB","GGR","BRB", "GRR"]
        test_carpet = ["GRR", "RRR", "BRB"]
        total = total_matches(test_carpet)
        found = perfect_matches(test_carpet, 5, 3, total)
        expected = total + 6
        self.assertEqual(expected, found)
    
    def test_find_matches_basic(self):
        strip_1 = "ABBRTY"
        strip_2 = "YBUROY"
        expected_matches = 3
        found_matches = find_matches(strip_1, strip_2)
        self.assertEqual(expected_matches, found_matches)
    
    def test_potential_matches_basic(self):
        carpet = ["RRR", "GRR", "GGR", "BBB", "BBB"]
        strip_to_add = "BYB"
        found_matches = potential_matches(carpet, strip_to_add)
        expected_matches = 7 + 2
        self.assertEqual(expected_matches, found_matches)
    
    def test_compute_matches_basic(self):
        stock_list = ["RRR","BYB", "GGR"]
        found = compute_matches(stock_list)
        # Why does it flip some, but not the others?
        expected = {'RRR': ['RGG', 'BYB'], 'BYB': ['RRR', 'RGG'], 'GGR': ['RRR', 'BYB'], 'RGG': ['RRR', 'BYB']}
        self.assertEqual(expected, found)
    
    def test_compute_matches_medium(self):
        stock_list = ["RRR","BYB", "GGR", "RYL", "LLO", "ORR", "BRG", "BRR", "RYO"]
        found = compute_matches(stock_list)
        # Why does it flip some, but not the others?
        expected = {'RRR': ['RRO', 'RRB', 'RGG', 'LYR', 'GRB', 'OYR', 'BYB', 'OLL'], 'BYB': ['LYR', 'GRB', 'RRB', 'OYR', 'RRR', 'RGG', 'OLL', 'RRO'], 'GGR': ['RRR', 'LYR', 'ORR', 'GRB', 'BRR', 'OYR', 'BYB', 'LLO'], 'RYL': ['RYO', 'RRR', 'BYB', 'RGG', 'OLL', 'RRO', 'RRB', 'BRG'], 'LLO': ['LYR', 'RRO', 'RYO', 'RRR', 'BYB', 'GGR', 'BRG', 'BRR'], 'ORR': ['RRR', 'BRR', 'OYR', 'GGR', 'LYR', 'OLL', 'BRG', 'BYB'], 'BRG': ['BRR', 'RRR', 'BYB', 'RGG', 'ORR', 'RYL', 'LLO', 'RYO'], 'BRR': ['RRR', 'ORR', 'BRG', 'BYB', 'GGR', 'LYR', 'OYR', 'LLO'], 'RYO': ['RYL', 'RRO', 'RRR', 'BYB', 'RGG', 'LLO', 'RRB', 'BRG'], 'RGG': ['RRR', 'RYL', 'RRO', 'BRG', 'RRB', 'RYO', 'BYB', 'OLL'], 'LYR': ['OYR', 'RRR', 'BYB', 'GGR', 'LLO', 'ORR', 'BRR', 'GRB'], 'OLL': ['RYL', 'ORR', 'OYR', 'RRR', 'BYB', 'RGG', 'GRB', 'RRB'], 'RRO': ['RRR', 'RRB', 'RYO', 'RGG', 'RYL', 'LLO', 'GRB', 'BYB'], 'GRB': ['RRB', 'RRR', 'BYB', 'GGR', 'RRO', 'LYR', 'OLL', 'OYR'], 'RRB': ['RRR', 'RRO', 'GRB', 'BYB', 'RGG', 'RYL', 'RYO', 'OLL'], 'OYR': ['LYR', 'ORR', 'RRR', 'BYB', 'GGR', 'OLL', 'BRR', 'GRB']}
        self.assertEqual(expected, found)
        
    def test_optimal_matches(self):
        current_carpet = ["CCC", "CCC"]
        total = 3
        remaining = 3
        temp_stock = ["CCC", "CBC", "CCA", "BCC", "ABC", "BBC", "BAA", "BBB", "BBA", "BAB"]
        found = optimal_matches(remaining, current_carpet, compute_matches(temp_stock), total)
        expected = 9
        self.assertEqual(expected, found)


if __name__ == '__main__':
    unittest.main()