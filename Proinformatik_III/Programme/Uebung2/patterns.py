#ProInf III/Übung 2./Aufgabe 3.

n=int(input("enter the no of rows :"))
for i in range(0,n+1):
    for j in range(0,n-i):
        print(end=" ")
    for j in range(0,i):
        print("#",end=" ")
    print()
if i==n:
    for i in range(n-1,0,-1):
        for j in range(0,n-i):
            print(end=" ")
        for j in range(0,i):
            print("#",end=" ")
        print()