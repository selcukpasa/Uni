import random
richtigeZahl = random.randint(1,20)
x = int(input("geben sie eine Zahl ein:"))
if 1<=x<=20:
        versuch1 = x
else:
        print ("fehler")
if versuch1 == richtigeZahl:
        print("Sie haben nach einem versuch gewonnen!")
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
versuch3 = 0
while versuch3 == 0:
    y = int(input("geben sie eine dritte zahl ein:"))
    if 1<=y<=20:
        versuch3 = y
    else:
        print("fehler")
if versuch3 == richtigeZahl:
    print("Sie haben nach drei versuchen gewonnen!")
elif versuch3 < richtigeZahl:
    print("ist zu klein, sie haben leider verloren :-(")
elif versuch3 > richtigeZahl:
    print("ist zu gross, sie haben leider verloren :-(")


"""import random
 
erg = random.randint(0, 100)
print("Bitte erraten sie die gesuchte Zahl, sie befindet sich zwischen 1 und 100")
i = 1
while True:
    print("ihr " + str(i) + ". Versuch:")
    zahl = int(input())
    if zahl > erg:
        print("die gesuchte Zahl ist kleiner.")
    elif zahl < erg:
        print("die gesuchte Zahl ist größer.")
    elif zahl == erg:
        print("Glückwunsch die von Ihnen eingebene Zahl" , erg ,  "stimmt mit der gesuchten Zahl überein.")
    i += 1"""
