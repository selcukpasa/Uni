def bin_search(key,A, count = 0):
	if len(A)>=1:
		count += 1
		m = len(A)//2
		if A[m]==key:
			return True, count
		elif key<A[m]:
			return bin_search(key, A[0:m],count)
		else:
			return bin_search(key, A[(m+1):],count)
	elif len(A)==1:
		return A[0]==key
	else:
		return False, count

#print(bin_search(5,[1,3,5]))


def binary_search_it (key, A):
	lowerBound = 0
	upperBound = len(A) - 1

	while  lowerBound <= upperBound:
		current = (lowerBound + upperBound)//2
		if A[current] == key:
			return True
		else:
			if	A[current] < key:
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
		
def bubblesort_errcount(A):
	swap = True
	counter = 0
	listsorted = sorted(A)
	stop = len(A)-1
	while swap:
		swap = False
		for i in range(stop):
			if A[i]>A[i+1]:
				elem = A[i+1]
				dist = abstand(elem, i+1, listsorted)
				print("elem = ", elem ,"dist_pre =", dist)
				A[i], A[i+1] = A[i+1], A[i]
				dist2 = abstand(elem,i,listsorted)
				print("dist_post =", dist2)
				if not (dist2 < dist):
					counter = counter + 1
				print(A)
				swap = True
		print("decrease stopp: ",A)
		stop = stop-1
	return counter
		
def abstand(elem, elem_index, liste):
	"""bestimmt abstand zwischen elem_index und position von elem innerhalb von liste"""
	for i in range(len(liste)):
		if elem == liste[i]:
			return abs(i - elem_index)
			
print("abst = ",bubblesort_errcount([4,3,2,1]))

def insertsort(A):
	for j in range(1, len(A)):
		key = A[j]
		k = j-1
		while k>=0 and A[k]>key:
			A[k+1] = A[k]
			print('Suche Position: ',A)
			k = k-1
		A[k+1] = key
		print(A)
		
def minimum(A):
	min = A[0]
	for i in range(0,len(A)-1):
		if A[i]<A[i+1]:
			A[i+1]=A[i]
	return A[len(A)-1]
			
def selectionsort(A):
	res = []
	for i in range(len(A)):
		min = minimum(A[0:len(A)])# die kopie brauchen wir, damit A[i+1]=A[i] nicht ueberschrieben wird
		A.remove(min)
		res.append(min)
	return res
print(selectionsort([1,0,5,8,7,9]))