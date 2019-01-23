def caesar_chiffre(s):
    umlaut1 = ord('Ö')
    umlaut2 = ord('Ä')
    umlaut3 = ord('Ü')
    umlaut4 = ord('ä')
    umlaut5 = ord('ö')
    umlaut6 = ord('ü')
    res = ""
    for zeichen in s:
        asciizahl = ord(zeichen)
        """if zeichen == "Ä" or zeichen == "ä":
            s = s[0:n] + "ae" + s[n+1:len(s)]
            continue"""
        if 96 < asciizahl < 123:
            asciizahl -= 32
            res += chr(asciizahl)
        elif 91 <= asciizahl < 97:
            pass
        elif ord(zeichen) == umlaut4:
            res += "AE"
        elif ord(zeichen) == umlaut5:
            res += "OE"
        elif ord(zeichen) == umlaut6:
            res += "UE"
        elif ord(zeichen) == umlaut1:
            res += "OE" 
        elif ord(zeichen) == umlaut2:
            res += "AE"
        elif ord(zeichen) == umlaut3:
            res += "UE"
    verschluesselt=""
    for c in res:
        c =  chr((ord(c) + n-65)%26+65)
        verschluesselt+=c 
    """return verschluesselt"""
    entschluesselt=""
    for f in verschluesselt:
        f =  chr((ord(f) - n-65)%26+65)
        entschluesselt += f
    print(verschluesselt)
    return entschluesselt
s = (input("Geben Sie ein Wort ein:"))
n = int(input("Geben Sie eine Zahl an:"))
print(caesar_chiffre(s))

