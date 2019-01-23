#ProInf III Übung WS2. Aufgabe 3.(Selcuk Karasinan; Fatima Azham)

def sortieren (handy, kabel):
    if handy == []:                     #Wenn die Liste leer ist soll das Programm den Fehler zurückgeben!
        print("Kiste ist leer!")
    for elem in handy:                  #Wenn das elem mit dem Kabel übereinstimmt soll es das zurückgeben, ansonsten wird solange überprüft bis das gesuchte Element gefunden wurde(>,<,= wird durch das "==" durchgeführt.)
        if elem == kabel:               
            return (elem,kabel)         #Das gefundene elem soll mit dem kabel als Tupel zurückgegeben werden.
            
handy = eval(input("Fügen Sie die Kiste ein:"))
kabel = int(input("Welcher Kabel soll es sein:"))
print (sortieren (handy, kabel))