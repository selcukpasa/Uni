#ProInf III/Übung 3./Aufgabe 3.

l = eval(input("Liste einfügen:"))                            
x = []
unique = []
for elem in l:
    if elem not in x:
        unique.append(elem)
        x.append(elem)
print (unique)

