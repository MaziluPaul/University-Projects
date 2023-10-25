;10.z=(a*a+b*b)/(a*a-b*b-5).

ASSUME CS:code , DS:data

data SEGMENT

a db 4
b db 2
c db 1
d db 1
rez dw ?

data ENDS

code SEGMENT
start:
mov AX, data
mov DS, AX

mov AL, b
imul b
; AX = b*b
mov CX, AX

mov AL, a
imul a
; AX = a*a

sub AX,CX
mov BX, 5
sub AX, BX
; AX = a*a-b*b-5
mov CX, AX

mov AL, b
imul b
; AX = b*b
mov BX, AX

mov AL, a
imul a
; AX = a*a

add AX, BX
; AX = a*a+b*b
cwd
idiv CX
mov AH,0

mov rez, AX
mov AX, 4C00h
int 21h
code ENDS
END start