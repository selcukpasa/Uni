import random


def generate_list(a,b,n):
    printList = []

    for i in range(0,n):
        printList.append(random.randint(a,b))
    return printList

a = int(input("Fügen Sie a ein:"))
b = int(input("Fügen Sie b ein:"))
n = int(input("Fügen Sie n ein:"))
print(generate_list(a,b,n))