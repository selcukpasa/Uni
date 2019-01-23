import random

def generateGameboard():
    n = int(input("Länge: "))
    m = int(input("Breite: "))

    matrix = [0] * n
    for i in range(m):
        matrix[i] = [0] * n
    return matrix



def printGameboard(matrix):
    for row in matrix:
        print(' '.join([str(elem) for elem in row]))

printGameboard(generateGameboard())



"""Analyse:
    Eingabegrößen: Multiplikation (2x), Zuweisungen (2), Index aus einer Liste
"""



"""
def testPrintGameboard():
    print(printGameboard(10,10))
    print(printGameboard(9,10))
    print(Gameboard(5,5))

testPrintGameboard()
"""
