def majority(liste):
    ''' text '''
    h = len(liste)
    for e in liste:
        zaehler=0                       #ein Zähler wird verwendet
        for e2 in liste:
            if e2 == e:                 #wenn e2 zwei identisch ist mit e, dann wird der Zähler auf 1 erhöht
                zaehler += 1
        if zaehler >= int((h/2)+1):     #int = Da sonst ein float rauskommt z.B: [3,1,2,1,1,3,1] = 4.5; Der Zähler wird dann mit h/2+1 verglichen
            return e
liste= eval(input("Geben Sie eine Liste ein:")) 
print (majority(liste))                 # Wenn es nicht übereinstimmt wird None ausgegeben 