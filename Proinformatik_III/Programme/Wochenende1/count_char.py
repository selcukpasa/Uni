def count_chars(text):
    dic = {}
    for char in text:
        if char not in dic:
            dic[char] = 1
        else:
            dic[char] += 1
    return dic 
text= input("Geben Sie ihren Text ein:")
print(count_chars(text))

