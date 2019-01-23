import random

def generateGameboard(n,m):
    '''Inititalisiert eine n x m Matrix'''
    matrix = [['.' for x in range(n)] for y in range(m)]
    return matrix

def printGameboard(matrix):
    '''Die Funktion gibt die n x m Matrix aus'''
    for y in range(len(matrix)):
        line = ''
        for x in range(len(matrix[y])):
            line = line + str(matrix[y][x]) + ' '
        print(line)

print(printGameboard(generateGameboard(10,10))) #Test-Aufruf


#p Minen (zwischen 0 und 50) platziert mit Hilfe vom modul random
def newGame(p,matrix):
    '''Die Funktion platziert auf zufälligen Feldern Minen'''
    for y in range(len(matrix)):
        for x in range(len(matrix[y])):
            if(random.randint(0,51) < p):
                matrix[y][x] = '*'  #felder mit Minen
            else:
                matrix[y][x] = '.'  #felder ohne Mine
    return matrix

print(printGameboard(newGame(5,generateGameboard(10,5)))) #Test-Aufruf
