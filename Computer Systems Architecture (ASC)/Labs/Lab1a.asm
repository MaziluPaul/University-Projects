; spunem asamblorului care sunt segmentele folosite de noi

ASSUME cs: code, ds:data
; data - segmentul de date in care vom defini variabilele
data SEGMENT
a db 7 ; a este un octet(byte)
b dw 10 ; b este un cuvant (word -2 bytes)
c db 3,5,6,1 ; c e un sir de octeti
data ENDS
;code - numele segmentului de cod
code SEGMENT
start:
mov ax,data ; pentru a sti care e segmentul de date, mutam in registrul ds
mov ds,ax ; adresa segmentului de date, si pentru ca nu putem face asta direct folosim intermediar registrul ax
; .......
;aici avem instructiunile programului nostru
;........
mov ax,4C00h
int 21h
; e apelata intreruperea 21, cu ah incarcat cu 4C,adica sfarsitul executiei, si se returneaza codul de eroare al=00, adica
; executie cu succes
code ENDS
END start