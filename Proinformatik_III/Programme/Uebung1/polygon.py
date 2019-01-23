#ProInf III: Übung 1.

import math

n = int(input("Bitte geben Sie n ein:"))
s = int(input("Bitte geben Sie s ein:"))
a=s/(2*math.tan(math.pi/n))
area=(n*s*a)/2
print("Flaecheninhalt:",(area))