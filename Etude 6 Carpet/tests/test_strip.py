import unittest
from carpet import *

class StripTest(unittest.TestCase):

    def test_true(self):
        self.assertEqual(6, 6)

    @unittest.skip    
    def test_strip_constructor(self):
        self.assertEqual("ABBB", Strip("ABBB").colours)
    
    @unittest.skip  
    def test_strip_in_carpet(self):
        strip = Strip("AABB")
        carpet = Carpet(strip)
        self.assertEqual(["AABB"], carpet.strips)

if __name__ == '__main__':
    unittest.main()