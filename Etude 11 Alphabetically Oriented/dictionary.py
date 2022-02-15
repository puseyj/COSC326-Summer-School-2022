
import sys
import re
ls = []
# validPuncChars = {".", ",", ";", ":", "?", "!"}

def main():
    lines = open(sys.argv[1], 'r').readlines()
    for line in lines:
        #line = line.replace('"', '').replace('\n', '')
        line = line.strip()
        arr = re.split("\\s+", line)
        for word in arr:
            #print(f"word : {word}")
            word = word.strip()
            checkIfValid(word)

    sort = sorted(ls)
    # remove all duplicates and put back into sortable form
    st = sorted(list(set(sort)))
    for word in st:
        print(word)

def checkIfValid(s: str):
    aposCount = s.count("'")
    # puncCount, puncInd = getPuncCharCount(s)
    match = re.search("(^(\"|\\s+)*[a-zA-Z']+(\\s+|[.,;:?!\"])*$)", s)
    if match is not None:
        #print(f"match: {match}")
        if aposCount <= 1:
            stripped = re.sub("^(\\s+|\"|)", "", s)
            stripped =  re.sub("(\\s+|[.,;:?!\"])$", "", stripped)
            counter = 0
            indexOfCap = 0
            for x in range(0, len(stripped)):
                    if stripped[x].isupper():
                        counter += 1
                        indexOfCap = x
            if stripped.startswith("'") is not True and counter <= 1 and indexOfCap == 0:
                strippedApos = re.sub("'*", "", stripped)
                if strippedApos.isalpha():
                    ls.append(stripped.lower())
# sum(1 for x in stripped if x.isupper()) <= 1
main()
