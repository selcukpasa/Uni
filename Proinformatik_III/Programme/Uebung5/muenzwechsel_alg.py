def muenzwechsel (geld,ausgabe):
    geld = int(float(geld)*100) 
    ausgabe = int(float(ausgabe) * 100)
    wechselgeld = int(geld - ausgabe)

    zwei_euro = int(wechselgeld // 200)
    ein_euro = int(wechselgeld % 200) // 100
    fuenfzig_cent = int(wechselgeld % 200 % 100) // 50
    zwanzig_cent = int(wechselgeld % 200 % 100 % 50) // 20
    zehn_cent = int(wechselgeld % 200 % 100 % 50 % 20) // 10
    fuenf_cent = int(wechselgeld % 200 % 100 % 50 % 20 % 10) // 5
    zwei_cent = int(wechselgeld % 200 % 100 % 50 % 20 % 10 % 5) // 2
    ein_cent = int(wechselgeld % 200 % 100 % 50 % 20 % 10 % 5 % 2) // 1
    print(wechselgeld)
    print(zwei_euro, "zwei Euros")
    print(ein_euro, "ein Euros")
    print(fuenfzig_cent, "fuenfzig Cents")
    print(zwanzig_cent, "zwanzig Cents")
    print(zehn_cent, "zehn Cents")
    print(fuenf_cent, "fuenf Cents")
    print(zwei_cent, "zwei Cents")
    print(ein_cent, "ein Cents")
geld = input("Wie viel Geld haben Sie dabei?: ")
ausgabe = input("Wie viel wollen Sie ausgeben?: ")
print (muenzwechsel(geld, ausgabe))