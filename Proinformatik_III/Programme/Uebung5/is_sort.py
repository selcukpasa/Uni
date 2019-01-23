import random

def insertsort(liste):
    for j in range(1, len(liste)):
        einzusortierender_wert = liste[j]
        k = j-1
        while k>=0 and liste[k]>einzusortierender_wert:
            liste[k+1] = liste[k]
            print('Suche Position: ',liste)
            k = k-1
        liste[k+1] = einzusortierender_wert
        print (liste)


def is_sorted(liste):
    for e in liste:
        if e in liste == insertsort(liste):
            return 1
        elif e in liste == insertsort(liste, reverse= True):
            return -1
        else:
            return 0 
liste = eval(input("Geben Sie eine Liste ein:"))
print (is_sorted(liste))

#Definieren Sie eine Funktion generate random list, die bei Eingabe eines Wertebereichs
#(a, b) und einer ganzen Zahl n eine Liste mit n zufaelligen Zahlen zwischen a
#und b generiert.

"""a = int(input("Geben Sie a ein:"))
b = int(input("Geben Sie b ein:"))
n = int(input("Geben Sie die Länge ein:"))
f = sorted(random.sample(range(a,b), n))
print(f)"""

