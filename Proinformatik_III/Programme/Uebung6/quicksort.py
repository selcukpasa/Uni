import random 

def partition( A, low, high ):
    pivot = A[median]
    i = low
    for j in range(low+1,high+1):
        if ( A[j] < pivot ):
            i=i+1
            A[i], A[j] = A[j], A[i]
    A[i], A[low] = A[low], A[i]
    return i

def median ( A, low, high):
    for random_elem1 in A:
        random.randint(random_elem1)
    for random_elem2 in A:
        random.randint(random_elem2)
    for random_elem3 in A:
        random.randint(random_elem3)
    random_elem = [random_elem1,random_elem2,random_elem3]
    piv_cand = [A[random_elem]]
    if low>piv_cand>high:
        return sorted(piv_cand)[1]

"""Schreiben Sie eine Variante des Quicksort-Algorithmus aus der Vorlesung, der den
Median-Wert aus drei zuf¨allig gew¨ahlten Elementen des zu sortierenden Teilarrays
berechnet und diesen Wert als Pivot verwendet. Testen und vergleichen Sie Ihre
Implementierung mit der aus der Vorlesung fur zuf ¨ ¨allige und fur aufsteigend sortierte ¨
Listen (worst-case-Eingabe)."""