def bin_search(key,A):
    if len(A)>1:
        m = len(A)//2
        if A[m]==key:
            return True
        elif key<A[m]:
            return bin_search(key, A[0:m])
        else:
            return bin_search(key, A[(m+1):])
    elif len(A)==1:
        return A[0]==key
    else:
        return False




def binary_search_it (key, A):
    lowerBound = 0
    upperBound = len(A) - 1

    while  lowerBound <= upperBound:
        current = (lowerBound + upperBound)//2
        if A[current] == key:
            return True
        else:
            if  A[current] < key:
                lowerBound = current + 1
            else:
                upperBound = current - 1
    return False


def bubblesort (A):
    swap = True
    stop = len(A)-1
    while swap:
        swap = False
        for i in range(stop):
            if A[i]>A[i+1]:
                A[i], A[i+1] = A[i+1], A[i]
                print(A)
                swap = True
        print("decrease stopp: ",A) 
        stop = stop-1

def insertsort(liste):
    for j in range(1, len(liste)):
        einzusortierender_wert = liste[j]
        k = j-1
        while k>=0 and liste[k]>einzusortierender_wert:
            liste[k+1] = liste[k]
            print('Suche Position: ',liste)
            k = k-1
        liste[k+1] = einzusortierender_wert
        print(liste)
