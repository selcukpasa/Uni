geld = input("Wie viel Geld haben Sie dabei?: ")
ausgabe = input("Wie viel wollen Sie ausgeben?: ")
geld = int(float(geld)*100) 
ausgabe = int(float(ausgabe) * 100)
wechselgeld = int(geld - ausgabe)

fuenfzig_cent = int(wechselgeld // 50)
zwanzig_cent = int(wechselgeld % 50) // 20
ein_cent = int(wechselgeld % 50 % 20) // 1


print(wechselgeld)
print(fuenfzig_cent, "fuenfzig Cents")
print(zwanzig_cent, "zwanzig Cents")
print(ein_cent, "ein Cents")

#Wenn man zB: 0.60 zurückbekommen will werden 1x50 Cent und 10x1 Cent zurückgegeben