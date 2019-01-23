#ProInf III/Übung 3./Aufgabe 1.

liste1 = eval(input("Geben Sie die erste Liste ein:"))
liste2 = eval(input("Geben Sie die zweite Liste ein:"))
resultat = zip(liste1, liste2)
resultatListe = list(resultat)
if len(liste1) == len(liste2):
    print(resultatListe)
else:
        print ("Error")


