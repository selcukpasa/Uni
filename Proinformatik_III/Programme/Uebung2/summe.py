#ProInf III/Übung 2./Aufgabe 1.
"""import random"""

liste = eval(input ("liste eingeben:" ))
sum=0
for elem in liste:
        sum = sum + int(elem)
print (sum)

"""def a2a ():
    richtigeZahl = random.randint(1,20)
    x = int(input("geben sie eine Zahl ein:"))
    if 1<=x<=20:
        versuch1 = x
    else:
        print ("fehler")
    if versuch1 == richtigeZahl:
        print("Sie haben nach einem versuch gewonnen!")
        return 0;
    elif versuch1 < richtigeZahl:
        print("ist zu klein")
    elif versuch1 > richtigeZahl:
        print("ist zu gross")
    versuch2 = 0
    while versuch2 == 0:
        y = int(input("geben sie eine zweite zahl ein:"))
        if 1<=y<=20:
            versuch2 = y
        else:
            print("fehler")
    if versuch2 == richtigeZahl:
        print("Sie haben nach zwei versuchen gewonnen!")
    elif versuch2 < richtigeZahl:
        print("ist zu klein")
    elif versuch2 > richtigeZahl:
        print("ist zu gross")
    while versuch3 == 0:
        y = int(input("geben sie eine dritte zahl ein:"))
        if 1<=y<=20:
            versuch3 = y
        else:
            print("fehler")
    versuch3 = int(input("geben sie eine dritte zahl ein:"))
    if versuch3 == richtigeZahl:
        print("Sie haben nach drei versuchen gewonnen!")
    elif versuch3 < richtigeZahl:
        print("ist zu klein, sie haben leider verloren :-(")
    elif versuch3 > richtigeZahl:
        print("ist zu gross, sie haben leider verloren :-(")
a2a()"""
