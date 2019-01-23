def is_sorted(n):
	a = None
	richtung = None
	for i in n:
		print("i =",i)
		if a == None:
			a = i
		else:
			if richtung != None:
				if (a < i):
					if richtung == 1:
						a = i
						continue
					elif richtung == -1:
						return 0
				elif (a > i):
					if richtung == 1:
						return 0
					elif richtung == -1:
						a = i
						continue
			if (a < i):
				richtung = 1
			elif (a > i):
				richtung = -1
			a = i
	return richtung

#n = [1,2,5,4]
n = [5,4,3,2,1]
print(is_sorted(n))