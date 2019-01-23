def merge(low, high):
    res = []
    i, j = 0, 0
    while i<len(low) and j<len(high):
        if low[i] <= high[j]:
            res.append(low[i])
            i = i+1
        else:
            res.append(high[j])
            j = j+1
        res = res + low[i:]
        res = res + high[j:]
        return res

def merge_inplace(A,A2):
    lenTL=1
    while lenTL < len(A):
        lenTL*=2
        for i in range (0,len(a),lenTL):
            A2[i:i+lenTL]=merge(A[i:i+lenTL//2],A[i+lenTL//2:i+lenTL]
            A,A2 = A2,A
return A