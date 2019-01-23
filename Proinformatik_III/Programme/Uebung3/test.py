n = int(input("Fügen Sie eine Zahl ein:"))
if n<0:
    ergebnis=-1
else:
    temp, ergebnis = 1,0
for i in range(n):
    ergebnis += temp
    ergebnis ,temp = temp, ergebnis
print (ergebnis)
