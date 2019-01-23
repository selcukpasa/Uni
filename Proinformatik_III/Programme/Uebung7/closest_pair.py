#ProInfo III Übung 7. Aufgabe 3.

def closest_pair (liste , x):
    for a in liste:
        if a < x:
            paar = int(x) - int(a)
            if paar in liste:
                return (a,paar)
        else:
            print("nicht in der Liste!")
liste = eval(input("Geben Sie eine Liste ein:"))
x = int(input("Geben Sie eine Zahl ein:"))
print (closest_pair(liste , x))